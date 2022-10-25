package site.metacoding.white.web;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

  private final UserService userService;
  private final HttpSession session;

  @PostMapping("/join")
  public ResponseEntity<?> save(@RequestBody JoinReqDto joinReqDto) { // @RequestBody : json 타입으로 전송
    User userPS = userService.save(joinReqDto);
    return new ResponseEntity<>(userPS, HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginReqDto loginReqDto) {
    User principal = userService.login(loginReqDto);
    session.setAttribute("principal", principal);
    return "ok";
  }

}
