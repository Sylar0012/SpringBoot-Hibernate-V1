package site.metacoding.white.web;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserRespDto.UpdateRespDto;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.dto.UserReqDto.UpdateReqDto;
import site.metacoding.white.dto.UserRespDto.JoinRespDto;
import site.metacoding.white.service.UserService;

@RequiredArgsConstructor
@RestController
public class UserApiController {

  private final UserService userService;
  private final HttpSession session;

  @PostMapping("/join")
  public ResponseDto<?> save(@RequestBody JoinReqDto joinReqDto) { // @RequestBody : json 타입으로 전송
    JoinRespDto joinRespDto = userService.save(joinReqDto);
    return new ResponseDto<>(1, "ok", joinRespDto);
  }

  // 회원정보 수정
  @PutMapping("/update")
  public ResponseDto<?> update(@RequestBody UpdateReqDto updateReqDto) {
    UpdateRespDto updateRespDto = userService.update(updateReqDto);
    return new ResponseDto<>(1, "ok", updateRespDto);
  }

  // // 회원정보 보기

  // @PostMapping("/login")
  // public ResponseDto<?> login(@RequestBody LoginReqDto loginReqDto) {
  // SessionUser sessionUser = userService.login(loginReqDto);
  // session.setAttribute("sessionUser", sessionUser);
  // return new ResponseDto<>(1, "ok", sessionUser);
  // }

}
