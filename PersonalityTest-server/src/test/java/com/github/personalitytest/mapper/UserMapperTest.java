package com.github.personalitytest.mapper;

import com.github.personalitytest.AbstractTest;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest extends AbstractTest {

  private User user;
  private UserDto userDto;

  @BeforeEach
  void setUp() {
    user = new User(UUID.randomUUID(), NAME_1, Gender.MALE, AGE_1, Collections.emptySet());
    userDto = new UserDto(user.getId(), NAME_1, Gender.MALE.getValue(), AGE_1);
  }

  @Test
  void toDto() {
    var dto = UserMapper.INSTANCE.toDto(user);

    assertThat(dto).isNotNull();
    assertEquals(user.getId(), dto.getId());
    assertEquals(user.getName(), dto.getName());
    assertEquals(user.getGender().getValue(), dto.getGender());
    assertEquals(user.getAge(), dto.getAge());
  }

  @Test
  void toEntity() {
    var entity = UserMapper.INSTANCE.toEntity(userDto);

    assertThat(entity).isNotNull();
    assertEquals(userDto.getId(), entity.getId());
    assertEquals(userDto.getName(), entity.getName());
    assertEquals(userDto.getGender(), entity.getGender().getValue());
    assertEquals(userDto.getAge(), entity.getAge());
  }
}