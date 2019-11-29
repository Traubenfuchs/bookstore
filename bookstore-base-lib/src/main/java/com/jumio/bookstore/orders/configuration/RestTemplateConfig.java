package com.jumio.bookstore.orders.configuration;

import org.springframework.boot.web.client.*;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@Configuration
public class RestTemplateConfig {
  @LoadBalanced
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.build();
  }
}