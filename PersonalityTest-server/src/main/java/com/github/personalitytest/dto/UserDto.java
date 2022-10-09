package com.github.personalitytest.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

  private UUID id;
  @NotNull(message = "Name must have a value")
  private String name;
  @NotNull(message = "Gender must have a value")
  private String gender;
  @NotNull(message = "Age must have a value")
  private int age;
}
