package com.github.personalitytest.model;

import com.github.personalitytest.type.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@Entity(name = "users")
@NoArgsConstructor
@ToString
public class User {

  @Id
  private @Getter @Setter UUID id;
  private @Getter @Setter String name;
  private @Getter @Setter Gender gender;
  private @Getter @Setter int age;
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private @Getter @Setter @ToString.Exclude Set<Result> results;

}
