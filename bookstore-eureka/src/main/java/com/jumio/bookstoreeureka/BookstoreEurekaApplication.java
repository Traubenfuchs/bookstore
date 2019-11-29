package com.jumio.bookstoreeureka;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.netflix.eureka.server.*;

@EnableEurekaServer
@SpringBootApplication
public class BookstoreEurekaApplication {
  public static void main(String[] args) {
    SpringApplication.run(BookstoreEurekaApplication.class, args);
  }
}
