package com.jumio.bookstore.books.mappers;

import com.jumio.bookstore.books.entities.*;
import com.jumio.bookstore.orders.dtos.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {
  BookDto entityToDto(BookEntity source);

  BookEntity dtoToEntity(BookDto destination);
}
