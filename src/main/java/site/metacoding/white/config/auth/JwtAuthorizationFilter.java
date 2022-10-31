package site.metacoding.white.config.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    // 헤더 Authorization 키 값에 bearer로 적힌 값이 있는지 체크
    String jwtToken = req.getHeader("Authorization");

    // 토큰 검증 ( 세션에 뭘 넣은적이 없어서 )
    jwtToken = jwtToken.replace("Bearer ", ""); // Bearer제거. ( http protocol이라 지켜야 해서 Bearer를 집어넣은거 )
    jwtToken = jwtToken.trim(); // 공백제거

    // JWT 객체 만들어서 알고리즘 넣고 토큰으로 서명하는 과정.
    // 알고리즘은 토큰 생성시 지정했던 그 알고리즘을 넣어야 함.
    try {
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("뺑소니")).build().verify(jwtToken);
      Long userId = decodedJWT.getClaim("id").asLong();
      String username = decodedJWT.getClaim("username").asString();
      SessionUser sessionUser = new SessionUser(User.builder().id(userId).username(username).build());
      HttpSession session = req.getSession();
      session.setAttribute("sessionUser", sessionUser);
      log.debug("디버그 : userId(filter) : " + userId);
    } catch (Exception e) {
      customResponse("토큰 검증 실패", resp);
    }
    // 디스페쳐 서블릿 입장 혹은 filter 체인 타기.
    chain.doFilter(req, resp);
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

}
