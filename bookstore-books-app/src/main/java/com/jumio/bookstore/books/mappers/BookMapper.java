package com.jumio.bookstore.books.mappers;

import com.jumio.bookstore.books.entities.*;
import com.jumio.bookstore.data.dtos.*;
import org.mapstruct.*;

import java.util.*;

@Mapper(componentModel = "spring")
public interface BookMapper {
  BookDto entityToDto(BookEntity source);

  Set<BookDto> entitiesToDtos(List<BookEntity> source);
}