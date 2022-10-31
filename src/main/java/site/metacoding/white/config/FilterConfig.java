package site.metacoding.white.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class FilterConfig {

  @Bean // 필터 IOC 컨테이너에 등록
  public FilterRegistrationBean<HelloFilter> jwtAuthenticationFilterRegister() {
    log.debug("디버그 : 인증 필터 등록");

    // 필터 객체 생성
    FilterRegistrationBean<HelloFilter> bean = new FilterRegistrationBean<>(new HelloFilter());

    // /hello 주소 실행시 필터 실행.
    bean.addUrlPatterns("/hello");
    return bean;
  }
}

@Slf4j
class HelloFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;

    if (req.getMethod().equals("POST")) {
      log.debug("디버그 : helloFiter 실행 됨");
    } else {
      log.debug("디버그 : POST요청이 아니라 실행 할 수 없습니다.");
    }

    // chain.doFilter(req, resp);

  }

}