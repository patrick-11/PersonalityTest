package com.github.personalitytest.type;

import com.github.personalitytest.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum Gender {

  MALE(Constants.MALE_VALUE),
  FEMALE(Constants.FEMALE_VALUE);

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

  public static class Constants {
    public static final String MALE = "MALE";
    public static final String FEMALE = "FEMALE";
    public static final String MALE_VALUE = "Male";
    public static final String FEMALE_VALUE = "Female";
  }
}
