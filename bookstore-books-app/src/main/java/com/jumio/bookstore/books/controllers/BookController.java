package com.jumio.bookstore.books.controllers;

import com.jumio.bookstore.books.services.*;
import com.jumio.bookstore.data.dtos.*;
import lombok.extern.slf4j.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Offers RESTful CRUD operations for the Book resource.
 */
@RequestMapping("api")
@RestController
@Slf4j
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("books")
  public Set<BookDto> getAll() {

    log.info("BookController.getAll called.");
    return bookService.getAll();
  }

  @GetMapping("book/uuid/{uuid}")
  public BookDto getByUuid(@PathVariable String uuid) {
    log.info("BookController.getByUuid called.");
    return bookService.getByUuid(uuid);
  }

  @GetMapping("book/isbn/{isbn}")
  public BookDto getByIsbn(@PathVariable String isbn) {
    log.info("BookController.getByIsbn called.");
    return bookService.getByIsbn(isbn);
  }
}