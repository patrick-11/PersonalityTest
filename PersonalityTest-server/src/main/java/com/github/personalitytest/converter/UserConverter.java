package com.github.personalitytest.converter;

import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class UserConverter implements ConverterInter<User, UserDto> {

  @Override
  public UserDto convertEntityToDto(User user) {
    UserDto userDto = new ModelMapper().map(user, UserDto.class);
    userDto.setGender(Gender.valueOf(userDto.getGender()).getValue());
    return userDto;
  }

  @Override
  public User convertDtoToEntity(UserDto userDto) {
    userDto.setGender(userDto.getGender().toUpperCase());
    return new ModelMapper().map(userDto, User.class);
  }
}
