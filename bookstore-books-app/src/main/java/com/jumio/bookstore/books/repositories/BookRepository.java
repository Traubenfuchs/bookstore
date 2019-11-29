package com.jumio.bookstore.books.repositories;

import com.jumio.bookstore.books.entities.*;
import org.springframework.data.jpa.repository.*;

public interface BookRepository extends JpaRepository<BookEntity, String> {
  BookEntity getByIsbn(String isbn);
}
