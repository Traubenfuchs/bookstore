package com.jumio.bookstore.orders.services;

import com.jumio.bookstore.data.requests.*;
import com.jumio.bookstore.orders.clients.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.util.*;
import java.util.stream.*;

/**
 * Offers methods to verify if the given books exist.
 */
@Service
public class BookVerifier {
  private final BookClient bookClient;

  public BookVerifier(BookClient bookClient) {
    this.bookClient = bookClient;
  }

  /**
   * Throws an exception, if one of the bookUuid in the given OrderItemCreationRequests does not exist.
   */
  void verifyOrThrow(Collection<OrderItemRequest> orderItemRequests) {
    verifyOrThrow(orderItemRequests.stream().map(OrderItemRequest::getBookUuid).collect(Collectors.toSet()));
  }

  private void verifyOrThrow(Set<String> bookUuids) {
    bookUuids.forEach(bookUuid -> {
      if (bookClient.getByUuid(bookUuid) == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book with uuid<" + bookUuid + "> does not exist.");
      }
    });
  }
}