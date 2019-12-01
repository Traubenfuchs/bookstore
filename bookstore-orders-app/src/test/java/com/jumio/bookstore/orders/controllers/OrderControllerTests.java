package com.jumio.bookstore.orders.controllers;

import com.jumio.bookstore.data.dtos.*;
import com.jumio.bookstore.data.requests.*;
import com.jumio.bookstore.enums.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.security.test.context.support.*;
import org.springframework.test.context.*;
import org.springframework.web.client.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests all possible paths.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@WithMockUser(authorities = {"SCOPE_write:orders", "SCOPE_read:orders"})
class OrderControllerTests {
  @Autowired
  private OrderControllerTestsHelper orderControllerTestsHelper;
  @MockBean
  private RestTemplate restTemplate;

  @Test
  void postAndGetSuccess() throws Exception {
    OrderRequest orderRequest = createTestOrderRequest1AndSetupBookMock();
    OrderItemRequest orderItemRequest = orderRequest.getOrderItemRequests().iterator().next();

    String uuidOfNewOrder = orderControllerTestsHelper.postSuccess(orderRequest).getUuid();

    OrderDto loadedOrderDto = orderControllerTestsHelper.getOrderSuccess(uuidOfNewOrder);
    Assertions.assertEquals(orderRequest.getOrderItemRequests().size(), loadedOrderDto.getOrderItems().size());
    OrderItemDto loadedOrderItemCreationRequest = loadedOrderDto.getOrderItems().iterator().next();

    Assertions.assertEquals(orderRequest.getAddress(), loadedOrderDto.getAddress());
    Assertions.assertEquals(orderItemRequest.getQuantity(), loadedOrderItemCreationRequest.getQuantity());
    Assertions.assertEquals(orderItemRequest.getBookUuid(), loadedOrderItemCreationRequest.getBookUuid());
  }

  @Test
  void putSuccess() throws Exception {
    OrderRequest orderRequest = createTestOrderRequest1AndSetupBookMock();
    OrderItemRequest orderItemRequest = orderRequest.getOrderItemRequests().iterator().next();

    String uuidOfNewOrder = orderControllerTestsHelper.postSuccess(orderRequest).getUuid();

    orderRequest.setAddress("Kleine Straße 5");
    orderItemRequest.setBookUuid(UUID.randomUUID().toString());
    mockBookServiceRestTemplateForBookUuid(orderItemRequest.getBookUuid());
    orderControllerTestsHelper.putSuccess(orderRequest, uuidOfNewOrder);

    OrderDto loadedOrderDto = orderControllerTestsHelper.getOrderSuccess(uuidOfNewOrder);
    Assertions.assertEquals(orderRequest.getOrderItemRequests().size(), loadedOrderDto.getOrderItems().size());
    OrderItemDto loadedOrderItemCreationRequest = loadedOrderDto.getOrderItems().iterator().next();

    Assertions.assertEquals(orderRequest.getAddress(), loadedOrderDto.getAddress());
    Assertions.assertEquals(orderItemRequest.getBookUuid(), loadedOrderItemCreationRequest.getBookUuid());
  }


  @Test
  @WithMockUser(username = "getMyOrdersSuccess", authorities = {"SCOPE_write:orders", "SCOPE_read:orders"})
  void getMyOrdersSuccess() throws Exception {
    OrderRequest orderRequest1 = createTestOrderRequest1AndSetupBookMock();
    OrderItemRequest orderItemRequest1 = orderRequest1.getOrderItemRequests().iterator().next();

    OrderRequest orderRequest2 = createTestOrderRequest1AndSetupBookMock();
    OrderItemRequest orderItemRequest2 = orderRequest2.getOrderItemRequests().iterator().next();

    String uuidOfNewOrder1 = orderControllerTestsHelper.postSuccess(orderRequest1).getUuid();
    String uuidOfNewOrder2 = orderControllerTestsHelper.postSuccess(orderRequest2).getUuid();

    Set<OrderDto> myOrders = orderControllerTestsHelper.getMyOrdersSuccess();
    assertThat(myOrders)
      .hasSize(2)
      .anyMatch(orderDto -> orderDto.getUuid().equals(uuidOfNewOrder1))
      .anyMatch(orderDto -> orderDto.getUuid().equals(uuidOfNewOrder2));
  }

  @Test
  void deleteSuccess() throws Exception {
    OrderRequest orderRequest1 = createTestOrderRequest1AndSetupBookMock();
    String uuidOfNewOrder = orderControllerTestsHelper.postSuccess(orderRequest1).getUuid();
    OrderDto orderBeforeDeletion = orderControllerTestsHelper.getOrderSuccess(uuidOfNewOrder);
    Assertions.assertEquals(OrderState.CREATED, orderBeforeDeletion.getOrderState());
    orderControllerTestsHelper.deleteSuccess(uuidOfNewOrder);
    OrderDto orderAfterDeletion = orderControllerTestsHelper.getOrderSuccess(uuidOfNewOrder);
    Assertions.assertEquals(OrderState.CANCELLED, orderAfterDeletion.getOrderState());
  }

