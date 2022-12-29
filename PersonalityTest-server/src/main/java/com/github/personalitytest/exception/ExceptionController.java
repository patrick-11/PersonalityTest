package com.github.personalitytest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Comparator;


@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler(value = NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException exception) {
    var details = new ArrayList<String>();
    details.add(exception.getLocalizedMessage());
    return generateErrorResponse(ErrorResponse.ENTRY_NOT_FOUND, details, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = NotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleNotValid(NotValidException exception) {
    var details = new ArrayList<String>();
    details.add(exception.getLocalizedMessage());
    return generateErrorResponse(ErrorResponse.ENTRY_NOT_VALID, details, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
    var details = new ArrayList<String>();
    exception.getBindingResult().getAllErrors().forEach(objectError -> details.add(objectError.getDefaultMessage()));
    return generateErrorResponse(ErrorResponse.ENTRY_NOT_VALID, details, HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<ErrorResponse> generateErrorResponse(String message, ArrayList<String> details, HttpStatus httpStatus) {
    details.sort(Comparator.naturalOrder());
    return new ResponseEntity<>(new ErrorResponse(message, details), httpStatus);
  }
}
