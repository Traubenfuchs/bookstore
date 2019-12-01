package com.jumio.bookstore.data.requests;

import com.jumio.bookstore.validators.*;
import lombok.*;
import lombok.experimental.*;

import javax.validation.constraints.*;
import java.util.*;

@Getter
@Setter
@Accessors(chain = true)
public class OrderRequest {
  @UniqueBookUuidAmongOrderItems
  @NotEmpty
  private Set<OrderItemRequest> orderItemRequests = new HashSet<>();
  @NotBlank
  private String address;
}