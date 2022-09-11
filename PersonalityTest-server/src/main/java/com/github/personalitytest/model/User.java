package com.github.personalitytest.model;

import com.github.personalitytest.type.Gender;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;


@Entity(name = "users")
public class User {

  @Id
  private UUID id;
  private String name;
  private Gender gender;
  private int age;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Result> results;


  public User() {
  }


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

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Set<Result> getResults() {
    return results;
  }

  public void setResults(Set<Result> results) {
    this.results = results;
  }


  @Override
  public String toString() {
    return "id: " + getId() + " name: " + getName() + " gender: " + getGender() + " age: " + getAge();
  }
}
