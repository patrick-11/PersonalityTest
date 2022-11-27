package com.github.personalitytest.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {

  MALE("Male"),
  FEMALE("Female");

  private final String value;

  public static Gender fromName(String name) {
    if (name != null) {
      for (var type : Gender.values()) {
        if (type.name().equalsIgnoreCase(name.trim())) {
          return type;
        }
      }
    }
    return null;
  }
}
