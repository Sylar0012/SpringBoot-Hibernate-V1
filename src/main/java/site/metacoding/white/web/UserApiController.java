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

  @PostMapping("/user/save")
  public String save(@RequestBody User user) { // @RequestBody : json 타입으로 전송
    userService.save(user);
    return "ok";
  }

  @PostMapping("/user/login")
  public String login(@RequestBody User user) {
    session.setAttribute("principal", userService.login(user));
    return "ok";
  }

}
