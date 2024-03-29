package com.github.personalitytest.service;

import com.github.personalitytest.TestHelper;
import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.mapper.ResultMapper;
import com.github.personalitytest.mapper.UserMapper;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.model.User;
import com.github.personalitytest.repository.ResultRepository;
import com.github.personalitytest.repository.UserRepository;
import com.github.personalitytest.service.impl.ResultServiceImpl;
import com.github.personalitytest.service.impl.UserServiceImpl;
import com.github.personalitytest.type.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class ResultServiceTest extends TestHelper {

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
    resultService = new ResultServiceImpl(resultRepository, new UserServiceImpl(userRepository));

    user1 = User.builder().id(UUID.randomUUID()).name(NAME_1).gender(Gender.MALE).age(AGE_1).build();
    userDto1 = UserMapper.INSTANCE.toDto(user1);

    result1 = Result.builder().id(UUID.randomUUID()).user(user1).answers(ANSWERS_1).build();
    result1.calculateResults();
    result1.calculateAvgScore();
    resultDto1 = ResultMapper.INSTANCE.toDto(result1);

    result2 = Result.builder().id(UUID.randomUUID()).user(user1).answers(ANSWERS_2).build();
    result2.calculateResults();
    result2.calculateAvgScore();
    resultDto2 = ResultMapper.INSTANCE.toDto(result2);

  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getAll_Valid_ReturnResults() {
    when(resultRepository.findAll()).thenReturn(List.of(result1, result2));
    var resultsDto = resultService.getAll();

    assertEquals(2, resultsDto.size());
    verify(resultRepository).findAll();
  }

  @Test
  void getAll_Empty_ReturnNoResults() {
    when(resultRepository.findAll()).thenReturn(Collections.emptyList());
    var resultsDto = resultService.getAll();

    assertEquals(0, resultsDto.size());
    verify(resultRepository).findAll();
  }

  @Test
  void get_Valid_ReturnResult() {
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(result1));
    var resultDto = resultService.get(result1.getId());

    assertThat(resultDto).isNotNull();
    assertEquals(result1.getId(), resultDto.getId());
    assertEquals(result1.getCompleted(), resultDto.getCompleted());
    assertEquals(result1.getUser().getId(), resultDto.getUser().getId());
    assertEquals(result1.getUser().getName(), resultDto.getUser().getName());
    assertEquals(result1.getUser().getGender().getValue(), resultDto.getUser().getGender());
    assertEquals(result1.getUser().getAge(), resultDto.getUser().getAge());
    assertEquals(result1.getAnswers(), resultDto.getAnswers());
    assertEquals(result1.getResults(), resultDto.getResults());
    assertEquals(result1.getAvgScore(), resultDto.getAvgScore());
    verify(resultRepository).findById(result1.getId());
  }

  @Test
  void get_NotFound_ThrowException() {
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    var exception = assertThrows(NotFoundException.class, () -> resultService.get(result1.getId()));
    assertEquals(ErrorResponse.RESULT_READ_NOT_FOUND, exception.getMessage());
  }

  @Test
  void getByUserId_Valid_ReturnResults() {
    when(resultRepository.findByUserId(ArgumentMatchers.any(UUID.class))).thenReturn(List.of(result1));
    var resultsDto = resultService.getByUserId(userDto1.getId());

    assertEquals(1, resultsDto.size());
    verify(resultRepository).findByUserId(userDto1.getId());
  }

  @Test
  void getByUserId_NotFound_ReturnNoResults() {
    when(resultRepository.findByUserId(ArgumentMatchers.any(UUID.class))).thenReturn(Collections.emptyList());
    var resultsDto = resultService.getByUserId(userDto1.getId());

    assertEquals(0, resultsDto.size());
    verify(resultRepository).findByUserId(userDto1.getId());
  }

  @Test
  void create_Valid_ReturnResult() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(result1));
    var resultDto = resultService.create(resultDto1.getUser().getId(), resultDto1);
    var captor = ArgumentCaptor.forClass(Result.class);

    assertThat(resultDto).isNotNull();
    verify(resultRepository).save(captor.capture());
    assertEquals(resultDto1.getCompleted(), captor.getValue().getCompleted());
    assertEquals(resultDto1.getUser().getId(), captor.getValue().getUser().getId());
    assertEquals(resultDto1.getUser().getName(), captor.getValue().getUser().getName());
    assertEquals(resultDto1.getUser().getGender(), captor.getValue().getUser().getGender().getValue());
    assertEquals(resultDto1.getUser().getAge(), captor.getValue().getUser().getAge());
    assertEquals(resultDto1.getAnswers(), captor.getValue().getAnswers());
    assertEquals(resultDto1.getResults(), captor.getValue().getResults());
    assertEquals(resultDto1.getAvgScore(), captor.getValue().getAvgScore());
  }

  @Test
  void create_UserNotFound_ThrowException() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    var exception = assertThrows(NotFoundException.class, () ->
        resultService.create(userDto1.getId(), resultDto1));
    assertEquals(ErrorResponse.USER_READ_NOT_FOUND, exception.getMessage());
  }

  @Test
  void update_Valid_ReturnResult() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(result1));
    var resultDto = resultService.update(resultDto1.getId(), resultDto2);
    var captor = ArgumentCaptor.forClass(Result.class);

    assertThat(resultDto).isNotNull();
    verify(resultRepository).save(captor.capture());
    assertEquals(resultDto1.getId(), captor.getValue().getId());
    assertEquals(resultDto2.getCompleted(), captor.getValue().getCompleted());
    assertEquals(resultDto2.getUser().getId(), captor.getValue().getUser().getId());
    assertEquals(resultDto2.getUser().getName(), captor.getValue().getUser().getName());
    assertEquals(resultDto2.getUser().getGender(), captor.getValue().getUser().getGender().getValue());
    assertEquals(resultDto2.getUser().getAge(), captor.getValue().getUser().getAge());
    assertEquals(resultDto2.getAnswers(), captor.getValue().getAnswers());
    assertEquals(resultDto2.getResults(), captor.getValue().getResults());
    assertEquals(resultDto2.getAvgScore(), captor.getValue().getAvgScore());
  }

  @Test
  void update_NotFound_ThrowException() {
    when(resultRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    var exception = assertThrows(NotFoundException.class, () ->
        resultService.update(resultDto1.getId(), resultDto1));
    assertEquals(ErrorResponse.RESULT_UPDATE_NOT_FOUND, exception.getMessage());
  }

  @Test
  void delete_Valid_ReturnTrue() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    boolean resultDeleted = resultService.delete(resultDto1.getId());
    assertTrue(resultDeleted);
  }

  @Test
  void delete_NotFound_ThrowException() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    var exception = assertThrows(NotFoundException.class, () ->
        resultService.delete(resultDto1.getId()));
    assertEquals(ErrorResponse.RESULT_DELETE_NOT_FOUND, exception.getMessage());
  }

  @Test
  void deleteByUserId_Valid_ReturnTrue() {
    when(resultRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    var resultDeleted = resultService.delete(userDto1.getId());
    assertTrue(resultDeleted);
  }

  @Test
  void deleteByUserId_UserNotFound_ThrowException() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    var exception = assertThrows(NotFoundException.class, () ->
        resultService.deleteByUserId(userDto1.getId()));
    assertEquals(ErrorResponse.USER_DELETE_NOT_FOUND, exception.getMessage());
  }
}