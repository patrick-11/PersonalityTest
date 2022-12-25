package com.github.personalitytest.converter;

import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.type.Gender;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class ResultConverter implements ConverterBasic<Result, ResultDto> {

  @Override
  public ResultDto convertEntityToDto(Result result) {
    var resultDto = new ModelMapper().map(result, ResultDto.class);
    var userDto = resultDto.getUser();
    userDto.setGender(Gender.valueOf(userDto.getGender()).getValue());
    resultDto.setUser(userDto);
    return resultDto;
  }

  @Override
  public Result convertDtoToEntity(ResultDto resultDto) {
    if (resultDto.getUser() != null) {
      var userDto = resultDto.getUser();
      userDto.setGender(userDto.getGender().toUpperCase());
    }
    return new ModelMapper().map(resultDto, Result.class);
  }
}
