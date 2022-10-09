package com.github.personalitytest.controller;

import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.service.ResultService;
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
@RequestMapping("api/results")
public class ResultController {

  @Autowired
  ResultService resultService;

  @GetMapping("/")
  public ResponseEntity<List<ResultDto>> getResults() {
    return new ResponseEntity<>(resultService.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResultDto> getResult(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.get(id), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<ResultDto>> getResultsByUserId(@PathVariable("userId") UUID userId) {
    return new ResponseEntity<>(resultService.getByUserId(userId), HttpStatus.OK);
  }

  @PostMapping("/{userId}")
  public ResponseEntity<ResultDto> createResult(@PathVariable("userId") UUID userId,@Valid @RequestBody ResultDto resultDto) {
    return new ResponseEntity<>(resultService.create(userId, resultDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResultDto> updateResult(@PathVariable("id") UUID id,@Valid @RequestBody ResultDto resultDto) {
    return new ResponseEntity<>(resultService.update(id, resultDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteResult(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.delete(id), HttpStatus.OK);
  }

  @DeleteMapping("/user/{userId}")
  public ResponseEntity<Boolean> deleteResultsByUserId(@PathVariable("userId") UUID userId) {
    return new ResponseEntity<>(resultService.deleteByUserId(userId), HttpStatus.OK);
  }
}
