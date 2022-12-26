package com.github.personalitytest.mapper;

import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.type.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ResultMapper extends MapperBasic<Result, ResultDto> {

  ResultMapper INSTANCE = Mappers.getMapper(ResultMapper.class);

  @Override
  @Mapping(source = "user.gender.value", target = "user.gender")
  ResultDto toDto(Result entity);

  @Override
  Result toEntity(ResultDto dto);

  @ValueMapping(source = "Male", target = "MALE")
  @ValueMapping(source = "Female", target = "FEMALE")
  Gender genderStringToGender(String genderString);
}
