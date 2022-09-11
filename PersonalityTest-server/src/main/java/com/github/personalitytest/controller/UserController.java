package com.github.personalitytest.controller;


import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@Controller
@CrossOrigin
@RequestMapping("api/users")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/")
  public ResponseEntity<List<UserDto>> getUsers() {
    return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUser(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
  }

  @PostMapping("/")
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user) {
    return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
  }

  @PostMapping("/{id}")
  public ResponseEntity<UserDto> createUserById(@PathVariable("id") UUID id,@Valid @RequestBody UserDto user) {
    return new ResponseEntity<>(userService.create(id, user), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(@PathVariable("id") UUID id,@Valid @RequestBody UserDto user) {
    return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteUser(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
  }
}
