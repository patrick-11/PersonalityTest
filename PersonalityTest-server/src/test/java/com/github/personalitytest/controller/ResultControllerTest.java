package com.github.personalitytest.controller;

import com.github.personalitytest.AbstractTest;
import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ResultControllerTest extends AbstractTest {

  protected final static String uri = "/api/results/";

  private ResultDto result;

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Before
  public void createUserAndResultBefore() throws Exception {
    UserDto user = new UserDto();
    user.setId(UUID.randomUUID());
    user.setName("Patrick");
    user.setGender("Male");
    user.setAge(25);

    ResultDto result = new ResultDto();
    result.setId(UUID.randomUUID());
    result.setUser(user);
    result.setAnswers(List.of(4, 5, 7, 5, 6, 4, 5, 6, 6, 4));
    this.result = result;

    String inputJson = super.mapToJson(result.getUser());
    mvc.perform(MockMvcRequestBuilders.post(UserControllerTest.uri + result.getUser().getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    inputJson = super.mapToJson(result);
    mvc.perform(MockMvcRequestBuilders.post(uri + result.getUser().getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
  }

  @Test
  public void getResults() throws Exception {
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    ResultDto[] results = super.mapFromJson(content, ResultDto[].class);
    assertTrue(results.length > 0);
  }

  @Test
  public void createResult() throws Exception {
    String inputJson = super.mapToJson(result);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri + result.getUser().getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(201, status);
  }

  @Test
  public void updateResult() throws Exception {
    result.setAnswers(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1));

    String inputJson = super.mapToJson(result);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + result.getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
  }

  @Test
  public void deleteResult() throws Exception {
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri + result.getId())).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    assertEquals(Boolean.toString(true), content);
  }
}
