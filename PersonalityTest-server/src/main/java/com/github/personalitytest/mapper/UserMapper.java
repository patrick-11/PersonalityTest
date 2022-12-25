package com.github.personalitytest.mapper;

import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper extends MapperBasic<User, UserDto> {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Override
  UserDto toDto(User entity);

  @Override
  User toEntity(UserDto dto);
}
