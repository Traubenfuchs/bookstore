package com.jumio.bookstore.orders.clients;

import com.jumio.bookstore.data.dtos.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.http.*;
import org.springframework.web.client.*;
import org.springframework.web.server.*;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookClientTests {
  @Mock
  private RestTemplate restTemplate;
  @InjectMocks
  private BookClient bookClient;


  @Test
  void testNotFound() {
    Mockito.when(restTemplate.getForObject(eq("http://bookstore-books-app/api/book/uuid/" + "notExistingUuid"), eq(BookDto.class)))
      .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND) {
      });

    Assertions.assertThrows(ResponseStatusException.class, () -> bookClient.getByUuid("notExistingUuid"));
  }

  @Test
  void testOtherClientError() {
    Mockito.when(restTemplate.getForObject(eq("http://bookstore-books-app/api/book/uuid/" + "notExistingUuid"), eq(BookDto.class)))
      .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST) {
      });

    Assertions.assertThrows(HttpClientErrorException.class, () -> bookClient.getByUuid("notExistingUuid"));
  }
}