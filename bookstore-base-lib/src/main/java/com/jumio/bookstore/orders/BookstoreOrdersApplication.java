package com.jumio.bookstore.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.*;

@EnableTransactionManagement
@SpringBootApplication
public class BookstoreOrdersApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookstoreOrdersApplication.class, args);
  }
}