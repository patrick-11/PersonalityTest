package com.github.personalitytest.model;

import com.github.personalitytest.type.Gender;
import com.github.personalitytest.type.converter.GenderAttributeConverter;
import lombok.*;

import javax.persistence.*;
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
  @Column(nullable = false)
  private UUID id;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  @Convert(converter = GenderAttributeConverter.class)
  private Gender gender;
  @Column(nullable = false)
  private int age;
  @ToString.Exclude
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Result> results;
}
