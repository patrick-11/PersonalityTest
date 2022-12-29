package com.github.personalitytest.service.impl;

import com.github.personalitytest.dto.ResultDto;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.exception.NotFoundException;
import com.github.personalitytest.mapper.ResultMapper;
import com.github.personalitytest.mapper.UserMapper;
import com.github.personalitytest.repository.ResultRepository;
import com.github.personalitytest.service.ResultService;
import com.github.personalitytest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

  private final ResultRepository resultRepository;
  private final UserService userService;

  @Override
  public List<ResultDto> getAll() {
    return resultRepository.findAll().stream().map(ResultMapper.INSTANCE::toDto).toList();
  }

  @Override
  public ResultDto get(UUID id) {
    return resultRepository.findById(id).map(ResultMapper.INSTANCE::toDto)
        .orElseThrow(() -> new NotFoundException(ErrorResponse.RESULT_GET_NOT_FOUND));
  }

  @Override
  public List<ResultDto> getByUserId(UUID id) {
    return resultRepository.findByUserId(id).stream().map(ResultMapper.INSTANCE::toDto).toList();
  }

  @Override
  public ResultDto create(ResultDto dto) {
    return null;
  }

  @Override
  public ResultDto create(UUID userId, ResultDto resultDto) {
    var user = UserMapper.INSTANCE.toEntity(userService.get(userId));
    var result = ResultMapper.INSTANCE.toEntity(resultDto).toBuilder().id(UUID.randomUUID()).user(user).build();
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

  @Override
  public boolean deleteByUserId(UUID id) {
    if (userService.exists(id)) {
      getByUserId(id).forEach(resultDto -> delete(resultDto.getId()));
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
