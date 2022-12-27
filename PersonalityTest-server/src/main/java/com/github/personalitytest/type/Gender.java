package com.github.personalitytest.type;

import com.github.personalitytest.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum Gender {

  MALE("Male"),
  FEMALE("Female");

  private final String value;

  public static Gender fromValue(String value) {
    if (value != null) {
      for (var gender : Gender.values()) {
        if (Objects.equals(gender.getValue(), value)) {
          return gender;
        }
      }
    }
    throw new IllegalArgumentException(ErrorResponse.GENDER_MAPPING_ERROR);
  }
}
