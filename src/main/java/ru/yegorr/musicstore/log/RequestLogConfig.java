package ru.yegorr.musicstore.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RequestLogConfig implements WebMvcConfigurer {

  private final RequestLogInterceptor requestLogInterceptor;

  @Autowired
  public RequestLogConfig(RequestLogInterceptor requestLogInterceptor) {
    this.requestLogInterceptor = requestLogInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(requestLogInterceptor);
  }
}
