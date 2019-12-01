package com.jumio.bookstore.orders.clients;

import com.jumio.bookstore.data.dtos.*;
import com.netflix.hystrix.contrib.javanica.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;
import org.springframework.web.server.*;

/**
 * HTTP/REST Client methods for bookstore-books-app.
 */
@Component
public class BookClient {
  private final RestTemplate restTemplate;

  public BookClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Returns the book with the given uuid or throws.
   *
   * @return never null
   */
  @HystrixCommand(commandKey = "getBookByUuid", groupKey = "book")
  public BookDto getByUuid(String bookUuid) {
    try {
      return restTemplate.getForObject("http://bookstore-books-app/api/book/uuid/" + bookUuid, BookDto.class);
    } catch (HttpClientErrorException httpClientErrorException) {
      if (httpClientErrorException.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Given bookUuid <" + bookUuid + "> does not exist!");
      }
      throw httpClientErrorException;
    }
  }
}