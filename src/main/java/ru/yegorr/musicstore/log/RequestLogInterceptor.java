package ru.yegorr.musicstore.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestLogInterceptor extends HandlerInterceptorAdapter {
  private final Logger log = LoggerFactory.getLogger(RequestLogInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler) throws Exception {
    log.debug(request.getRequestURI());
    return super.preHandle(request, response, handler);
  }
}
