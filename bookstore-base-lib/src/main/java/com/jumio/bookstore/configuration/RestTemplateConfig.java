package com.jumio.bookstore.configuration;

import org.springframework.boot.web.client.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

import javax.servlet.http.*;

/**
 * Provides a Eureka enabled RestTemplate that propagates the current Authorization header.
 */
@Configuration
public class RestTemplateConfig {
  @LoadBalanced
  @Bean
  public RestTemplate restTemplate(
    RestTemplateBuilder restTemplateBuilder,
    HttpServletRequest httpServletRequest) {
    return restTemplateBuilder
      .additionalInterceptors((request, body, execution) -> {
        request.getHeaders().set("Authorization", httpServletRequest.getHeader("Authorization"));
        return execution.execute(request, body);
      })
      .build();
  }
}