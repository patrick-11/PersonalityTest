package com.github.personalitytest.controller;


import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.service.ResultService;
import com.github.personalitytest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/v1/users")
public class UserController implements ControllerBasic<UserDto> {

  private final UserService userService;
  private final ResultService resultService;

  @Override
  @Operation(summary = "Get all users")
  @GetMapping("/")
  public ResponseEntity<List<UserDto>> getAll() {
    return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
  }

  @Override
  @Operation(summary = "Get an user by id")
  @GetMapping("/{id}")
  public ResponseEntity<UserDto> get(@Parameter(description = "Id of user to be searched")
                                     @PathVariable("id") UUID id) {
    return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
  }

  @Operation(summary = "Get all results of an user by id")
  @GetMapping("/{id}/results")
  public ResponseEntity<List<ResultDto>> getResults(@Parameter(description = "Id of user to be searched")
                                                    @PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.getByUserId(id), HttpStatus.OK);
  }

  @Override
  @Operation(summary = "Create an user")
  @PostMapping("/")
  public ResponseEntity<UserDto> create(@Parameter(description = "User to be created")
                                        @Valid @RequestBody UserDto user) {
    return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
  }

  @Override
  @Operation(summary = "Create an user by id")
  @PostMapping("/{id}")
  public ResponseEntity<UserDto> create(@Parameter(description = "Id of user to be created")
                                        @PathVariable("id") UUID id,
                                        @Parameter(description = "User to be created")
                                        @Valid @RequestBody UserDto user) {
    return new ResponseEntity<>(userService.create(id, user), HttpStatus.CREATED);
  }

  @Override
  @Operation(summary = "Update an user by id")
  @PutMapping("/{id}")
  public ResponseEntity<UserDto> update(@Parameter(description = "Id of user to be updated")
                                        @PathVariable("id") UUID id,
                                        @Parameter(description = "User to be updated")
                                        @Valid @RequestBody UserDto user) {
    return new ResponseEntity<>(userService.update(id, user), HttpStatus.OK);
  }

  @Override
  @Operation(summary = "Delete an user by id")
  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@Parameter(description = "Id of user to be deleted")
                                        @PathVariable("id") UUID id) {
    return new ResponseEntity<>(userService.delete(id), HttpStatus.OK);
  }

  @Operation(summary = "Delete all results of an user by id")
  @DeleteMapping("/{id}/results")
  public ResponseEntity<Boolean> deleteResults(@Parameter(description = "Id of user where results to be deleted")
                                               @PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.deleteByUserId(id), HttpStatus.OK);
  }
}
