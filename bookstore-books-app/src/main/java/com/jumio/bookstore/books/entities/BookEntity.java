package com.jumio.bookstore.books.entities;

import com.jumio.bookstore.entities.*;
import lombok.*;
import lombok.experimental.*;

import javax.persistence.*;

@Setter
@Getter
@Accessors(chain = true)
@Entity
@Table(name = "BOOKS")
public class BookEntity extends BaseEntity<BookEntity> {
  @Column(name = "TITLE", nullable = false)
  private String title;

  /**
   * It is assumed that an ISBN-13 is used.
   */
  @Column(name = "ISBN", nullable = false, unique = true)
  private String isbn;

  @Column(name = "PRICE")
  private Integer price;
}