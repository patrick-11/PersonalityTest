package com.github.personalitytest.service;

import com.github.personalitytest.converter.UserConverter;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.exception.NotValidException;
import com.github.personalitytest.repository.UserRepository;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService implements ServiceInter<UserDto> {

  @Autowired
  UserRepository userRepository;
  @Autowired
  UserConverter userConverter;

  @Override
  public List<UserDto> getAll() {
    return userRepository.findAll().stream().map(user -> userConverter.convertEntityToDto(user)).toList();
  }

  @Override
  public UserDto get(UUID id) {
    Optional<User> user = userRepository.findById(id);
    if(user.isEmpty()) {
      throw new NotFoundException("User not found. Fetch was unsuccessful!");
    }
    return userConverter.convertEntityToDto(user.get());
  }

  @Override
  public UserDto create(UserDto userDto) {
    if(!validate(userDto)) {
      return userDto;
    }
    User user = userConverter.convertDtoToEntity(userDto);
    user.setId(UUID.randomUUID());
    userRepository.save(user);
    return get(user.getId());
  }

  @Override
  public UserDto create(UUID id, UserDto userDto) {
    if(!validate(userDto)) {
      return userDto;
    }
    User user = userConverter.convertDtoToEntity(userDto);
    user.setId(id);
    userRepository.save(user);
    return get(user.getId());
  }

  @Override
  public UserDto update(UUID id, UserDto userDto) {
    if(!validate(userDto)) {
      return userDto;
    }
    Optional<User> user = userRepository.findById(id);
    if(user.isEmpty()) {
      throw new NotFoundException("User not found. Update was unsuccessful!");
    }
    user.get().setName(userDto.getName());
    user.get().setGender(Gender.fromName(userDto.getGender()));
    user.get().setAge(userDto.getAge());
    userRepository.save(user.get());
    return get(user.get().getId());
  }

  @Override
  public boolean delete(UUID id) {
    if(exists(id)) {
      userRepository.deleteById(id);
      return true;
    }
    return false;
  }

  @Override
  public boolean exists(UUID id) {
    if(!userRepository.existsById(id)) {
      throw new NotFoundException("User does not exist!");
    }
    return true;
  }

  @Override
  public boolean validate(UserDto userDto) {
    if(userDto.getName().length() < 2 || userDto.getName().length() > 10) {
      throw new NotValidException("Name is not between 2 and 10 characters long!");
    }
    if(Gender.fromName(userDto.getGender()) == null) {
      throw new NotValidException("Gender is not male or female!");
    }
    if(userDto.getAge() < 5 || userDto.getAge() > 120) {
      throw new NotValidException("Age must be between 5 and 120!");
    }
    return true;
  }
}
