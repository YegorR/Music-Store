package ru.yegorr.musicstore.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class CorsConfig {
  @Bean
  public FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean() {
    FilterRegistrationBean<CorsFilter> corsFilter = new FilterRegistrationBean<>();
    corsFilter.setOrder(Ordered.LOWEST_PRECEDENCE);
    corsFilter.setFilter(new CorsFilter());
    corsFilter.addUrlPatterns("/*");
    return corsFilter;
  }
}
