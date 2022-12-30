package com.github.personalitytest.controller;

import com.github.personalitytest.AbstractTest;
import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.service.ResultService;
import com.github.personalitytest.service.UserService;
import com.github.personalitytest.type.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(UserController.class)
class UserControllerTest extends AbstractTest {

  protected final static String URI = "/api/v1/users/";
  @MockBean
  UserService userService;
  @MockBean
  ResultService resultService;
  UserController userController;
  AutoCloseable autoCloseable;
  private UserDto userDto1;
  private UserDto userDto2;
  private ResultDto resultDto1;


  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    userController = new UserController(userService, resultService);

    userDto1 = new UserDto(UUID.randomUUID(), NAME_1, Gender.MALE.getValue(), AGE_1);
    userDto2 = new UserDto(UUID.randomUUID(), NAME_2, Gender.MALE.getValue(), AGE_2);
    resultDto1 = new ResultDto(UUID.randomUUID(), TIMESTAMP, userDto1, ANSWERS_2, RESULTS_2, AVG_SCORE_2);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getAll_Success() throws Exception {
    when(userService.getAll()).thenReturn(List.of(userDto1, userDto2));

    var mvcResult = mvc.perform(get(URI).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(List.of(userDto1, userDto2)), content);
  }

  @Test
  void getAll_Empty() throws Exception {
    when(userService.getAll()).thenReturn(Collections.emptyList());

    var mvcResult = mvc.perform(get(URI).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(Collections.emptyList()), content);
  }

  @Test
  void get_Success() throws Exception {
    when(userService.get(ArgumentMatchers.any(UUID.class))).thenReturn(userDto1);

    var mvcResult = mvc.perform(get(URI + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(userDto1), content);
  }

  @Test
  void get_NotFound() throws Exception {
    when(userService.get(ArgumentMatchers.any(UUID.class)))
        .thenThrow(new NotFoundException(ErrorResponse.USER_READ_NOT_FOUND));

    var mvcResult = mvc.perform(get(URI + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_FOUND,
        List.of(ErrorResponse.USER_READ_NOT_FOUND))), content);
  }

  @Test
  void getResults_Success() throws Exception {
    when(resultService.getByUserId(ArgumentMatchers.any(UUID.class))).thenReturn(List.of(resultDto1));

    var mvcResult = mvc.perform(get(URI + userDto1.getId() + "/results")
        .accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(List.of(resultDto1)), content);
  }

