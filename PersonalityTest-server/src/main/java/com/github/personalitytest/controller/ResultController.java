package com.github.personalitytest.controller;

import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.service.ResultService;
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
@RequestMapping("api/results")
public class ResultController implements ControllerBasic<ResultDto> {

  private final ResultService resultService;

  @Override
  @Operation(summary = "Get all results")
  @GetMapping("/")
  public ResponseEntity<List<ResultDto>> getAll() {
    return new ResponseEntity<>(resultService.getAll(), HttpStatus.OK);
  }

  @Override
  @Operation(summary = "Get a result by id")
  @GetMapping("/{id}")
  public ResponseEntity<ResultDto> get(@Parameter(description = "Id of result to be searched")
                                       @PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.get(id), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ResultDto> create(ResultDto dto) {
    return null;
  }

  @Override
  @Operation(summary = "Create a result")
  @PostMapping("/{userId}")
  public ResponseEntity<ResultDto> create(@Parameter(description = "Id of user of the result")
                                          @PathVariable("userId") UUID userId,
                                          @Parameter(description = "Result of the test")
                                          @Valid @RequestBody ResultDto resultDto) {
    return new ResponseEntity<>(resultService.create(userId, resultDto), HttpStatus.CREATED);
  }

  @Override
  @Operation(summary = "Update a result")
  @PutMapping("/{id}")
  public ResponseEntity<ResultDto> update(@Parameter(description = "Id of result to be updated")
                                          @PathVariable("id") UUID id,
                                          @Parameter(description = "Result of the test")
                                          @Valid @RequestBody ResultDto resultDto) {
    return new ResponseEntity<>(resultService.update(id, resultDto), HttpStatus.OK);
  }

  @Override
  @Operation(summary = "Delete a result")
  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@Parameter(description = "Id of result to be deleted")
                                        @PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.delete(id), HttpStatus.OK);
  }
}
