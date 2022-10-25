package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class BoardReqDto {

  @Getter
  @Setter
  public static class BoardSaveReqDto {
    private String title;
    private String content;
    private User user;
  }

  // DTO는 여기다 추가.
}
