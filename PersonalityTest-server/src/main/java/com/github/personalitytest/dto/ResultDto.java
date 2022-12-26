package com.github.personalitytest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.model.Result;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResultDto {

  private UUID id;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy - HH:mm")
  private Timestamp completed;
  private UserDto user;
  @NotEmpty(message = ErrorResponse.RESULT_VALIDATION_ANSWER_EMPTY_ERROR)
  @Size(min = Result.ANSWERS_SIZE, max = Result.ANSWERS_SIZE, message = ErrorResponse.RESULT_VALIDATION_ANSWER_SIZE_ERROR)
  private List<
      @DecimalMin(value = Result.ANSWERS_VALUE_MIN, message = ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR)
      @DecimalMax(value = Result.ANSWERS_VALUE_MAX, message = ErrorResponse.RESULT_VALIDATION_ANSWER_VALUE_ERROR)
          Integer> answers;
  private List<Double> results;
  private double avgScore;
}
