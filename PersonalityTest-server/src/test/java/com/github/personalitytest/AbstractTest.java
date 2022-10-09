package com.github.personalitytest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;


public abstract class AbstractTest {

  @Autowired
  protected MockMvc mvc;
  protected final static String ENTRY_NOT_FOUND = "Entry not found!";
  protected final static String ENTRY_NOT_VALID = "Entry not valid!";
  protected final static String USER_DOES_NOT_EXIST = "User does not exist!";
  protected final static String RESULT_DOES_NOT_EXIST = "Result does not exist!";
  protected final static String USER_GET_NOT_FOUND = "User not found. Fetch was unsuccessful!";
  protected final static String RESULT_GET_NOT_FOUND = "Result not found. Fetch was unsuccessful!";
  protected final static String USER_UPDATE_NOT_FOUND = "User not found. Update was unsuccessful!";
  protected final static String RESULT_UPDATE_NOT_FOUND = "Result not found. Update was unsuccessful!";
  protected final static String USER_VALIDATION_NAME_ERROR = "Name is not between 2 and 10 characters long!";
  protected final static String USER_VALIDATION_GENDER_ERROR = "Gender is not male or female!";
  protected final static String USER_VALIDATION_AGE_ERROR = "Age must be between 5 and 120!";
  protected final static String RESULT_VALIDATION_ANSWER_SIZE_ERROR = "Answers must have 10 entries!";
  protected final static String RESULT_VALIDATION_ANSWER_VALUE_ERROR = "Answer on position 4 must be between 1 and 7!";


  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, clazz);
  }
}
