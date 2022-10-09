package com.github.personalitytest.controller;

import com.github.personalitytest.AbstractTest;
import com.github.personalitytest.converter.ResultConverter;
import com.github.personalitytest.converter.UserConverter;
import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.exception.NotValidException;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.model.User;
import com.github.personalitytest.service.ResultService;
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


@WebMvcTest(ResultController.class)
class ResultControllerTest extends AbstractTest {

  protected final static String uri = "/api/results/";
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
    UserConverter userConverter = new UserConverter();
    ResultConverter resultConverter = new ResultConverter();
    resultController = new ResultController(resultService);

    userDto1 = new UserDto(UUID.randomUUID(), "Patrick", "Male", 25);
    User user1 = userConverter.convertDtoToEntity(userDto1);
    Result result1 = new Result();
    result1.setId(UUID.randomUUID());
    result1.setUser(user1);
    result1.setAnswers(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    result1.calculateResults();
    result1.calculateAvgScore();
    resultDto1 = resultConverter.convertEntityToDto(result1);

    resultDto2 = new ResultDto();
    resultDto2.setAnswers(List.of(4, 4, 4, 4, 4, 4, 4, 4, 4, 4));
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getResults() throws Exception {
    when(resultService.getAll()).thenReturn(List.of(resultDto1));

    MvcResult mvcResult = mvc.perform(get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(List.of(resultDto1)), content);
  }

  @Test
  void getResults_ResultsDoNotExist() throws Exception {
    when(resultService.getAll()).thenReturn(List.of());

    MvcResult mvcResult = mvc.perform(get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(List.of()), content);
  }

  @Test
  void getResult() throws Exception {
    when(resultService.get(ArgumentMatchers.any(UUID.class))).thenReturn(resultDto1);

    MvcResult mvcResult = mvc.perform(get(uri + resultDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(resultDto1), content);
  }

  @Test
  void getResult_ResultNotFound() throws Exception {
    when(resultService.get(ArgumentMatchers.any(UUID.class))).thenThrow(new NotFoundException(RESULT_GET_NOT_FOUND));

    MvcResult mvcResult = mvc.perform(get(uri + resultDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_FOUND, List.of(RESULT_GET_NOT_FOUND))), content);
  }

  @Test
  void getResultsByUserId() throws Exception {
    when(resultService.getByUserId(ArgumentMatchers.any(UUID.class))).thenReturn(List.of(resultDto1));

    MvcResult mvcResult = mvc.perform(get(uri + "user/" + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(List.of(resultDto1)), content);
  }

  @Test
  void getResultsByUserId_ResultNotFound() throws Exception {
    when(resultService.getByUserId(ArgumentMatchers.any(UUID.class))).thenThrow(new NotFoundException(RESULT_GET_NOT_FOUND));

    MvcResult mvcResult = mvc.perform(get(uri + "user/" + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_FOUND, List.of(RESULT_GET_NOT_FOUND))), content);
  }

  @Test
  void createResult() throws Exception {
    when(resultService.create(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenReturn(resultDto1);

    MvcResult mvcResult = mvc.perform(post(uri + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto1)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(resultDto1), content);
  }

  @Test
  void createResult_NoContent() throws Exception {
    when(resultService.create(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenReturn(resultDto1);
    MvcResult mvcResult = mvc.perform(post(uri + resultDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void createResult_AnswerSizeNotValid() throws Exception {
    when(resultService.create(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenThrow(new NotValidException(RESULT_VALIDATION_ANSWER_SIZE_ERROR));

    MvcResult mvcResult = mvc.perform(post(uri + userDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto1)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_VALID, List.of(RESULT_VALIDATION_ANSWER_SIZE_ERROR))), content);
  }

  @Test
  void updateResult() throws Exception {
    when(resultService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenReturn(resultDto1);

    MvcResult mvcResult = mvc.perform(put(uri + resultDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto2)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(resultDto1), content);
  }

  @Test
  void updateResult_NoContent() throws Exception {
    when(resultService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenReturn(resultDto1);
    MvcResult mvcResult = mvc.perform(put(uri + resultDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
  }

  @Test
  void updateResult_ResultNotFound() throws Exception {
    when(resultService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenThrow(new NotFoundException(RESULT_UPDATE_NOT_FOUND));

    MvcResult mvcResult = mvc.perform(put(uri + resultDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto2)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_FOUND, List.of(RESULT_UPDATE_NOT_FOUND))), content);
  }

  @Test
  void updateResult_AnswerValueNotValid() throws Exception {
    when(resultService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(ResultDto.class)))
        .thenThrow(new NotValidException(RESULT_VALIDATION_ANSWER_VALUE_ERROR));

    MvcResult mvcResult = mvc.perform(put(uri + resultDto1.getId())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapToJson(resultDto2)))
        .andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_VALID, List.of(RESULT_VALIDATION_ANSWER_VALUE_ERROR))), content);
  }

  @Test
  void deleteResult() throws Exception {
    when(resultService.delete(ArgumentMatchers.any(UUID.class))).thenReturn(true);

    MvcResult mvcResult = mvc.perform(delete(uri + resultDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertTrue(Boolean.parseBoolean(content));
  }

  @Test
  void deleteResult_ResultNotFound() throws Exception {
    when(resultService.delete(ArgumentMatchers.any(UUID.class))).thenThrow(new NotFoundException(RESULT_DOES_NOT_EXIST));

    MvcResult mvcResult = mvc.perform(delete(uri + resultDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_FOUND, List.of(RESULT_DOES_NOT_EXIST))), content);
  }

  @Test
  void deleteResultsByUserId() throws Exception {
    when(resultService.deleteByUserId(ArgumentMatchers.any(UUID.class))).thenReturn(true);

    MvcResult mvcResult = mvc.perform(delete(uri + "user/" + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    assertTrue(Boolean.parseBoolean(content));
  }

  @Test
  void deleteResultsByUserId_ResultNotFound() throws Exception {
    when(resultService.deleteByUserId(ArgumentMatchers.any(UUID.class)))
        .thenThrow(new NotFoundException(RESULT_DOES_NOT_EXIST));

    MvcResult mvcResult = mvc.perform(delete(uri + "user/" + userDto1.getId()).accept(MediaType.APPLICATION_JSON)).andReturn();
    String content = mvcResult.getResponse().getContentAsString();

    assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    assertEquals(mapToJson(new ErrorResponse(ENTRY_NOT_FOUND, List.of(RESULT_DOES_NOT_EXIST))), content);
  }
}
