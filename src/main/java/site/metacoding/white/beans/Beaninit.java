package site.metacoding.white.beans;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration
public class Beaninit {

  @Bean
  public ObjectMapper ominit() {
    return new ObjectMapper();
  }

  @Bean
  public HttpHeaders hhInit() {
    return new HttpHeaders();
  }

}
// 내가만든 클래스가 아닌데 IOC에 등록하고 싶을때 사용.
// @Configuration 이 달려있으면 내부를 스캔해서 @Bean 어노테이션을 찾음. 이후 해당 어노테이션이 달려 있으면 IOC
// 컨테이너에 올림.
//