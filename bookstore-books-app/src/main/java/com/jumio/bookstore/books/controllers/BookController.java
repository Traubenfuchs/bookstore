package com.jumio.bookstore.books.controllers;


import com.jumio.bookstore.books.services.*;
import com.jumio.bookstore.orders.dtos.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api")
@RestController
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("books")
  public Set<BookDto> getAll() {
    return bookService.getAll();
  }

  @GetMapping("book/{uuid}")
  public BookDto getByUuid(@PathVariable String uuid) {
    return bookService.getByUuid(uuid);
  }

  @GetMapping("book/isbn/{isbn}")
  public BookDto getByIsbn(@PathVariable String isbn) {
    return bookService.getByIsbn(isbn);
  }
}
