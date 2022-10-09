package com.github.personalitytest.controller;

import com.github.personalitytest.AbstractTest;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.exception.NotValidException;
import com.github.personalitytest.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(UserController.class)
class UserControllerTest extends AbstractTest {

  protected final static String uri = "/api/users/";
  @MockBean
  UserService userService;
  UserController userController;
  AutoCloseable autoCloseable;
  private UserDto userDto1;
  private UserDto userDto2;


  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    userController = new UserController(userService);

    userDto1 = new UserDto(UUID.randomUUID(), "Patrick", "Male", 25);
    userDto2 = new UserDto(UUID.randomUUID(), "Hannes", "Male", 24);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getUsers() throws Exception {
    when(userService.getAll()).thenReturn(List.of(userDto1, userDto2));

    MvcResult mvcResult = mvc.perform(get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(List.of(userDto1, userDto2)), content);
  }

  @Test
  void getUsers_UsersDoNotExist() throws Exception {
    when(userService.getAll()).thenReturn(List.of());

    MvcResult mvcResult = mvc.perform(get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(List.of()), content);
  }

  @Test
  void getUser() throws Exception {
    when(userService.get(ArgumentMatchers.any(UUID.class))).thenReturn(userDto1);

    MvcResult mvcResult = mvc.perform(get(uri + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(userDto1), content);
  }

  @Test
  void getUser_UserNotFound() throws Exception {
    when(userService.get(ArgumentMatchers.any(UUID.class))).thenThrow(new NotFoundException(USER_GET_NOT_FOUND));

    MvcResult mvcResult = mvc.perform(get(uri + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_FOUND, List.of(USER_GET_NOT_FOUND))), content);
  }

  @Test
  void createUser() throws Exception {
    when(userService.create(ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);

    MvcResult mvcResult = mvc.perform(post(uri)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(userDto1), content);
  }

  @Test
  void createUser_NoContent() throws Exception {
    when(userService.create(ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);
    MvcResult mvcResult = mvc.perform(post(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void createUser_UserNameNotValid() throws Exception {
    when(userService.create(ArgumentMatchers.any(UserDto.class)))
        .thenThrow(new NotValidException(USER_VALIDATION_NAME_ERROR));

    userDto1.setName("T");
    MvcResult mvcResult = mvc.perform(post(uri)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_VALID, List.of(USER_VALIDATION_NAME_ERROR))), content);
  }

  @Test
  void createUserById() throws Exception {
    when(userService.create(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);

    MvcResult mvcResult = mvc.perform(post(uri + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(userDto1), content);
  }

  @Test
  void createUserById_NoContent() throws Exception {
    when(userService.create(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);
    MvcResult mvcResult = mvc.perform(post(uri + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void createUserById_UserGenderNotValid() throws Exception {
    when(userService.create(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class)))
        .thenThrow(new NotValidException(USER_VALIDATION_GENDER_ERROR));

    userDto1.setGender("Non-Binary");
    MvcResult mvcResult = mvc.perform(post(uri + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_VALID, List.of(USER_VALIDATION_GENDER_ERROR))), content);
  }

  @Test
  void updateUser() throws Exception {
    when(userService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);

    MvcResult mvcResult = mvc.perform(put(uri + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(userDto1), content);
  }

  @Test
  void updateUser_NoContent() throws Exception {
    when(userService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);
    MvcResult mvcResult = mvc.perform(post(uri + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void updateUser_UserAgeNotValid() throws Exception {
    when(userService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class)))
        .thenThrow(new NotValidException(USER_VALIDATION_AGE_ERROR));

    userDto1.setAge(3);
    MvcResult mvcResult = mvc.perform(put(uri + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_VALID, List.of(USER_VALIDATION_AGE_ERROR))), content);
  }

  @Test
  void updateUser_UserNotFound() throws Exception {
    when(userService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class)))
        .thenThrow(new NotFoundException(USER_UPDATE_NOT_FOUND));

    MvcResult mvcResult = mvc.perform(put(uri + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_FOUND, List.of(USER_UPDATE_NOT_FOUND))), content);
  }

  @Test
  void deleteUser() throws Exception {
    when(userService.delete(ArgumentMatchers.any(UUID.class))).thenReturn(true);

    MvcResult mvcResult = mvc.perform(delete(uri + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertTrue(Boolean.parseBoolean(content));
  }

  @Test
  void deleteUser_UserNotFound() throws Exception {
    when(userService.delete(ArgumentMatchers.any(UUID.class))).thenThrow(new NotFoundException(USER_DOES_NOT_EXIST));

    MvcResult mvcResult = mvc.perform(delete(uri + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_FOUND, List.of(USER_DOES_NOT_EXIST))), content);
  }
}
