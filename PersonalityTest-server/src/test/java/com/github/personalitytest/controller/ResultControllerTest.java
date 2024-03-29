package com.github.personalitytest.controller;

import com.github.personalitytest.TestHelper;
import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.service.ResultService;
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


@WebMvcTest(ResultController.class)
class ResultControllerTest extends TestHelper {

  protected final static String URI = "/api/v1/results/";
  @MockBean
  ResultService resultService;
  ResultController resultController;
  AutoCloseable autoCloseable;
  private UserDto userDto1;
  private ResultDto resultDto1;
  private ResultDto resultDto2;


  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    resultController = new ResultController(resultService);

    userDto1 = new UserDto(UUID.randomUUID(), NAME_1, Gender.MALE.getValue(), AGE_1);
    resultDto1 = new ResultDto();
    resultDto1.setAnswers(ANSWERS_1);
    resultDto2 = new ResultDto(UUID.randomUUID(), TIMESTAMP, userDto1, ANSWERS_2, RESULTS_2, AVG_SCORE_2);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getAll_Valid_ReturnResults() throws Exception {
    when(resultService.getAll()).thenReturn(List.of(resultDto2));

    var mvcResult = mvc.perform(get(URI).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(List.of(resultDto2)), content);
  }

  @Test
  void getAll_Empty_ReturnNoResults() throws Exception {
    when(resultService.getAll()).thenReturn(Collections.emptyList());

    var mvcResult = mvc.perform(get(URI).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(Collections.emptyList()), content);
  }

  @Test
  void get_Valid_ReturnResult() throws Exception {
    when(resultService.get(ArgumentMatchers.any(UUID.class))).thenReturn(resultDto2);

    var mvcResult = mvc.perform(get(URI + resultDto2.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(resultDto2), content);
  }

  @Test
  void get_NotFound_ReturnErrorResponse() throws Exception {
    when(resultService.get(ArgumentMatchers.any(UUID.class)))
        .thenThrow(new NotFoundException(ErrorResponse.RESULT_READ_NOT_FOUND));

    var mvcResult = mvc.perform(get(URI + resultDto2.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_FOUND,
        List.of(ErrorResponse.RESULT_READ_NOT_FOUND))), content);
  }

  @Test
  void create_Valid_ReturnResult() throws Exception {
    when(resultService.create(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenReturn(resultDto1);

    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(resultDto1), content);
  }

  @Test
  void create_UserNotFound_ReturnErrorResponse() throws Exception {
    when(resultService.create(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenThrow(new NotFoundException(ErrorResponse.USER_READ_NOT_FOUND));

    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_FOUND,
        List.of(ErrorResponse.USER_READ_NOT_FOUND))), content);
  }

  @Test
  void create_NoContent_ThrowException() throws Exception {
    var mvcResult = mvc.perform(post(URI + resultDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void create_AnswerNull_ReturnErrorResponse() throws Exception {
    resultDto1.setAnswers(null);
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.RESULT_VALIDATION_ANSWER_EMPTY_ERROR))), content);
  }

  @Test
  void create_AnswerEmpty_ReturnErrorResponse() throws Exception {
    resultDto1.setAnswers(Collections.emptyList());
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.RESULT_VALIDATION_ANSWER_EMPTY_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_SIZE_ERROR))), content);
  }


  @Test
  void create_AnswerWrongSize_ReturnErrorResponse() throws Exception {
    resultDto1.setAnswers(List.of(7, 7, 7, 7, 7, 7, 7, 7, 7));
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.RESULT_VALIDATION_ANSWER_SIZE_ERROR))), content);
  }

  @Test
  void create_AnswerWrongValues_ReturnErrorResponse() throws Exception {
    resultDto1.setAnswers(List.of(8, 8, 8, 8, 8, 8, 8, 8, 8, 8));
    var mvcResult = mvc.perform(post(URI + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto1)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_VALID,
        List.of(ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR,
            ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR))), content);
  }

  @Test
  void update_Valid_ReturnResult() throws Exception {
    when(resultService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenReturn(resultDto2);

    var mvcResult = mvc.perform(put(URI + resultDto2.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto2)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(resultDto2), content);
  }

  @Test
  void update_NoContent_ThrowException() throws Exception {
    var mvcResult = mvc.perform(put(URI + resultDto2.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void update_NotFound_ReturnErrorResponse() throws Exception {
    when(resultService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenThrow(new NotFoundException(ErrorResponse.RESULT_UPDATE_NOT_FOUND));

    var mvcResult = mvc.perform(put(URI + resultDto2.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto2)))
        .andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_FOUND,
        List.of(ErrorResponse.RESULT_UPDATE_NOT_FOUND))), content);
  }

  @Test
  void delete_Valid_ReturnTrue() throws Exception {
    when(resultService.delete(ArgumentMatchers.any(UUID.class))).thenReturn(true);

    var mvcResult = mvc.perform(delete(URI + resultDto2.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertTrue(Boolean.parseBoolean(content));
  }

  @Test
  void delete_NotFound_ReturnErrorResponse() throws Exception {
    when(resultService.delete(ArgumentMatchers.any(UUID.class)))
        .thenThrow(new NotFoundException(ErrorResponse.RESULT_DELETE_NOT_FOUND));

    var mvcResult = mvc.perform(delete(URI + resultDto2.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    var content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ErrorResponse.ENTRY_NOT_FOUND,
        List.of(ErrorResponse.RESULT_DELETE_NOT_FOUND))), content);
  }
}
