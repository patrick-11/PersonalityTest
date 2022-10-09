package com.github.personalitytest.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
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
  private Timestamp completed;
  private UserDto user;
  @NotNull(message = "Answers must have a value")
  private List<Integer> answers;
  private List<Double> results;
  private double avgScore;
}
