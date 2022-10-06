package com.github.personalitytest.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity(name = "results")
@NoArgsConstructor
@ToString
public class Result {

  @Id
  private @Getter @Setter UUID id;
  @CreationTimestamp
  private @Getter @Setter Timestamp completed;
  @ManyToOne @JoinColumn
  private @Getter @Setter User user;
  @ElementCollection(targetClass=Integer.class)
  private @Getter @Setter List<Integer> answers;
  @ElementCollection(targetClass=Double.class)
  private @Getter @Setter List<Double> results;
  private @Getter @Setter double avgScore;

  public void calculateResults() {
    List<Double> temp = new ArrayList<>();
    temp.add((getAnswers().get(0) + reverse(getAnswers().get(5)))/2);
    temp.add((getAnswers().get(6) + reverse(getAnswers().get(1)))/2);
    temp.add((getAnswers().get(2) + reverse(getAnswers().get(7)))/2);
    temp.add((getAnswers().get(8) + reverse(getAnswers().get(3)))/2);
    temp.add((getAnswers().get(4) + reverse(getAnswers().get(9)))/2);
    setResults(temp);
  }

  public void calculateAvgScore() {
    setAvgScore(getResults().stream().reduce(0.0, Double::sum)/getResults().size());
  }

  private double reverse(double value) {
    return (8 - value);
  }

}
