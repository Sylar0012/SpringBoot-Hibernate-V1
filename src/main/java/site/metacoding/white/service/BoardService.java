package site.metacoding.white.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;

import site.metacoding.white.dto.BoardReqDto.BoardSaveReqDto;
import site.metacoding.white.dto.BoardRespDto.BoardAllRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardDetailRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;

@RequiredArgsConstructor
@Service // 컴퍼넌트 스캔.
public class BoardService {

  private final BoardRepository boardRepository;

  @Transactional
  public BoardSaveRespDto save(BoardSaveReqDto boardSaveReqDto) {

    // 핵심 로직
    Board boardPS = boardRepository.save(boardSaveReqDto.toEntity());

    // DTO 전환
    BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(boardPS);

    return boardSaveRespDto;
  }

  @Transactional(readOnly = true) // 세션 종료 안됨
  public BoardDetailRespDto findById(Long id) {

    // Board boardPS = boardRepository.findById(id).orElseThrow(()-> new
    // RuntimeException("해당 " + id + "로 상세보기를 할 수 없습니다")) ;

    Optional<Board> boardOP = boardRepository.findById(id); // 오픈 인뷰가 false니까 조회후 세션 종료
    if (boardOP.isPresent()) {
      BoardDetailRespDto boardDetailRespDto = new BoardDetailRespDto(boardOP.get());
      return boardDetailRespDto;
    } else {
      throw new RuntimeException("해당 " + id + "로 상세보기를 할 수 없습니다");
    }

  }

  @Transactional
  public void update(Long id, Board board) {

    // 영속화
    Optional<Board> boardOP = boardRepository.findById(id); // 오픈 인뷰가 false니까 조회후 세션 종료
    if (boardOP.isPresent()) {
      boardOP.get().update(board.getTitle(), board.getContent());
    } else {
      throw new RuntimeException("해당 " + id + "의 게시글을 수정 할 수 없습니다");
    }

    // 영속화된 데이터를 클라이언트가 보낸 데이터로 수정

  }// 트렌젝션 종료시 -> 더티체킹을 함.

  public List<BoardAllRespDto> findAll() {
    List<Board> boardList = boardRepository.findAll();
    List<BoardAllRespDto> boardAllRespDtoList = new ArrayList<>();
    // 1. List의 크기 만큼 for문을 돌려야함
    for (Board board : boardList) {
      // 2. board -> DTO로 옮겨야함
      boardAllRespDtoList.add(new BoardAllRespDto(board));
    }
    // 3. DTO를 List에 담기
    return boardAllRespDtoList;
  }

  @Transactional
  public void deleteById(Long id) {
    boardRepository.deleteById(id);
  }
}
