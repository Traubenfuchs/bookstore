package com.jumio.bookstore.books;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.*;

@EnableDiscoveryClient
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.jumio.bookstore"})
@SpringBootApplication
public class BookstoreBooksApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookstoreBooksApplication.class, args);
  }

}
