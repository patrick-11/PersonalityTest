package com.github.personalitytest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ToString
public class UserDto {

  private @Getter @Setter UUID id;
  @NotNull(message = "Name must have a value")
  private @Getter @Setter String name;
  @NotNull(message = "Gender must have a value")
  private @Getter @Setter String gender;
  @NotNull(message = "Age must have a value")
  private @Getter @Setter int age;

}
