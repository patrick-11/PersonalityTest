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
public class ResultController implements ControllerBasic<ResultDto> {

  @Autowired
  ResultService resultService;

  @Override
  @GetMapping("/")
  public ResponseEntity<List<ResultDto>> getAll() {
    return new ResponseEntity<>(resultService.getAll(), HttpStatus.OK);
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<ResultDto> get(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.get(id), HttpStatus.OK);
  }

  @Override
  public ResponseEntity<ResultDto> create(ResultDto dto) {
    return null;
  }

  @PostMapping("/{userId}")
  public ResponseEntity<ResultDto> create(@PathVariable("userId") UUID userId, @Valid @RequestBody ResultDto resultDto) {
    return new ResponseEntity<>(resultService.create(userId, resultDto), HttpStatus.CREATED);
  }

  @Override
  @PutMapping("/{id}")
  public ResponseEntity<ResultDto> update(@PathVariable("id") UUID id, @Valid @RequestBody ResultDto resultDto) {
    return new ResponseEntity<>(resultService.update(id, resultDto), HttpStatus.OK);
  }

  @Override
  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable("id") UUID id) {
    return new ResponseEntity<>(resultService.delete(id), HttpStatus.OK);
  }
}
