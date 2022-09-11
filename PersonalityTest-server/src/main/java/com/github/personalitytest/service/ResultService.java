package com.github.personalitytest.service;

import com.github.personalitytest.converter.ResultConverter;
import com.github.personalitytest.converter.UserConverter;
import com.github.personalitytest.dto.UserDto;
import com.github.personalitytest.exception.NotValidException;
import com.github.personalitytest.repository.ResultRepository;
import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.model.Result;
import com.github.personalitytest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ResultService implements ServiceInter<ResultDto> {

  @Autowired
  ResultRepository resultRepository;
  @Autowired
  UserService userService;
  @Autowired
  ResultConverter resultConverter;
  @Autowired
  UserConverter userConverter;

  @Override
  public List<ResultDto> getAll() {
    return resultRepository.findAll().stream().map(result -> resultConverter.convertEntityToDto(result)).toList();
  }

  @Override
  public ResultDto get(UUID id) {
    Optional<Result> result = resultRepository.findById(id);
    if(result.isEmpty()) {
      throw new NotFoundException("Result not found. Fetch was unsuccessful!");
    }
    return resultConverter.convertEntityToDto(result.get());
  }

  public List<ResultDto> getByUserId(UUID userId) {
    return resultRepository.findAll().stream().filter(result -> result.getUser().getId().equals(userId))
        .map(result -> resultConverter.convertEntityToDto(result)).toList();
  }

  @Override
  public ResultDto create(ResultDto dto) {
    return null;
  }

  @Override
  public ResultDto create(UUID userId, ResultDto resultDto) {
    if(!validate(resultDto)) {
      return resultDto;
    }
    UserDto userDto = userService.get(userId);
    Result result = resultConverter.convertDtoToEntity(resultDto);
    User user = userConverter.convertDtoToEntity(userDto);
    result.setId(UUID.randomUUID());
    result.setUser(user);
    result.calculateResults();
    result.calculateAvgScore();
    resultRepository.save(result);
    return get(result.getId());
  }

  @Override
  public ResultDto update(UUID id, ResultDto resultDto) {
    if(!exists(id) || !validate(resultDto)) {
      return resultDto;
    }
    Optional<Result> result = resultRepository.findById(id);
    if(result.isEmpty()) {
      throw new NotFoundException("Result not found. Update was unsuccessful!");
    }
    result.get().setAnswers(resultDto.getAnswers());
    result.get().calculateResults();
    result.get().calculateAvgScore();
    resultRepository.save(result.get());
    return get(result.get().getId());
  }

  @Override
  public boolean delete(UUID id) {
    if(exists(id)) {
      resultRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public boolean deleteByUserId(UUID userId) {
    if(userService.exists(userId)) {
      getByUserId(userId).forEach(resultDto -> delete(resultDto.getId()));
      return true;
    }
    return false;
  }

  @Override
  public boolean exists(UUID id) {
    if(!resultRepository.existsById(id)) {
      throw new NotFoundException("Result does not exist!");
    }
    return true;
  }

  @Override
  public boolean validate(ResultDto resultDto) {
    if(resultDto.getAnswers().size() != 10) {
      throw new NotValidException("Answers must have 10 entries!");
    }
    for(int i = 0; i < resultDto.getAnswers().size(); i++) {
      int answer = resultDto.getAnswers().get(i);
      if(answer < 1 || answer > 7) {
        throw new NotValidException("Answer on position " + i + " must be between 1 and 7!");
      }
    }
    return true;
  }
}
