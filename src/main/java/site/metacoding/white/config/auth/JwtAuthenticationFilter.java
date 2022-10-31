package site.metacoding.white.config.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.util.SHA256;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements Filter {

  private final UserRepository userRepository; // DI (FilterConfig한테 주입 받음)

  // /login 요청시
  // post 요청시
  // username, password (json)
  // db확인
  // 토큰 생성
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // 다운캐스팅을 안하면 쓸수 있는 메서드 갯수가 적음.
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    // Post요청이 아닌것을 거부
    if (!req.getMethod().equals("POST")) {
      customResponse("로그인시에는 post요청을 해야 합니다.", resp);
      return;
    }

    // Body 값 받기
    LoginReqDto loginReqDto = accecptBody(req);

    Optional<User> userOP = userRepository.findByUsername(loginReqDto.getUsername());
    if (userOP == null) {
      customResponse("유저네임이 없습니다.", resp);
      return;
    }
    // -- 유저네임 체크 -- //

    SHA256 sh = new SHA256();
    User userPS = userOP.get();
    String encPassword = sh.encrypt(loginReqDto.getPassword());
    if (!userPS.getPassword().equals(encPassword)) {
      customResponse("패스워드가 다릅니다.", resp);
      return;
    }
    // -- 패스워드 체크 -- //
    Date expire = new Date(System.currentTimeMillis() + (1000 * 60 * 60));
    String jwtToken = JWT.create()
        .withSubject("metacoding")
        .withExpiresAt(expire)
        .withClaim("id", userPS.getId())
        .withClaim("iuserma,e", userPS.getUsername())
        .sign(Algorithm.HMAC512("뺑소니"));

    log.debug("디버그 : " + jwtToken);

    // JWT토큰 응답
    customJwtResponse(jwtToken, userPS, resp);
  }

  private LoginReqDto accecptBody(HttpServletRequest req) throws IOException, StreamReadException, DatabindException {
    ObjectMapper om = new ObjectMapper();
    LoginReqDto loginReqDto = om.readValue(req.getInputStream(), LoginReqDto.class);
    log.debug("디버그 : " + loginReqDto.getUsername());
    log.debug("디버그 : " + loginReqDto.getPassword());
    return loginReqDto;
  }

  private void customResponse(String msg, HttpServletResponse resp) throws IOException, JsonProcessingException {
    resp.setStatus(400);
    resp.setContentType("application/json; charset=utf-8");
    PrintWriter out = resp.getWriter();
    ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);
    ObjectMapper om2 = new ObjectMapper();
    String body = om2.writeValueAsString(responseDto);
    out.println(body);
    out.flush();
  }

  private void customJwtResponse(String token, User userPS, HttpServletResponse resp)
      throws IOException, JsonProcessingException {
    resp.setStatus(200);
    resp.setHeader("Authorization", "Bearer " + token);
    resp.setContentType("application/json; charset=utf-8");
    PrintWriter out = resp.getWriter();
    ResponseDto<?> responseDto = new ResponseDto<>(1, "성공", new SessionUser(userPS));
    ObjectMapper om2 = new ObjectMapper();
    String body = om2.writeValueAsString(responseDto);
    out.println(body);
    out.flush();
  }
}