  @Test
  void getResults_Empty() throws Exception {
    when(resultService.getByUserId(ArgumentMatchers.any(UUID.class))).thenReturn(Collections.emptyList());

    var mvcResult = mvc.perform(get(URI + userDto1.getId() + "/results")
            .accept(MediaType.APPLICATION_JSON))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(Collections.emptyList()), content);
  }

  @Test
  void create_Success() throws Exception {
    when(userService.create(ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);

    var mvcResult = mvc.perform(post(URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(userDto1), content);
  }

  @Test
  void create_NoContent() throws Exception {
    var mvcResult = mvc.perform(post(URI).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void create_NameNull() throws Exception {
    userDto1.setName(null);
    var mvcResult = mvc.perform(post(URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_NAME_EMPTY_ERROR))), content);
  }

  @Test
  void create_NameEmpty() throws Exception {
    userDto1.setName("");
    var mvcResult = mvc.perform(post(URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_NAME_SIZE_ERROR, ErrorResponse.USER_VALIDATION_NAME_EMPTY_ERROR))), content);
  }

  @Test
  void create_NameWrongSize() throws Exception {
    userDto1.setName("T");
    var mvcResult = mvc.perform(post(URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_NAME_SIZE_ERROR))), content);
  }

  @Test
  void create_GenderNull() throws Exception {
    userDto1.setGender(null);
    var mvcResult = mvc.perform(post(URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_GENDER_EMPTY_ERROR))), content);
  }

  @Test
  void create_GenderEmpty() throws Exception {
    userDto1.setGender("");
    var mvcResult = mvc.perform(post(URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_GENDER_ERROR, ErrorResponse.USER_VALIDATION_GENDER_EMPTY_ERROR))), content);
  }

  @Test
  void create_GenderNotExisting() throws Exception {
    userDto1.setGender("Non-Binary");
    var mvcResult = mvc.perform(post(URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_GENDER_ERROR))), content);
  }

  @Test
  void create_AgeSmaller() throws Exception {
    userDto1.setAge(4);
    var mvcResult = mvc.perform(post(URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_AGE_SIZE_ERROR))), content);
  }

  @Test
  void create_AgeGreater() throws Exception {
    userDto1.setAge(121);
    var mvcResult = mvc.perform(post(URI)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_AGE_SIZE_ERROR))), content);
  }

  @Test
  void createById_Success() throws Exception {
    when(userService.create(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);

    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(userDto1), content);
  }

  @Test
  void createById_NoContent() throws Exception {
    var mvcResult = mvc.perform(post(URI + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void createById_NameNull() throws Exception {
    userDto1.setName(null);
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_NAME_EMPTY_ERROR))), content);
  }

  @Test
  void createById_NameEmpty() throws Exception {
    userDto1.setName("");
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_NAME_SIZE_ERROR, ErrorResponse.USER_VALIDATION_NAME_EMPTY_ERROR))), content);
  }

  @Test
  void createById_NameWrongSize() throws Exception {
    userDto1.setName("T");
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_NAME_SIZE_ERROR))), content);
  }

  @Test
  void createById_GenderNull() throws Exception {
    userDto1.setGender(null);
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_GENDER_EMPTY_ERROR))), content);
  }

  @Test
  void createById_GenderEmpty() throws Exception {
    userDto1.setGender("");
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_GENDER_ERROR, ErrorResponse.USER_VALIDATION_GENDER_EMPTY_ERROR))), content);
  }

  @Test
  void createById_GenderNotExisting() throws Exception {
    userDto1.setGender("Non-Binary");
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_GENDER_ERROR))), content);
  }

  @Test
  void createById_AgeSmaller() throws Exception {
    userDto1.setAge(4);
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_AGE_SIZE_ERROR))), content);
  }

  @Test
  void createById_AgeGreater() throws Exception {
    userDto1.setAge(121);
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_AGE_SIZE_ERROR))), content);
  }

  @Test
  void update_Success() throws Exception {
    when(userService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class))).thenReturn(userDto1);

    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(userDto1), content);
  }

  @Test
  void update_NoContent() throws Exception {
    var mvcResult = mvc.perform(put(URI + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void update_NameNull() throws Exception {
    userDto1.setName(null);
    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_NAME_EMPTY_ERROR))), content);
  }

  @Test
  void update_NameEmpty() throws Exception {
    userDto1.setName("");
    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_NAME_SIZE_ERROR, ErrorResponse.USER_VALIDATION_NAME_EMPTY_ERROR))), content);
  }

  @Test
  void update_NameWrongSize() throws Exception {
    userDto1.setName("T");
    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_NAME_SIZE_ERROR))), content);
  }

  @Test
  void update_GenderNull() throws Exception {
    userDto1.setGender(null);
    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_GENDER_EMPTY_ERROR))), content);
  }

  @Test
  void update_GenderEmpty() throws Exception {
    userDto1.setGender("");
    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_GENDER_ERROR, ErrorResponse.USER_VALIDATION_GENDER_EMPTY_ERROR))), content);
  }

  @Test
  void update_GenderNotExisting() throws Exception {
    userDto1.setGender("Non-Binary");
    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_GENDER_ERROR))), content);
  }

  @Test
  void update_AgeSmaller() throws Exception {
    userDto1.setAge(4);
    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_AGE_SIZE_ERROR))), content);
  }

  @Test
  void update_AgeGreater() throws Exception {
    userDto1.setAge(121);
    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.USER_VALIDATION_AGE_SIZE_ERROR))), content);
  }

  @Test
  void update_NotFound() throws Exception {
    when(userService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(UserDto.class)))
        .thenThrow(new NotFoundException(ErrorResponse.USER_UPDATE_NOT_FOUND));

    var mvcResult = mvc.perform(put(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(userDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_FOUND,
        List.of(ErrorResponse.USER_UPDATE_NOT_FOUND))), content);
  }

  @Test
  void delete_Success() throws Exception {
    when(userService.delete(ArgumentMatchers.any(UUID.class))).thenReturn(true);

    var mvcResult = mvc.perform(delete(URI + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertTrue(Boolean.parseBoolean(content));
  }

  @Test
  void delete_NotFound() throws Exception {
    when(userService.delete(ArgumentMatchers.any(UUID.class))).thenThrow(new NotFoundException(ErrorResponse.USER_DELETE_NOT_FOUND));

    var mvcResult = mvc.perform(delete(URI + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_FOUND,
        List.of(ErrorResponse.USER_DELETE_NOT_FOUND))), content);
  }

  @Test
  void deleteResults_Success() throws Exception {
    when(resultService.deleteByUserId(ArgumentMatchers.any(UUID.class))).thenReturn(true);

    var mvcResult = mvc.perform(delete(URI + userDto1.getId() + "/results").accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertTrue(Boolean.parseBoolean(content));
  }

  @Test
  void deleteResults_NotFound() throws Exception {
    when(resultService.deleteByUserId(ArgumentMatchers.any(UUID.class)))
        .thenThrow(new NotFoundException(ErrorResponse.RESULT_DELETE_NOT_FOUND));

    var mvcResult = mvc.perform(delete(URI + userDto1.getId() + "/results").accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_FOUND,
        List.of(ErrorResponse.RESULT_DELETE_NOT_FOUND))), content);
  }
}
