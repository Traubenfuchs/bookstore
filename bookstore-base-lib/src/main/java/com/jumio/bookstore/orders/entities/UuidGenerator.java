package com.jumio.bookstore.orders.entities;

import org.hibernate.*;
import org.hibernate.engine.spi.*;
import org.hibernate.id.*;

import java.io.*;
import java.util.*;

public class UuidGenerator implements IdentifierGenerator {
  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
    BaseEntity<?> be = (BaseEntity) object;

    if (be.getUuid() == null || be.getUuid().length() == 0) {
      return UUID.randomUUID().toString();
    }
    return be.getUuid();
  }
}