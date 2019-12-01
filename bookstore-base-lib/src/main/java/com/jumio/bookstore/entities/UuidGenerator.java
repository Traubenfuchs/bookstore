package com.jumio.bookstore.entities;

import org.hibernate.engine.spi.*;
import org.hibernate.id.*;

import java.io.*;
import java.util.*;

public class UuidGenerator implements IdentifierGenerator {
  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) {
    BaseEntity<?> be = (BaseEntity) object;

    if (be.getUuid() == null || be.getUuid().length() == 0) {
      return UUID.randomUUID().toString();
    }
    return be.getUuid();
  }
}