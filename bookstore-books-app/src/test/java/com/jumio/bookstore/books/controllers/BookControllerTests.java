package com.jumio.bookstore.books.controllers;

import com.fasterxml.jackson.databind.*;
import com.jumio.bookstore.data.dtos.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;

import java.time.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests all possible paths.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@WithMockUser(authorities = "SCOPE_read:books")
class BookControllerTests {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getAllSuccess() throws Exception {
    byte[] resultRaw = mockMvc.perform(get("/api/books"))
      .andExpect(status().is(200))
      .andReturn().getResponse().getContentAsByteArray();

    List<BookDto> books = Arrays.asList(objectMapper.readValue(resultRaw, BookDto[].class));

    assertThat(books)
      .hasSize(3)
      .containsExactlyInAnyOrder(
        testBook1(),
        testBook2(),
        testBook3());
  }

  @Test
  void getByUuidSuccess() throws Exception {
    byte[] resultRaw = mockMvc.perform(get("/api/book/uuid/9a020cba-3786-429f-8843-2723a57a8b8a"))
      .andExpect(status().is(200))
      .andReturn().getResponse().getContentAsByteArray();
    BookDto result = objectMapper.readValue(resultRaw, BookDto.class);

    Assertions.assertEquals(result, testBook1());
  }

  @Test
  void getByIsbnSuccess() throws Exception {
    byte[] resultRaw = mockMvc.perform(get("/api/book/isbn/9783551354020"))
      .andExpect(status().is(200))
      .andReturn().getResponse().getContentAsByteArray();
    BookDto result = objectMapper.readValue(resultRaw, BookDto.class);

    Assertions.assertEquals(result, testBook2());
  }

  @Test
  void getByUuidNotFound() throws Exception {
    mockMvc.perform(get("/api/book/uuid/xxx"))
      .andExpect(status().is(404));
  }

  @Test
  void getByIsbnNotFound() throws Exception {
    mockMvc.perform(get("/api/book/isbn/xxx"))
      .andExpect(status().is(404));
  }

  @WithMockUser(authorities = "xxx")
  @Test
  void getAll403() throws Exception {
    mockMvc.perform(get("/api/book/isbn/xxx"))
      .andExpect(status().is(403));
  }

  @WithMockUser(authorities = "xxx")
  @Test
  void getByIsbn403() throws Exception {
    mockMvc.perform(get("/api/book/isbn/xxx"))
      .andExpect(status().is(403));
  }

  @WithMockUser(authorities = "xxx")
  @Test
  void getByUuid403() throws Exception {
    mockMvc.perform(get("/api/book/isbn/xxx"))
      .andExpect(status().is(403));
  }

  private BookDto testBook1() {
    return new BookDto()
      .setUuid("9a020cba-3786-429f-8843-2723a57a8b8a")
      .setTitle("Harry Potter und der Stein der Weisen (Harry Potter 1)")
      .setIsbn("9783551557414")
      .setPrice(20000)
      .setDtUpdate(Instant.parse("2019-06-21T14:57:17+00:00.00Z"))
      .setDtCreation(Instant.parse("2019-06-21T14:57:17+00:00.00Z"));
  }

  private BookDto testBook2() {
    return new BookDto()
      .setUuid("b22498f5-c3de-410f-9d61-5ccf9c30f18b")
      .setTitle("Harry Potter und die Kammer des Schreckens")
      .setIsbn("9783551354020")
      .setPrice(899)
      .setDtUpdate(Instant.parse("2019-06-21T14:57:17+00:00.00Z"))
      .setDtCreation(Instant.parse("2019-06-21T14:57:17+00:00.00Z"));
  }

  private BookDto testBook3() {
    return new BookDto()
      .setUuid("3ff8c6b0-27e7-4c80-8e72-903ecec6e8e1")
      .setTitle("Das inoffizielle Harry-Potter-Buch der Zauberei")
      .setIsbn("9783742301901")
      .setPrice(25000)
      .setDtUpdate(Instant.parse("2019-06-21T14:57:17+00:00.00Z"))
      .setDtCreation(Instant.parse("2019-06-21T14:57:17+00:00.00Z"));
  }
}