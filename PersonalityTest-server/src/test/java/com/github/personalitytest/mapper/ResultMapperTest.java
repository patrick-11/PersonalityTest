package com.github.personalitytest.mapper;

import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultMapperTest {

  private User user;
  private UserDto userDto;
  private Result result;
  private ResultDto resultDto;

  @BeforeEach
  void setUp() {
    user = new User(UUID.randomUUID(), "Patrick", Gender.MALE, 25, Collections.emptySet());
    userDto = new UserDto(user.getId(), "Patrick", "Male", 25);
    result = new Result(UUID.randomUUID(), Timestamp.valueOf("2022-01-01 12:00:00"), user,
        List.of(4, 4, 4, 4, 4, 4, 4, 4, 4, 4), List.of(4.0, 4.0, 4.0, 4.0, 4.0), 4.0);
    resultDto = new ResultDto(result.getId(), Timestamp.valueOf("2022-01-01 12:00:00"), userDto,
        List.of(4, 4, 4, 4, 4, 4, 4, 4, 4, 4), List.of(4.0, 4.0, 4.0, 4.0, 4.0), 4.0);
  }

  @Test
  void convertEntityToDto() {
    var dto = new ResultMapper().toDto(result);

    assertThat(dto).isNotNull();
    assertEquals(result.getId(), dto.getId());
    assertEquals(result.getCompleted(), dto.getCompleted());
    assertEquals(result.getUser().getId(), dto.getUser().getId());
    assertEquals(result.getUser().getName(), dto.getUser().getName());
    assertEquals(result.getUser().getGender().getValue(), dto.getUser().getGender());
    assertEquals(result.getUser().getAge(), dto.getUser().getAge());
    assertEquals(result.getAnswers(), dto.getAnswers());
    assertEquals(result.getResults(), dto.getResults());
    assertEquals(result.getAvgScore(), dto.getAvgScore());
  }

  @Test
  void convertDtoToEntity() {
    var entity = new ResultMapper().toEntity(resultDto);

    assertThat(entity).isNotNull();
    assertEquals(resultDto.getId(), entity.getId());
    assertEquals(resultDto.getCompleted(), entity.getCompleted());
    assertEquals(resultDto.getUser().getId(), entity.getUser().getId());
    assertEquals(resultDto.getUser().getName(), entity.getUser().getName());
    assertEquals(resultDto.getUser().getGender(), entity.getUser().getGender().toString());
    assertEquals(resultDto.getUser().getAge(), entity.getUser().getAge());
    assertEquals(resultDto.getAnswers(), entity.getAnswers());
    assertEquals(resultDto.getResults(), entity.getResults());
    assertEquals(resultDto.getAvgScore(), entity.getAvgScore());
  }
}