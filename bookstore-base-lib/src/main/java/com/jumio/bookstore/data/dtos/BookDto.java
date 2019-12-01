package com.jumio.bookstore.data.dtos;

import lombok.*;
import lombok.experimental.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Accessors(chain = true)
public class BookDto extends BaseDto<BookDto> {
  private String isbn;
  private Integer price;
  private String title;
}