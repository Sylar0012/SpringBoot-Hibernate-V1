package site.metacoding.white.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.service.BoardService;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

  private final BoardService boardService;

  @PostMapping("/board")
  public String save(@RequestBody Board board) { // @RequestBody : json 타입으로 전송
    boardService.save(board);
    return "ok";
  }
}
