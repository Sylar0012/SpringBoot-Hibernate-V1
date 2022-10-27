package site.metacoding.white.dto;

import site.metacoding.white.domain.Board;
import site.metacoding.white.domain.User;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto;
import site.metacoding.white.dto.BoardRespDto.BoardSaveRespDto.UserDto;

public class BoardSaveRespDtoTest {

  public void innerclass_test() {
    Board board = new Board();
    BoardSaveRespDto boardSaveRespDto = new BoardSaveRespDto(board);
    boardSaveRespDto.setId(1L);
    boardSaveRespDto.setTitle("title");
    boardSaveRespDto.setContent("content");

    User user = new User();
    UserDto userDto = new UserDto(user);
    userDto.setId(1L);
    userDto.setUsername("ssar");

    boardSaveRespDto.setUser(userDto);
  }

}
