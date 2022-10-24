package site.metacoding.white.service;

import java.util.List;

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

  @Transactional
  public void update(Long id, Board board) {

    // 영속화
    Board boardPS = boardRepository.findById(id);

    // 영속화된 데이터를 클라이언트가 보낸 데이터로 수정
    boardPS.setTitle(board.getTitle());
    boardPS.setContent(board.getContent());
  }// 트렌젝션 종료시 -> 더티체킹을 함.

  public List<Board> findAll() {
    return boardRepository.findAll();
  }

  @Transactional
  public void deleteById(Long id) {
    boardRepository.deleteById(id);
  }
}
