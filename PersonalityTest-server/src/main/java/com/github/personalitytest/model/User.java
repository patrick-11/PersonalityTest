package com.github.personalitytest.model;

import com.github.personalitytest.type.Gender;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@Entity(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

  @Id
  private UUID id;
  private String name;
  private Gender gender;
  private int age;
  @ToString.Exclude
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Result> results;
}
