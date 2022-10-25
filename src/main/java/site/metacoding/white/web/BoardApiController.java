package site.metacoding.white.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.dto.ResponseDto;
import site.metacoding.white.dto.SessionUser;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardReqDto.BoardUpdateReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardDetailRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardUpdateRespDto;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

  private final BoardService boardService;
  private final HttpSession session;

  @PostMapping("/board")
  public ResponseDto<?> save(@RequestBody BoardSaveReqDto boardSaveReqDto) {
    SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
    boardSaveReqDto.setSessionUser(sessionUser);
    BoardSaveRespDto boardSaveRespDto = boardService.save(boardSaveReqDto); // 서비스에는 단 하나의 객체만 전달한다.
    return new ResponseDto<>(1, "성공", boardSaveRespDto);
  }

  @GetMapping("/board/{id}")
  public BoardDetailRespDto findById(@PathVariable Long id) {
    return boardService.findById(id);
  }

  @PutMapping("/board/{id}")
  public ResponseDto<?> update(@PathVariable Long id, @RequestBody BoardUpdateReqDto boardUpdateReqDto) {
    BoardUpdateRespDto boardUpdateRespDto = boardService.update(id, boardUpdateReqDto);
    return new ResponseDto<>(1, "성공", boardUpdateRespDto);
  }

  @GetMapping("/board/list")
  public List<Board> findAll() {
    return boardService.findAll();
  }

  @DeleteMapping("/board/{id}")
  public String deleteById(@PathVariable Long id) {
    boardService.deleteById(id);
    return "ok";
  }
}
