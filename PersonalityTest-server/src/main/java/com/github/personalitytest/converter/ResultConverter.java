package com.github.personalitytest.converter;

import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.type.Gender;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class ResultConverter {

  public ResultDto convertEntityToDto(Result result) {
    ModelMapper modelMapper = new ModelMapper();
    ResultDto resultDto = modelMapper.map(result, ResultDto.class);
    UserDto userDto = resultDto.getUser();
    userDto.setGender(Gender.fromName(userDto.getGender()).getValue());
    resultDto.setUser(userDto);
    return resultDto;
  }

  public Result convertDtoToEntity(ResultDto resultDto) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(resultDto, Result.class);
  }
}
