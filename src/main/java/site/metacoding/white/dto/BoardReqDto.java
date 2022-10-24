package site.metacoding.white.dto;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.white.domain.User;

public class BoardReqDto {

  @Getter
  @Setter
  public static class BoardSaveDto {
    private String title;
    private String content;

    private ServiceDto serviceDto;

    // 클라이언트 한테 받는거 아님
    @Getter
    @Setter
    public class ServiceDto {
      private User user;
    }

    public void newInstance() {
      serviceDto = new ServiceDto();
    }

  }

  // DTO는 여기다 추가.
}
