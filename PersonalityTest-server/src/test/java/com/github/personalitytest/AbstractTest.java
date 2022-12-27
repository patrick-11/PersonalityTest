package com.github.personalitytest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;


public abstract class AbstractTest {

  protected final String NAME_1 = "Patrick";
  protected final String NAME_2 = "Hannes";
  protected final int AGE_1 = 25;
  protected final int AGE_2 = 24;
  protected final List<Integer> ANSWERS_1 = List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
  protected final List<Integer> ANSWERS_2 = List.of(4, 4, 4, 4, 4, 4, 4, 4, 4, 4);
  protected final List<Double> RESULTS_2 = List.of(4.0, 4.0, 4.0, 4.0, 4.0);
  protected final double AVG_SCORE_2 = 4.0;
  protected final Timestamp TIMESTAMP = Timestamp.valueOf("2022-01-01 12:00:00");

  @Autowired
  protected MockMvc mvc;

  protected String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, clazz);
  }
}
