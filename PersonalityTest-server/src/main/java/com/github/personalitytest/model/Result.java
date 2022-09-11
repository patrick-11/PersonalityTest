package com.github.personalitytest.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity(name = "results")
public class Result {

  @Id
  private UUID id;
  @CreationTimestamp
  private Timestamp completed;
  @ManyToOne
  @JoinColumn
  private User user;
  @ElementCollection(targetClass=Integer.class)
  private List<Integer> answers;
  @ElementCollection(targetClass=Double.class)
  private List<Double> results;
  private double avgScore;


  public Result() {
  }


  public void calculateResults() {
    List<Double> temp = new ArrayList<>();
    temp.add((answers.get(0) + reverse(answers.get(5)))/2);
    temp.add((answers.get(6) + reverse(answers.get(1)))/2);
    temp.add((answers.get(2) + reverse(answers.get(7)))/2);
    temp.add((answers.get(8) + reverse(answers.get(3)))/2);
    temp.add((answers.get(4) + reverse(answers.get(9)))/2);
    setResults(temp);
  }

  public void calculateAvgScore() {
    double temp = 0;
    for (double result : results) {
      temp += result;
    }
    setAvgScore(temp/results.size());
  }

  private double reverse(double value) {
    return (8 - value);
  }


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


  public User getUser() {
    return user;
  }

  public void setUser(User user) {
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
