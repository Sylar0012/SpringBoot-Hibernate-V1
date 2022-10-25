package site.metacoding.white.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

//공통 ResponseDto
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto<R> {
  private Integer code;
  private String msg;
  private R data;

}
