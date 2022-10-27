package site.metacoding.white.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.BoardRepository;
import site.metacoding.white.domain.Comment;
import site.metacoding.white.domain.CommentRepository;
import site.metacoding.white.dto.CommentReqDto.CommentSaveReqDto;
import site.metacoding.white.dto.CommentRespDto.CommentSaveRespDto;

@Setter // 지워야함
@Slf4j
@RequiredArgsConstructor
@Service // 컴퍼넌트 스캔.
public class CommentService {

  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;

  @Transactional
  public CommentSaveRespDto save(CommentSaveReqDto commentSaveReqDto) {
    // 1. Board가 있는지 확인
    Optional<Board> boardOP = boardRepository.findById(commentSaveReqDto.getBoardId());

    if (boardOP.isPresent()) {
      // 2. Comment 객체 만들기
      Comment comment = commentSaveReqDto.toEntity(boardOP.get());
      Comment commentPS = commentRepository.save(comment);
      CommentSaveRespDto commentSaveRespDto = new CommentSaveRespDto(commentPS);
      return commentSaveRespDto;
    } else {
      throw new RuntimeException("게시글이 없어서 댓글을 달 수 없습니다.");
    }

  }

  @Transactional
  public void deleteById(Long id) {
    Optional<Comment> commentOP = commentRepository.findById(id);
    if (commentOP.isPresent()) {
      commentRepository.deleteById(id);
    } else {
      throw new RuntimeException("해당 " + id + "로 삭제 할 수 없습니다.");
    }

  }

}
