package site.metacoding.white.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.white.domain.Board;

public class BoardReqDto {

  @Setter
  @Getter
  public static class BoardSaveReqDto {
    private String title;
    private String content;
    private SessionUser sessionUser; // 서비스 로직

    public Board toEntity() {
      return Board.builder()
          .title(title)
          .content(content)
          .user(sessionUser.toEntity())
          .build();
    }
  }

  @NoArgsConstructor
  @Getter
  @Setter
  public static class BoardUpdateReqDto {
    private String title;
    private String content;

    public BoardUpdateReqDto(Board board) {
      this.title = board.getTitle();
      this.content = board.getContent();
    }
  }

}