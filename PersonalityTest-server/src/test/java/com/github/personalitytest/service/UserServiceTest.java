package com.github.personalitytest.service;

import com.github.personalitytest.AbstractTest;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.exception.NotValidException;
import com.github.personalitytest.mapper.UserMapper;
import com.github.personalitytest.model.User;
import com.github.personalitytest.repository.UserRepository;
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


class UserServiceTest extends AbstractTest {

  @Mock
  UserRepository userRepository;
  AutoCloseable autoCloseable;
  private UserService userService;
  private User user1;
  private User user2;
  private UserDto userDto1;
  private UserDto userDto2;


  @BeforeEach
  void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
    userService = new UserServiceImpl(userRepository);

    user1 = User.builder().id(UUID.randomUUID()).name(NAME_1).gender(Gender.MALE).age(AGE_1).build();
    userDto1 = UserMapper.INSTANCE.toDto(user1);
    user2 = User.builder().id(UUID.randomUUID()).name(NAME_2).gender(Gender.MALE).age(AGE_2).build();
    userDto2 = UserMapper.INSTANCE.toDto(user2);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getAll_Success() {
    when(userRepository.findAll()).thenReturn(List.of(user1, user2));
    var usersDto = userService.getAll();

    assertEquals(2, usersDto.size());
    verify(userRepository).findAll();
  }

  @Test
  void getAll_Empty() {
    when(userRepository.findAll()).thenReturn(Collections.emptyList());
    var usersDto = userService.getAll();

    assertEquals(0, usersDto.size());
    verify(userRepository).findAll();
  }

  @Test
  void get_Success() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    var userDto = userService.get(user1.getId());

    assertThat(userDto).isNotNull();
    assertEquals(user1.getId(), userDto.getId());
    assertEquals(user1.getName(), userDto.getName());
    assertEquals(user1.getGender().getValue(), userDto.getGender());
    assertEquals(user1.getAge(), userDto.getAge());
    verify(userRepository).findById(user1.getId());
  }

  @Test
  void get_NotFound() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    var exception = assertThrows(NotFoundException.class, () -> userService.get(user1.getId()));
    assertEquals(ErrorResponse.USER_GET_NOT_FOUND, exception.getMessage());
  }

  @Test
  void create_Success() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    var userDto = userService.create(userDto1);
    var captor = ArgumentCaptor.forClass(User.class);

    assertThat(userDto).isNotNull();
    verify(userRepository).save(captor.capture());
    assertEquals(userDto1.getName(), captor.getValue().getName());
    assertEquals(userDto1.getGender(), captor.getValue().getGender().getValue());
    assertEquals(userDto1.getAge(), captor.getValue().getAge());
  }

  @Test
  void createWithId_Success() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    var userDto = userService.create(userDto1.getId(), userDto1);
    var captor = ArgumentCaptor.forClass(User.class);

    assertThat(userDto).isNotNull();
    verify(userRepository).save(captor.capture());
    assertEquals(userDto1.getId(), captor.getValue().getId());
    assertEquals(userDto1.getName(), captor.getValue().getName());
    assertEquals(userDto1.getGender(), captor.getValue().getGender().getValue());
    assertEquals(userDto1.getAge(), captor.getValue().getAge());
  }

  @Test
  void createWithId_IdExists() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    var exception = assertThrows(NotValidException.class, () -> userService.create(userDto1.getId(), userDto1));
    assertEquals(ErrorResponse.USER_CREATE_ID_FOUND, exception.getMessage());
  }

  @Test
  void update_Success() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    var userDto = userService.update(userDto1.getId(), userDto2);
    var captor = ArgumentCaptor.forClass(User.class);

    assertThat(userDto).isNotNull();
    verify(userRepository).save(captor.capture());
    assertEquals(userDto1.getId(), captor.getValue().getId());
    assertEquals(userDto2.getName(), captor.getValue().getName());
    assertEquals(userDto2.getGender(), captor.getValue().getGender().getValue());
    assertEquals(userDto2.getAge(), captor.getValue().getAge());
  }

  @Test
  void update_NotFound() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    var exception = assertThrows(NotFoundException.class, () -> userService.update(userDto1.getId(), userDto2));
    assertEquals(ErrorResponse.USER_UPDATE_NOT_FOUND, exception.getMessage());
  }

  @Test
  void delete_Success() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    var userDeleted = userService.delete(userDto1.getId());
    assertTrue(userDeleted);
  }

  @Test
  void delete_DoesNotExist() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    var exception = assertThrows(NotFoundException.class, () -> userService.exists(userDto1.getId()));
    assertEquals(ErrorResponse.USER_DOES_NOT_EXIST, exception.getMessage());
  }

  @Test
  void exists_Success() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    var userExists = userService.exists(userDto1.getId());
    assertTrue(userExists);
  }

  @Test
  void exists_DoesNotExist() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    var exception = assertThrows(NotFoundException.class, () -> userService.exists(userDto1.getId()));
    assertEquals(ErrorResponse.USER_DOES_NOT_EXIST, exception.getMessage());
  }
}