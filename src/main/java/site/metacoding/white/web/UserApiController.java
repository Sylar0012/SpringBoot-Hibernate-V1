package site.metacoding.white.web;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

  private final UserService userService;
  private final HttpSession session;

  @PostMapping("/join")
  public String join(@RequestBody JoinReqDto joinReqDto) { // @RequestBody : json 타입으로 전송
    userService.join(joinReqDto);
    return "ok";
  }

  @PostMapping("/login")
  public String login(@RequestBody User user) {
    User principal = userService.login(user);
    session.setAttribute("principal", principal);
    return "ok";
  }

}
