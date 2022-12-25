package com.github.personalitytest.controller;


import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.service.ResultService;
import com.github.personalitytest.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@Controller
@AllArgsConstructor
@CrossOrigin
@RequestMapping("api/users")
public class UserController implements ControllerBasic<UserDto> {

  @Autowired
  UserService userService;
  @Autowired
  ResultService resultService;

  @GetMapping("/")
  public ResponseEntity<List<UserDto>> getAll() {
    return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> get(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
  }

  @GetMapping("/{id}/results")
  public ResponseEntity<List<ResultDto>> getResults(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.getByUserId(id), HttpStatus.OK);
  }

  @Override
  @PostMapping("/")
  public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto user) {
    return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
  }

  @PostMapping("/{id}")
  public ResponseEntity<UserDto> createById(@PathVariable("id") UUID id, @Valid @RequestBody UserDto user) {
    return new ResponseEntity<>(userService.create(id, user), HttpStatus.CREATED);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<UserDto> update(@PathVariable("id") UUID id, @Valid @RequestBody UserDto user) {
    return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}/results")
  public ResponseEntity<Boolean> deleteResults(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.deleteByUserId(id), HttpStatus.OK);
  }
}
