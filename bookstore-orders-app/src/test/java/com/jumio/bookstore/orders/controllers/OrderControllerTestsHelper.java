package com.jumio.bookstore.orders.controllers;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.jumio.bookstore.data.dtos.*;
import com.jumio.bookstore.data.requests.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.test.web.servlet.*;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Lazy
@Component
public class OrderControllerTestsHelper {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper om;

  ResultActions postRaw(OrderRequest orderRequest) throws Exception {
    return mockMvc.perform(post("/api/order")
      .contentType(MediaType.APPLICATION_JSON)
      .content(om.writeValueAsBytes(orderRequest)));
  }

  OrderDto postSuccess(OrderRequest orderRequest) throws Exception {
    return om.readValue(postRaw(orderRequest)
        .andExpect(status().isCreated()).
          andReturn().getResponse().getContentAsByteArray()
      , OrderDto.class);
  }

  ResultActions putRaw(OrderRequest orderRequest, String orderUuid) throws Exception {
    return mockMvc.perform(put("/api/order/" + orderUuid)
      .contentType(MediaType.APPLICATION_JSON)
      .content(om.writeValueAsBytes(orderRequest)));
  }

  OrderDto putSuccess(OrderRequest orderRequest, String orderUuid) throws Exception {
    return om.readValue(putRaw(orderRequest, orderUuid)
        .andExpect(status().isOk()).
          andReturn().getResponse().getContentAsByteArray()
      , OrderDto.class);
  }

  ResultActions getOrderRaw(String orderUuid) throws Exception {
    return mockMvc.perform(get("/api/order/" + orderUuid));
  }

  OrderDto getOrderSuccess(String orderUuid) throws Exception {
    return om.readValue(getOrderRaw(orderUuid)
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsByteArray(),
      OrderDto.class);
  }

  ResultActions getMyOrdersRaw() throws Exception {
    return mockMvc.perform(get("/api/orders"));
  }

  Set<OrderDto> getMyOrdersSuccess() throws Exception {
    return om.readValue(getMyOrdersRaw()
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsByteArray(),
      new TypeReference<>() {
      });
  }

  ResultActions deleteRaw(String orderUuid) throws Exception {
    return mockMvc.perform(delete("/api/order/" + orderUuid));
  }

  void deleteSuccess(String orderUuid) throws Exception {
    deleteRaw(orderUuid).andExpect(status().isAccepted());
  }
}