  @Test
  void deleteAlreadyDeleted() throws Exception {
    OrderRequest orderRequest1 = createTestOrderRequest1AndSetupBookMock();
    String uuidOfNewOrder = orderControllerTestsHelper.postSuccess(orderRequest1).getUuid();
    orderControllerTestsHelper.deleteSuccess(uuidOfNewOrder);
    orderControllerTestsHelper.deleteRaw(uuidOfNewOrder).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "user_id_3", authorities = {"SCOPE_write:orders", "SCOPE_read:orders"})
  void deleteAlreadyShipped() throws Exception {
    orderControllerTestsHelper.deleteRaw("O3").andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(username = "user_id_3", authorities = {"SCOPE_write:orders", "SCOPE_read:orders"})
  void getOtherUsersOrder() throws Exception {
    orderControllerTestsHelper.getOrderRaw("O1")
      .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username = "user_id_2", authorities = {"SCOPE_write:orders", "SCOPE_read:orders"})
  void updatingCancelledOrder() throws Exception {
    OrderRequest orderRequest1 = createTestOrderRequest1AndSetupBookMock();
    orderControllerTestsHelper.putRaw(orderRequest1, "O2")
      .andExpect(status().isBadRequest());
  }

  @Test
  void getNonExistingOrder() throws Exception {
    orderControllerTestsHelper.getOrderRaw("nonexisting")
      .andExpect(status().isNotFound());
  }

  /**
   * book in partner system does not exist
   */
  @Test
  void postBookNotFound() throws Exception {
    OrderRequest orderRequest1 = createTestOrderRequest1AndSetupBookMock();
    orderRequest1.getOrderItemRequests().iterator().next().setBookUuid("xxx");
    orderControllerTestsHelper.postRaw(orderRequest1).andExpect(status().isBadRequest());
  }

  /**
   * book in partner system does not exist
   */
  @Test
  void putBookNotFound() throws Exception {
    OrderRequest orderRequest1 = createTestOrderRequest1AndSetupBookMock();
    String uuidOfNewOrder = orderControllerTestsHelper.postSuccess(orderRequest1).getUuid();
    orderRequest1.getOrderItemRequests().iterator().next().setBookUuid("xxx");
    orderControllerTestsHelper.putRaw(orderRequest1, uuidOfNewOrder).andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = {"wrong authority"})
  void post403() throws Exception {
    orderControllerTestsHelper.postRaw(new OrderRequest())
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = {"wrong authority"})
  void put403() throws Exception {
    orderControllerTestsHelper.putRaw(new OrderRequest(), "xxx")
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = {"wrong authority"})
  void getOrder403() throws Exception {
    orderControllerTestsHelper.getOrderRaw("xyt")
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = {"wrong authority"})
  void getMyOrders403() throws Exception {
    orderControllerTestsHelper.getMyOrdersRaw()
      .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(authorities = {"wrong authority"})
  void delete403() throws Exception {
    orderControllerTestsHelper.deleteRaw("")
      .andExpect(status().isForbidden());
  }

  @Test
  void twoIdenticalOrderItemBookUuids() throws Exception {
    OrderRequest orderRequest1 = createTestOrderRequest1AndSetupBookMock();
    orderRequest1.getOrderItemRequests().add(new OrderItemRequest().setBookUuid("a").setQuantity(1));
    orderRequest1.getOrderItemRequests().add(new OrderItemRequest().setBookUuid("a").setQuantity(2));
    orderControllerTestsHelper.postRaw(orderRequest1).andExpect(status().isBadRequest());
  }

  private OrderRequest createTestOrderRequest1() {
    OrderRequest orderRequest = new OrderRequest()
      .setAddress("Tiefer Graben 19/8 1010 Wien");
    OrderItemRequest orderItemRequest = new OrderItemRequest()
      .setBookUuid(UUID.randomUUID().toString())
      .setQuantity(5);
    orderRequest.getOrderItemRequests().add(orderItemRequest);
    return orderRequest;
  }

  private void mockBookServiceRestTemplateForBookUuid(String uuid) {
    Mockito.when(restTemplate.getForObject(eq("http://bookstore-books-app/api/book/uuid/" + uuid), eq(BookDto.class)))
      .thenReturn(new BookDto());
  }

  private OrderRequest createTestOrderRequest1AndSetupBookMock() {
    OrderRequest orderRequest = createTestOrderRequest1();
    OrderItemRequest orderItemRequest = orderRequest.getOrderItemRequests().iterator().next();
    mockBookServiceRestTemplateForBookUuid(orderItemRequest.getBookUuid());
    return orderRequest;
  }
}