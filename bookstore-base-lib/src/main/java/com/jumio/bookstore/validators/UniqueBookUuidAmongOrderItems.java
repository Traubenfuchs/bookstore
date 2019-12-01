package com.jumio.bookstore.validators;

import javax.validation.*;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueBookUuidAmongOrderItemsValidator.class)
public @interface UniqueBookUuidAmongOrderItems {
  String message() default "At least one bookUuid exists twice in the given order items.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
