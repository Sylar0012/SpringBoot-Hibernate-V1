package site.metacoding.white.dto;

import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;

public class BoardSaveRespDtoTest {

  public void innerclass_test() {
    BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto();
    boardSaveRespDto.setId(1L);
    boardSaveRespDto.setTitle("title");
    boardSaveRespDto.setContent("content");

    UserDto userDto = new UserDto();
    userDto.setId(1L);
    userDto.setUsername("ssar");

    boardSaveRespDto.setUser(userDto);
  }

}
