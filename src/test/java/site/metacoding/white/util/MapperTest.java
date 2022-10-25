package site.metacoding.white.util;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Product {
  private Integer id;
  private String name;
  private Integer price;
  private Integer qty;
  private String mcp; // 제조사
}

class ProductDto {
  private String name;
  private Integer price;
  private Integer qty;
}

public class MapperTest {

  @Test
  public void 매핑하기() {
    // 1. Product 객체생성

    // 2. 값 넣기

    // 3. ProductDto 객체생성 (디폴트)

    // 4. Product -> ProductDto로 옮기기
  }
}
