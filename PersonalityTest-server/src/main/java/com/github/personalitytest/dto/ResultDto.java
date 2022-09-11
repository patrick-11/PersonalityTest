package com.github.personalitytest.dto;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


public class ResultDto {

  private UUID id;
  private Timestamp completed;
  private UserDto user;
  @NotNull(message = "Answers must have a value")
  private List<Integer> answers;
  private List<Double> results;
  private double avgScore;


  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Timestamp getCompleted() {
    return completed;
  }

  public void setCompleted(Timestamp completed) {
    this.completed = completed;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public List<Integer> getAnswers() {
    return answers;
  }

  public void setAnswers(List<Integer> answers) {
    this.answers = answers;
  }

  public List<Double> getResults() {
    return results;
  }

  public void setResults(List<Double> results) {
    this.results = results;
  }

  public double getAvgScore() {
    return avgScore;
  }

  public void setAvgScore(double avgScore) {
    this.avgScore = avgScore;
  }

  @Override
  public String toString() {
    return "id: " + getId() + " completed: " + getCompleted() + " user: " + getUser() + " answers: " + getAnswers() +
        " results: " + getResults() + " avgScore: " + getAvgScore();
  }
}
