package com.github.personalitytest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@ToString
public class ResultDto {

  private @Getter @Setter UUID id;
  private @Getter @Setter Timestamp completed;
  private @Getter @Setter UserDto user;
  @NotNull(message = "Answers must have a value")
  private @Getter @Setter List<Integer> answers;
  private @Getter @Setter List<Double> results;
  private @Getter @Setter double avgScore;

}
