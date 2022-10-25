package site.metacoding.white.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;

@RequiredArgsConstructor
@Service // 컴퍼넌트 스캔.
public class BoardService {

  private final BoardRepository boardRepository;

  @Transactional
  public void save(BoardSaveReqDto boardSaveReqDto) {
    Board board = new Board();
    board.setTitle(boardSaveReqDto.getTitle());
    board.setContent(boardSaveReqDto.getContent());
    board.setUser(boardSaveReqDto.getUser());
    boardRepository.save(board);
  }

  @Transactional(readOnly = true) // 세션 종료 안됨
  public Board findById(Long id) {
    Board boardPS = boardRepository.findById(id); // 오픈 인뷰가 false니까 조회후 세션 종료
    boardPS.getUser().getUsername(); // Lazy 로딩됨. (근데 Eager이면 이미 로딩되서 select 두번 됨
    // 4. user select 됨?
    System.out.println("서비스단에서 지연로딩 함. 왜? 여기까지는 디비커넥션이 유지되니까");
    return boardPS;
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
