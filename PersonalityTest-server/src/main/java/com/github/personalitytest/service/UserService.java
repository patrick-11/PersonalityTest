package com.github.personalitytest.service;

import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.mapper.UserMapper;
import com.github.personalitytest.repository.UserRepository;
import com.github.personalitytest.type.Gender;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService implements ServiceBasic<UserDto> {

  @Autowired
  UserRepository userRepository;

  @Override
  public List<UserDto> getAll() {
    return userRepository.findAll().stream().map(UserMapper.INSTANCE::toDto).toList();
  }

  @Override
  public UserDto get(UUID id) {
    return userRepository.findById(id).map(UserMapper.INSTANCE::toDto)
        .orElseThrow(() -> new NotFoundException(ErrorResponse.USER_GET_NOT_FOUND));
  }

  @Override
  public UserDto create(UserDto userDto) {
    var user = UserMapper.INSTANCE.toEntity(userDto).toBuilder().id(UUID.randomUUID()).build();
    userRepository.save(user);
    return get(user.getId());
  }

  @Override
  public UserDto create(UUID id, UserDto userDto) {
    var user = UserMapper.INSTANCE.toEntity(userDto).toBuilder().id(id).build();
    userRepository.save(user);
    return get(user.getId());
  }

  @Override
  public UserDto update(UUID id, UserDto userDto) {
    var user = userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(ErrorResponse.USER_UPDATE_NOT_FOUND))
        .toBuilder().name(userDto.getName()).gender(Gender.fromName(userDto.getGender())).age(userDto.getAge()).build();
    userRepository.save(user);
    return get(user.getId());
  }

  @Override
  public boolean delete(UUID id) {
    if (exists(id)) {
      userRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Override
  public boolean exists(UUID id) {
    if (!userRepository.existsById(id)) {
      throw new NotFoundException(ErrorResponse.USER_DOES_NOT_EXIST);
    }
    return true;
  }
}
