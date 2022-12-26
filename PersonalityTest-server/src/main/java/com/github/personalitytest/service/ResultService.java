package com.github.personalitytest.service;

import com.github.personalitytest.dto.ResultDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public interface ResultService extends ServiceBasic<ResultDto> {

  List<ResultDto> getByUserId(UUID userId);

  boolean deleteByUserId(UUID userId);
}
