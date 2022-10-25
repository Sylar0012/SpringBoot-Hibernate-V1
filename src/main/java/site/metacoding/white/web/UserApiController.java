package site.metacoding.white.web;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.User;
import site.metacoding.white.service.BoardService;
import site.metacoding.white.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

  private final UserService userService;
  private final HttpSession session;

  @PostMapping("/join")
  public String join(@RequestBody User user) { // @RequestBody : json 타입으로 전송
    userService.join(user);
    return "ok";
  }

  @PostMapping("/login")
  public String login(@RequestBody User user) {
    User principal = userService.login(user);
    session.setAttribute("principal", principal);
    return "ok";
  }

}
