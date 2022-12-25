package com.github.personalitytest.service;

import java.util.List;
import java.util.UUID;


public interface ServiceBasic<T> {

  List<T> getAll();

  T get(UUID id);

  T create(T dto);

  T create(UUID id, T dto);

  T update(UUID id, T dto);

  boolean delete(UUID id);

  boolean exists(UUID id);
}
