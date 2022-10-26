package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.Comment;

public class CommentReqDto {

  @Getter
  @Setter
  public static class CommentSaveReqDto {
    private String content;
    private Long boardId;
    private SessionUser sessionUser; // 서비스로직
    // Board도 필요함.

    public Comment toEntity(Board board) {
      Comment comment = Comment.builder()
          .content(content)
          .board(board)
          .user(sessionUser.toEntity())
          .build();
      return comment;
    }
  }

}
