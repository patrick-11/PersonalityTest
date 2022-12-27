package com.github.personalitytest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {

  public static final String ENTRY_NOT_FOUND = "Entry not found!";
  public static final String ENTRY_NOT_VALID = "Entry not valid!";

  public static final String USER_DOES_NOT_EXIST = "User does not exist!";
  public static final String RESULT_DOES_NOT_EXIST = "Result does not exist!";

  public static final String USER_GET_NOT_FOUND = "User not found. Fetch was unsuccessful!";
  public static final String RESULT_GET_NOT_FOUND = "Result not found. Fetch was unsuccessful!";
  public static final String USER_UPDATE_NOT_FOUND = "User not found. Update was unsuccessful!";
  public static final String RESULT_UPDATE_NOT_FOUND = "Result not found. Update was unsuccessful!";

  public static final String USER_VALIDATION_NAME_EMPTY_ERROR = "Name may not be empty!";
  public static final String USER_VALIDATION_GENDER_EMPTY_ERROR = "Gender may not be empty!";
  public static final String RESULT_VALIDATION_ANSWER_EMPTY_ERROR = "Answer may not be empty!";

  public static final String USER_VALIDATION_NAME_SIZE_ERROR = "Name is not between 2 and 10 characters long!";
  public static final String USER_VALIDATION_GENDER_ERROR = "Gender is not male or female!";
  public static final String USER_VALIDATION_AGE_SIZE_ERROR = "Age must be between 5 and 120!";
  public static final String RESULT_VALIDATION_ANSWER_SIZE_ERROR = "Answers must have 10 entries!";
  public static final String RESULT_VALIDATION_ANSWER_VALUE_ERROR = "Answers must have a value between 1 and 7!";

  public static final String GENDER_MAPPING_ERROR = "Gender value cannot be mapped to an Gender type!";

  private String message;
  private List<String> details;
}
