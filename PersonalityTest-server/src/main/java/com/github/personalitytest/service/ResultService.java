package com.github.personalitytest.service;

import com.github.personalitytest.converter.ResultConverter;
import com.github.personalitytest.converter.UserConverter;
import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.repository.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
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
    return resultRepository.findAll().stream().map(resultConverter::convertEntityToDto).toList();
  }

  @Override
  public ResultDto get(UUID id) {
    return resultRepository.findById(id).map(resultConverter::convertEntityToDto)
        .orElseThrow(() -> new NotFoundException(ErrorResponse.RESULT_GET_NOT_FOUND));
  }

  public List<ResultDto> findByUser(UUID userId) {
    return resultRepository.findByUser(userId).stream().map(resultConverter::convertEntityToDto).toList();
  }

  @Override
  public ResultDto create(ResultDto dto) {
    return null;
  }

  @Override
  public ResultDto create(UUID userId, ResultDto resultDto) {
    var user = userConverter.convertDtoToEntity(userService.get(userId));
    var result = resultConverter.convertDtoToEntity(resultDto).toBuilder().id(UUID.randomUUID()).user(user).build();
    result.calculateResults();
    result.calculateAvgScore();
    resultRepository.save(result);
    return get(result.getId());
  }

  @Override
  public ResultDto update(UUID id, ResultDto resultDto) {
    var result = resultRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(ErrorResponse.RESULT_UPDATE_NOT_FOUND))
        .toBuilder().answers(resultDto.getAnswers()).build();
    result.calculateResults();
    result.calculateAvgScore();
    resultRepository.save(result);
    return get(result.getId());
  }

  @Override
  public boolean delete(UUID id) {
    if (exists(id)) {
      resultRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public boolean deleteByUserId(UUID userId) {
    if (userService.exists(userId)) {
      findByUser(userId).forEach(resultDto -> delete(resultDto.getId()));
      return true;
    }
    return false;
  }

  @Override
  public boolean exists(UUID id) {
    if (!resultRepository.existsById(id)) {
      throw new NotFoundException(ErrorResponse.RESULT_DOES_NOT_EXIST);
    }
    return true;
  }
}
