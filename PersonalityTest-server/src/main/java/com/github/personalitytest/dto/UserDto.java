package com.github.personalitytest.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;


public class UserDto {

  private UUID id;
  @NotNull(message = "Name must have a value")
  private String name;
  @NotNull(message = "Gender must have a value")
  private String gender;
  @NotNull(message = "Age must have a value")
  private int age;


  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }


  @Override
  public String toString() {
    return "id: " + getId() + " name: " + getName() + " gender: " + getGender() + " age: " + getAge();
  }

}
