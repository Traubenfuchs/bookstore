package com.jumio.bookstore.books.services;

import com.jumio.bookstore.books.mappers.*;
import com.jumio.bookstore.books.repositories.*;
import com.jumio.bookstore.orders.dtos.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  public BookService(
    BookRepository bookRepository,
    BookMapper bookMapper
  ) {
    this.bookRepository = bookRepository;
    this.bookMapper = bookMapper;
  }

  public BookDto getByIsbn(String isbn) {
    return bookMapper.entityToDto(bookRepository.getByIsbn(isbn));
  }

  public BookDto getByUuid(String uuid) {
    return bookMapper.entityToDto(bookRepository.getOne(uuid));
  }

  public Set<BookDto> getAll() {
    return bookRepository.findAll().stream().map(bookMapper::entityToDto).collect(Collectors.toSet());
  }
}
