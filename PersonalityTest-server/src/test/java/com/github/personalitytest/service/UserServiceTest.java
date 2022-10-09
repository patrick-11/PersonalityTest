package com.github.personalitytest.service;

import com.github.personalitytest.AbstractTest;
import com.github.personalitytest.converter.UserConverter;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.exception.NotValidException;
import com.github.personalitytest.model.User;
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
import static org.mockito.Mockito.*;


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
    UserConverter userConverter = new UserConverter();
    userService = new UserService(userRepository, userConverter);

    userDto1 = new UserDto(UUID.randomUUID(), "Patrick", "Male", 25);
    user1 = userConverter.convertDtoToEntity(userDto1);

    userDto2 = new UserDto(UUID.randomUUID(), "Hannes", "Male", 24);
    user2 = userConverter.convertDtoToEntity(userDto2);
  }

  @AfterEach
  void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void getAll() {
    when(userRepository.findAll()).thenReturn(List.of(user1, user2));
    List<UserDto> usersDto = userService.getAll();

    assertEquals(2, usersDto.size());
    verify(userRepository).findAll();
  }

  @Test
  void getAll_UserNotFound() {
    when(userRepository.findAll()).thenReturn(List.of());
    List<UserDto> usersDto = userService.getAll();

    assertEquals(0, usersDto.size());
    verify(userRepository).findAll();
  }

  @Test
  void get() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    UserDto userDto = userService.get(user1.getId());

    assertThat(userDto).isNotNull();
    assertEquals(user1.getId(), userDto.getId());
    assertEquals(user1.getName(), userDto.getName());
    assertEquals(user1.getGender().getValue(), userDto.getGender());
    assertEquals(user1.getAge(), userDto.getAge());
    verify(userRepository).findById(user1.getId());
  }

  @Test
  void get_UserNotFound() {
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.get(user1.getId()));
    assertEquals(USER_GET_NOT_FOUND, exception.getMessage());
  }

  @Test
  void create() {
    when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user1);
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    UserDto userDto = userService.create(userDto1);

    assertThat(userDto).isNotNull();
    assertEquals(userDto1.getId(), userDto.getId());
    assertEquals(userDto1.getName(), userDto.getName());
    assertTrue(userDto1.getGender().equalsIgnoreCase(userDto.getGender()));
    assertEquals(userDto1.getAge(), userDto.getAge());
  }

  @Test
  void createWithId() {
    when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user1);
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    UserDto userDto = userService.create(userDto1.getId(), userDto1);

    assertThat(userDto).isNotNull();
    assertEquals(userDto1.getId(), userDto.getId());
    assertEquals(userDto1.getName(), userDto.getName());
    assertTrue(userDto1.getGender().equalsIgnoreCase(userDto.getGender()));
    assertEquals(userDto1.getAge(), userDto.getAge());
  }

  @Test
  void update() {
    when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user1);
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.of(user1));
    UserDto userDto = userService.update(userDto1.getId(), userDto2);

    assertThat(userDto).isNotNull();
    assertEquals(user1.getId(), userDto.getId());
    assertEquals(user2.getName(), userDto.getName());
    assertEquals(user2.getGender().getValue(), userDto.getGender());
    assertEquals(user2.getAge(), userDto.getAge());
  }

  @Test
  void update_UserNotFound() {
    when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user1);
    when(userRepository.findById(ArgumentMatchers.any(UUID.class))).thenReturn(Optional.empty());
    NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.update(userDto1.getId(), userDto2));
    assertEquals(USER_UPDATE_NOT_FOUND, exception.getMessage());
  }

  @Test
  void delete() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    boolean userDeleted = userService.delete(userDto1.getId());
    assertTrue(userDeleted);
  }

  @Test
  void delete_UserDoesNotExist() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.exists(userDto1.getId()));
    assertEquals(USER_DOES_NOT_EXIST, exception.getMessage());
  }

  @Test
  void exists() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(true);
    boolean userExists = userService.exists(userDto1.getId());
    assertTrue(userExists);
  }

  @Test
  void exists_UserDoesNotExist() {
    when(userRepository.existsById(ArgumentMatchers.any(UUID.class))).thenReturn(false);
    NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.exists(userDto1.getId()));
    assertEquals(USER_DOES_NOT_EXIST, exception.getMessage());
  }

  @Test
  void validate() {
    boolean userValid = userService.validate(userDto1);
    assertTrue(userValid);
  }

  @Test
  void validate_NameLessThanLimit() {
    userDto1.setName("T");
    NotValidException exception = assertThrows(NotValidException.class, () -> userService.validate(userDto1));
    assertEquals(USER_VALIDATION_NAME_ERROR, exception.getMessage());
  }

  @Test
  void validate_NameGreaterThanLimit() {
    userDto1.setName("Bartholomew");
    NotValidException exception = assertThrows(NotValidException.class, () -> userService.validate(userDto1));
    assertEquals(USER_VALIDATION_NAME_ERROR, exception.getMessage());
  }

  @Test
  void validate_GenderNotMaleOrFemale() {
    userDto1.setGender("Non-Binary");
    NotValidException exception = assertThrows(NotValidException.class, () -> userService.validate(userDto1));
    assertEquals(USER_VALIDATION_GENDER_ERROR, exception.getMessage());
  }

  @Test
  void validate_AgeLessThanLimit() {
    userDto1.setAge(4);
    NotValidException exception = assertThrows(NotValidException.class, () -> userService.validate(userDto1));
    assertEquals(USER_VALIDATION_AGE_ERROR, exception.getMessage());
  }

  @Test
  void validate_AgeGreaterThanLimit() {
    userDto1.setAge(125);
    NotValidException exception = assertThrows(NotValidException.class, () -> userService.validate(userDto1));
    assertEquals(USER_VALIDATION_AGE_ERROR, exception.getMessage());
  }
}