package com.github.personalitytest.mapper;

import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper extends MapperBasic<User, UserDto> {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Override
  @Mapping(source = "gender.value", target = "gender")
  UserDto toDto(User entity);

  @Override
  User toEntity(UserDto dto);

  @ValueMapping(source = "Male", target = "MALE")
  @ValueMapping(source = "Female", target = "FEMALE")
  Gender genderStringToGender(String genderString);
}
