package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import site.metacoding.white.domain.User;
import site.metacoding.white.domain.UserRepository;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.UserRespDto.UpdateRespDto;
import site.metacoding.white.dto.UserReqDto.JoinReqDto;
import site.metacoding.white.dto.UserReqDto.LoginReqDto;
import site.metacoding.white.dto.UserReqDto.UpdateReqDto;
import site.metacoding.white.dto.UserRespDto.JoinRespDto;
import site.metacoding.white.util.SHA256;

@RequiredArgsConstructor
@Service // 컴퍼넌트 스캔.
public class UserService {

  private final UserRepository userRepository;
  private final SHA256 sha256;

  // 응답의 DTO는 서비스에서 만든다.
  @Transactional // 트랜잭션을 붙이지 않으면 영속화 되어 있는 객체가 flush가 안됨.
  public JoinRespDto save(JoinReqDto joinReqDto) {

    // 비밀번호 해시
    String encPassword = sha256.encrypt(joinReqDto.getPassword());
    joinReqDto.setPassword(encPassword);

    // 회원정보 저장
    User userPS = userRepository.save(joinReqDto.toEntity());

    // DTO 리턴
    return new JoinRespDto(userPS);
  } // 트랜잭션 종료

  @Transactional(readOnly = true)
  public SessionUser login(LoginReqDto loginReqDto) {
    User userPS = userRepository.findByUsername(loginReqDto.getUsername());
    if (userPS.getPassword().equals(sha256.encrypt(loginReqDto.getPassword()))) {
      return new SessionUser(userPS);
    } else {
      throw new RuntimeException("아이디 혹은 패스워드가 잘못 입력되었습니다.");
    }
  }

  public UpdateRespDto update(UpdateReqDto updateReqDto) {
    Long id = updateReqDto.getId();
    User userPS = new User();
    if (userPS != null) {
      String encPassword = sha256.encrypt(updateReqDto.getPassword());
      userPS.update(encPassword);
      return new UpdateRespDto(userPS);
    } else {
      throw new RuntimeException("해당 " + id + "의 게시글을 수정 할 수 없습니다");
      // 비밀번호 해시
    }
  }
}
