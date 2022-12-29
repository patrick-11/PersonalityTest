package com.github.personalitytest.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ControllerBasic<T> {

  ResponseEntity<List<T>> getAll();

  ResponseEntity<T> get(UUID id);

  ResponseEntity<T> create(T dto);

  ResponseEntity<T> create(UUID id, T dto);

  ResponseEntity<T> update(UUID id, T dto);

  ResponseEntity<Boolean> delete(UUID id);
}
