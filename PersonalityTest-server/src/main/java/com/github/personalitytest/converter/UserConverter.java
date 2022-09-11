package com.github.personalitytest.converter;

import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

  public UserDto convertEntityToDto(User user) {
    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto = modelMapper.map(user, UserDto.class);
    userDto.setGender(Gender.fromName(userDto.getGender()).getValue());
    return userDto;
  }

  public User convertDtoToEntity(UserDto userDto) {
    ModelMapper modelMapper = new ModelMapper();
    userDto.setGender(userDto.getGender().toUpperCase());
    return modelMapper.map(userDto, User.class);
  }
}
