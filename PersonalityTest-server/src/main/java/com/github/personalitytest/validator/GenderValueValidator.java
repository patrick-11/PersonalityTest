package com.github.personalitytest.validator;

import com.github.personalitytest.type.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Stream;

public class GenderValueValidator implements ConstraintValidator<GenderValue, CharSequence> {

  private List<String> acceptedValues;

  @Override
  public void initialize(GenderValue constraintAnnotation) {
    acceptedValues = Stream.of(constraintAnnotation.clazz().getEnumConstants())
        .map(constant -> Gender.valueOf(String.valueOf(constant)).getValue())
        .toList();
  }

  @Override
  public boolean isValid(CharSequence charSequence, ConstraintValidatorContext constraintValidatorContext) {
    if (charSequence == null) {
      return true;
    }
    return acceptedValues.contains(charSequence.toString());
  }
}
