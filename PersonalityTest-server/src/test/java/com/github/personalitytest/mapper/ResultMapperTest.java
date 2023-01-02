package com.github.personalitytest.mapper;

import com.github.personalitytest.TestHelper;
import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultMapperTest extends TestHelper {

  private User user;
  private UserDto userDto;
  private Result result;
  private ResultDto resultDto;

  @BeforeEach
  void setUp() {
    user = new User(UUID.randomUUID(), NAME_1, Gender.MALE, AGE_1, Collections.emptySet());
    userDto = new UserDto(user.getId(), NAME_1, Gender.MALE.getValue(), AGE_1);
    result = new Result(UUID.randomUUID(), TIMESTAMP, user, ANSWERS_2, RESULTS_2, AVG_SCORE_2);
    resultDto = new ResultDto(result.getId(), TIMESTAMP, userDto, ANSWERS_2, RESULTS_2, AVG_SCORE_2);
  }

  @Test
  void toDto_Valid_ReturnDto() {
    var dto = ResultMapper.INSTANCE.toDto(result);

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
  void toEntity_Valid_ReturnEntity() {
    var entity = ResultMapper.INSTANCE.toEntity(resultDto);

    assertThat(entity).isNotNull();
    assertEquals(resultDto.getId(), entity.getId());
    assertEquals(resultDto.getCompleted(), entity.getCompleted());
    assertEquals(resultDto.getUser().getId(), entity.getUser().getId());
    assertEquals(resultDto.getUser().getName(), entity.getUser().getName());
    assertEquals(resultDto.getUser().getGender(), entity.getUser().getGender().getValue());
    assertEquals(resultDto.getUser().getAge(), entity.getUser().getAge());
    assertEquals(resultDto.getAnswers(), entity.getAnswers());
    assertEquals(resultDto.getResults(), entity.getResults());
    assertEquals(resultDto.getAvgScore(), entity.getAvgScore());
  }
}