package site.metacoding.white.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;

@RequiredArgsConstructor
@Service // 컴퍼넌트 스캔.
public class BoardService {

  private final BoardRepository boardRepository;

  @Transactional
  public void save(Board board) {
    boardRepository.save(board);
  }

  public Board findById(Long id) {
    return boardRepository.findById(id);
  }
}
