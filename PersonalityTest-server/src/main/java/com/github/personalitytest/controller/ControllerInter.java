package com.github.personalitytest.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ControllerInter<T> {

  ResponseEntity<List<T>> getAll();

  ResponseEntity<T> get(UUID uuid);

  ResponseEntity<T> create(T dto);

  ResponseEntity<T> update(UUID uuid, T dto);

  ResponseEntity<Boolean> delete(UUID uuid);
}
