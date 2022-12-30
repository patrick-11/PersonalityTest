package com.github.personalitytest.mapper;

import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.model.Result;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface ResultMapper extends MapperBasic<Result, ResultDto>, GenderMapper {

  ResultMapper INSTANCE = Mappers.getMapper(ResultMapper.class);

  @Override
  ResultDto toDto(Result entity);

  @Override
  Result toEntity(ResultDto dto);
}
