package com.jumio.bookstore.books.services;

import com.jumio.bookstore.books.entities.*;
import com.jumio.bookstore.books.mappers.*;
import com.jumio.bookstore.books.repositories.*;
import com.jumio.bookstore.data.dtos.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.util.*;

/**
 * Offers CRUD operations for the Book resource.
 */
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
    BookEntity book = bookRepository.getByIsbn(isbn);
    throwIfBookDoesNotExist(book);
    return bookMapper.entityToDto(book);
  }

  public BookDto getByUuid(String bookUuid) {
    BookEntity book = bookRepository.getByUuid(bookUuid);
    throwIfBookDoesNotExist(book);
    return bookMapper.entityToDto(book);
  }

  public Set<BookDto> getAll() {
    return bookMapper.entitiesToDtos(bookRepository.findAll());
  }

  private void throwIfBookDoesNotExist(BookEntity bookEntity) {
    if (bookEntity == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requested book not found.");
    }
  }
}
