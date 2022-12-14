package site.metacoding.white.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardReqDto.BoardUpdateReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardAllRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardDetailRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

  private final BoardService boardService;
  private final HttpSession session;

  @PostMapping("/s/board")
  public ResponseDto<?> save(@RequestBody BoardSaveReqDto boardSaveReqDto) {
    SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
    if (sessionUser == null) {
      new ResponseDto<>(-1, "로그인이 되지 않았습니다.", null);
    }
    boardSaveReqDto.setSessionUser(sessionUser);
    BoardSaveRespDto boardSaveRespDto = boardService.save(boardSaveReqDto); // 서비스에는 단 하나의 객체만 전달한다.
    return new ResponseDto<>(1, "성공", boardSaveRespDto);
  }

  // 게시글 상세보기 ( Board + User + List<comment> )
  @GetMapping("/board/{id}")
  public ResponseDto<?> findById(@PathVariable Long id) {
    SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
    if (sessionUser == null) {
      new ResponseDto<>(-1, "로그인이 되지 않았습니다.", null);
    }
    BoardDetailRespDto boardDetailRespDto = boardService.findById(id);
    return new ResponseDto<>(1, "성공", boardDetailRespDto);
  }

  @PutMapping("/s/board/{id}")
  public ResponseDto<?> update(@PathVariable Long id, @RequestBody BoardUpdateReqDto boardUpdateReqDto) {
    SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
    if (sessionUser == null) {
      new ResponseDto<>(-1, "로그인이 되지 않았습니다.", null);
    }
    boardUpdateReqDto.setId(id);
    return new ResponseDto<>(1, "성공", boardService.update(boardUpdateReqDto));
  }

  @GetMapping("/board/list")
  public ResponseDto<?> findAll() {
    List<BoardAllRespDto> boardAllRespDtoList = boardService.findAll();
    return new ResponseDto<>(1, "성공", boardAllRespDtoList);
  }

  @DeleteMapping("/s/board/{id}")
  public ResponseDto<?> deleteById(@PathVariable Long id) {
    SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
    // 권한 관련 파라메터는 두번째 파라메터로 넘긴다. ( 컨트롤러 말고 서비스에서 권한체크 하란 소리 )
    boardService.deleteById(id, sessionUser.getId());
    return new ResponseDto<>(1, "성공", null);
  }
}
