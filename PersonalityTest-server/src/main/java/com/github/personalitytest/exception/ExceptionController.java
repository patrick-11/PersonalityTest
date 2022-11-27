package com.github.personalitytest.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Comparator;


@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<Object> exception(NotFoundException exception) {
    var details = new ArrayList<String>();
    details.add(exception.getLocalizedMessage());
    return new ResponseEntity<>(new ErrorResponse(ErrorResponse.ENTRY_NOT_FOUND, details), HttpStatus.NOT_FOUND);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    var details = new ArrayList<String>();
    exception.getBindingResult().getAllErrors().forEach(objectError -> details.add(objectError.getDefaultMessage()));
    details.sort(Comparator.naturalOrder());
    return new ResponseEntity<>(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID, details), HttpStatus.BAD_REQUEST);
  }

}
