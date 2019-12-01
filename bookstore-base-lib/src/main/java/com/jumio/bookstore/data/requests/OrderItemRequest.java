package com.jumio.bookstore.data.requests;

import lombok.*;
import lombok.experimental.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Accessors(chain = true)
public class OrderItemRequest {
  @NotBlank
  private String bookUuid;
  @NotNull
  @Min(1)
  private Integer quantity;
}