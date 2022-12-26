package com.github.personalitytest.type.converter;

import com.github.personalitytest.type.Gender;

import javax.persistence.AttributeConverter;

public class GenderAttributeConverter implements AttributeConverter<Gender, String> {
  @Override
  public String convertToDatabaseColumn(Gender gender) {
    return gender.getValue();
  }

  @Override
  public Gender convertToEntityAttribute(String value) {
    return Gender.fromValue(value);
  }
}
