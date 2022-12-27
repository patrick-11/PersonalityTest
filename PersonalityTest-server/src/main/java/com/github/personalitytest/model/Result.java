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
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class Result {

  public static final int ANSWERS_SIZE = 10;
  public static final String ANSWERS_VALUE_MIN = "1";
  public static final String ANSWERS_VALUE_MAX = "7";
  public static final String COMPLETED_TIMESTAMP_PATTERN = "dd.MM.yy - HH:mm";

  @Id
  @Column(nullable = false)
  private UUID id;
  @CreationTimestamp
  @Column(nullable = false)
  private Timestamp completed;
  @ManyToOne
  @JoinColumn(nullable = false)
  private User user;
  @ElementCollection(targetClass = Integer.class)
  @Column(nullable = false)
  private List<Integer> answers;
  @ElementCollection(targetClass = Double.class)
  @Column(nullable = false)
  private List<Double> results;
  @Column(nullable = false)
  private double avgScore;

  public void calculateResults() {
    List<Double> temp = new ArrayList<>();
    temp.add((getAnswers().get(0) + reverse(getAnswers().get(5))) / 2);
    temp.add((getAnswers().get(6) + reverse(getAnswers().get(1))) / 2);
    temp.add((getAnswers().get(2) + reverse(getAnswers().get(7))) / 2);
    temp.add((getAnswers().get(8) + reverse(getAnswers().get(3))) / 2);
    temp.add((getAnswers().get(4) + reverse(getAnswers().get(9))) / 2);
    setResults(temp);
  }

  public void calculateAvgScore() {
    setAvgScore(getResults().stream().reduce(0.0, Double::sum) / getResults().size());
  }

  private double reverse(double value) {
    return (8 - value);
  }
}
