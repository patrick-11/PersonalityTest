package com.github.personalitytest.model;

import com.github.personalitytest.type.Gender;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;


@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class User {

  public static final int NAME_SIZE_MIN = 2;
  public static final int NAME_SIZE_MAX = 10;
  public static final String AGE_VALUE_MIN = "5";
  public static final String AGE_VALUE_MAX = "120";

  @Id
  private UUID id;
  private String name;
  private Gender gender;
  private int age;
  @ToString.Exclude
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Result> results;
}
