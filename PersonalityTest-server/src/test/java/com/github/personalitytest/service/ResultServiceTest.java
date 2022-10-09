package com.github.personalitytest.service;

import com.github.personalitytest.AbstractTest;
import com.github.personalitytest.converter.ResultConverter;
import com.github.personalitytest.converter.UserConverter;
import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.exception.NotValidException;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.model.User;
import com.github.personalitytest.repository.ResultRepository;
import com.github.personalitytest.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ResultServiceTest extends AbstractTest {

  @Mock
  ResultRepository resultRepository;
  @Mock
  UserRepository userRepository;
  AutoCloseable autoCloseable;
  ResultService resultService;
  private User user1;
  private UserDto userDto1;
  private Result result1;
  private Result result2;
  private ResultDto resultDto1;
  private ResultDto resultDto2;

  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);

    UserConverter userConverter = new UserConverter();
    ResultConverter resultConverter = new ResultConverter();
    UserService userService = new UserService(userRepository, userConverter);
    resultService = new ResultService(resultRepository, userService, resultConverter, userConverter);

    userDto1 = new UserDto(UUID.randomUUID(), "Patrick", "Male", 25);
    user1 = userConverter.convertDtoToEntity(userDto1);
    result1 = new Result();
    result1.setId(UUID.randomUUID());
    result1.setUser(user1);
    result1.setAnswers(List.of(1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
    result1.calculateResults();
    result1.calculateAvgScore();
    resultDto1 = resultConverter.convertEntityToDto(result1);

    result2 = new Result();
    result2.setId(UUID.randomUUID());
    result2.setUser(user1);
    result2.setAnswers(List.of(4, 4, 4, 4, 4, 4, 4, 4, 4, 4));
    result2.calculateResults();
    result2.calculateAvgScore();
    resultDto2 = resultConverter.convertEntityToDto(result1);

  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getAll() {
    when(resultRepository.findAll()).thenReturn(List.of(result1));
    List<ResultDto> resultsDto = resultService.getAll();

    assertEquals(1, resultsDto.size());
    verify(resultRepository).findAll();
  }

  @Test
  void getAll_ResultsNotFound() {
    when(resultRepository.findAll()).thenReturn(List.of());
    List<ResultDto> resultsDto = resultService.getAll();

    assertEquals(0, resultsDto.size());
    verify(resultRepository).findAll();
  }

  @Test
  void get() {
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(result1));
    ResultDto resultDto = resultService.get(result1.getId());

    assertThat(resultDto).isNotNull();
    verify(resultRepository).findById(result1.getId());
  }

  @Test
  void get_ResultNotFound() {
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    NotFoundException exception = assertThrows(NotFoundException.class, () -> resultService.get(result1.getId()));
    assertEquals(RESULT_GET_NOT_FOUND, exception.getMessage());
  }

  @Test
  void getByUserId() {
    when(resultRepository.findAll()).thenReturn(List.of(result1));
    List<ResultDto> resultsDto = resultService.getByUserId(userDto1.getId());

    assertEquals(1, resultsDto.size());
    verify(resultRepository).findAll();
  }

  @Test
  void getByUserId_ResultsNotFound() {
    when(resultRepository.findAll()).thenReturn(List.of());
    List<ResultDto> resultsDto = resultService.getByUserId(userDto1.getId());

    assertEquals(0, resultsDto.size());
    verify(resultRepository).findAll();
  }

  @Test
  void create() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    when(resultRepository.save(ArgumentMatchers.any(Result.class))).thenReturn(result1);
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(result1));
    ResultDto resultDto = resultService.create(resultDto1.getUser().getId(), resultDto1);

    assertThat(resultDto).isNotNull();
    assertEquals(resultDto1.getId(), resultDto.getId());
    assertEquals(resultDto1.getAnswers(), resultDto.getAnswers());
    assertEquals(resultDto1.getResults(), resultDto.getResults());
    assertEquals(resultDto1.getAvgScore(), resultDto.getAvgScore());
  }

  @Test
  void create_UserNotFound() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    NotFoundException exception = assertThrows(NotFoundException.class, () ->
        resultService.create(userDto1.getId(), resultDto1));
    assertEquals(USER_GET_NOT_FOUND, exception.getMessage());
  }

  @Test
  void update() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(result1));
    when(resultRepository.save(ArgumentMatchers.any(Result.class))).thenReturn(result1);
    ResultDto resultDto = resultService.update(userDto1.getId(), resultDto2);

    assertThat(resultDto).isNotNull();
    assertEquals(resultDto1.getId(), resultDto.getId());
    assertEquals(resultDto2.getAnswers(), resultDto.getAnswers());
    assertEquals(resultDto2.getResults(), resultDto.getResults());
    assertEquals(resultDto2.getAvgScore(), resultDto.getAvgScore());
  }

  @Test
  void update_ResultDoesNotExist() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    NotFoundException exception = assertThrows(NotFoundException.class, () ->
        resultService.update(resultDto1.getId(), resultDto1));
    assertEquals(RESULT_DOES_NOT_EXIST, exception.getMessage());
  }

  @Test
  void update_ResultNotFound() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    NotFoundException exception = assertThrows(NotFoundException.class, () ->
        resultService.update(resultDto1.getId(), resultDto1));
    assertEquals(RESULT_UPDATE_NOT_FOUND, exception.getMessage());
  }

  @Test
  void delete() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    boolean resultDeleted = resultService.delete(resultDto1.getId());
    assertTrue(resultDeleted);
  }

  @Test
  void delete_ResultDoesNotExist() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    NotFoundException exception = assertThrows(NotFoundException.class, () ->
        resultService.delete(resultDto1.getId()));
    assertEquals(RESULT_DOES_NOT_EXIST, exception.getMessage());
  }

  @Test
  void deleteByUserId() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    boolean resultDeleted = resultService.delete(userDto1.getId());
    assertTrue(resultDeleted);
  }

  @Test
  void deleteByUserId_UserDoesNotExist() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    NotFoundException exception = assertThrows(NotFoundException.class, () ->
        resultService.deleteByUserId(userDto1.getId()));
    assertEquals(USER_DOES_NOT_EXIST, exception.getMessage());
  }

  @Test
  void exists() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    boolean resultExists = resultService.exists(resultDto1.getId());
    assertTrue(resultExists);
  }

  @Test
  void exists_ResultDoesNotExist() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    NotFoundException exception = assertThrows(NotFoundException.class, () -> resultService.exists(resultDto1.getId()));
    assertEquals(RESULT_DOES_NOT_EXIST, exception.getMessage());
  }

  @Test
  void validate() {
    boolean resultValid = resultService.validate(resultDto1);
    assertTrue(resultValid);
  }

  @Test
  void validate_AnswerSizeNotValid() {
    resultDto1.setAnswers(List.of(1, 1, 1, 1));
    NotValidException exception = assertThrows(NotValidException.class, () -> resultService.validate(resultDto1));
    assertEquals(RESULT_VALIDATION_ANSWER_SIZE_ERROR, exception.getMessage());
  }

  @Test
  void validate_AnswerValueNotValid() {
    resultDto1.setAnswers(List.of(1, 1, 1, 1, 0, 1, 1, 1, 1, 1));
    NotValidException exception = assertThrows(NotValidException.class, () -> resultService.validate(resultDto1));
    assertEquals(RESULT_VALIDATION_ANSWER_VALUE_ERROR, exception.getMessage());
  }

}