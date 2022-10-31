package site.metacoding.white.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.white.config.auth.JwtAuthenticationFilter;
import site.metacoding.white.config.auth.JwtAuthorizationFilter;
import site.metacoding.white.domain.UserRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FilterConfig {

  private final UserRepository userRepository; // DI ( 스프링 Ioc컨테이너 에서 옴.)

  @Bean // 필터 IOC 컨테이너에 등록
  public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterRegister() {
    log.debug("디버그 : 인증 필터 등록");

    // 필터 객체 생성
    FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>(
        new JwtAuthenticationFilter(userRepository));

    // /hello 주소 실행시 필터 실행.
    bean.addUrlPatterns("/login");
    bean.setOrder(1);
    // 필터의 순서를 정한것. 낮은순서대로 실행.

    return bean;
  }

  @Profile("dev")
  @Bean // 필터 IOC 컨테이너에 등록
  public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilterRegister() {
    log.debug("디버그 : 인가 필터 등록");

    // 필터 객체 생성
    FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(
        new JwtAuthorizationFilter());

    // 원래 두개인데 이친구만 예외
    bean.addUrlPatterns("/s/*");
    bean.setOrder(2); // 이거 다음에 DS로 감
    return bean;
  }
}
