package com.jumio.bookstore.validators;

import com.jumio.bookstore.data.requests.*;

import javax.validation.*;
import java.util.*;

public class UniqueBookUuidAmongOrderItemsValidator implements ConstraintValidator<UniqueBookUuidAmongOrderItems, Collection<OrderItemRequest>> {
  @Override
  public boolean isValid(
    Collection<OrderItemRequest> value,
    ConstraintValidatorContext context) {

    Set<String> alreadyFoundBooks = new HashSet<>();

    for (OrderItemRequest oicr : value) {
      if (alreadyFoundBooks.add(oicr.getBookUuid())) {
        continue;
      }

      context.disableDefaultConstraintViolation();

      context.buildConstraintViolationWithTemplate("" +
        "bookUuid<" +
        oicr.getBookUuid() +
        "> contained more than once in the given Order Items.")
        .addConstraintViolation();

      return false;
    }
    return true;
  }
}
