package com.github.personalitytest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.personalitytest.AbstractTest;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class UserControllerTest extends AbstractTest {

  protected final static String uri = "/api/users/";

  private UserDto user;

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Before
  public void createUserBefore() throws Exception {
    UserDto user = new UserDto();
    user.setId(UUID.randomUUID());
    user.setName("Patrick");
    user.setGender("Male");
    user.setAge(25);
    this.user = user;

    String inputJson = super.mapToJson(user);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri + user.getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(201, status);
  }

  @Test
  public void getUsers() throws Exception {
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    UserDto[] users = super.mapFromJson(content, UserDto[].class);
    assertTrue(users.length > 0);
  }

  @Test
  public void getUser() throws Exception {
    String inputJson = super.mapToJson(user);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri + user.getId())
        .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    assertEquals(content, inputJson);
  }

  @Test
  public void createUser() throws Exception {
    String inputJson = super.mapToJson(user);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri + user.getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(201, status);

    String content = mvcResult.getResponse().getContentAsString();
    assertEquals(content, inputJson);
  }

  @Test
  public void updateUser() throws Exception {
    user.setName("Peter");

    String inputJson = super.mapToJson(user);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri + user.getId())
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    assertEquals(inputJson, content);
  }

  @Test
  public void deleteUser() throws Exception {
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri + user.getId())).andReturn();

    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);

    String content = mvcResult.getResponse().getContentAsString();
    assertEquals(Boolean.toString(true), content);
  }
}
