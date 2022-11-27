package com.github.personalitytest.dto;

import com.github.personalitytest.exception.ErrorResponse;
import com.github.personalitytest.model.User;
import com.github.personalitytest.type.Gender;
import com.github.personalitytest.validator.GenderValue;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {

  private UUID id;
  @NotEmpty(message = ErrorResponse.USER_VALIDATION_NAME_EMPTY_ERROR)
  @Size(min = User.NAME_SIZE_MIN, max = User.NAME_SIZE_MAX, message = ErrorResponse.USER_VALIDATION_NAME_SIZE_ERROR)
  private String name;
  @NotEmpty(message = ErrorResponse.USER_VALIDATION_GENDER_EMPTY_ERROR)
  @GenderValue(clazz = Gender.class, message = ErrorResponse.USER_VALIDATION_GENDER_ERROR)
  private String gender;
  @DecimalMin(value = User.AGE_VALUE_MIN, message = ErrorResponse.USER_VALIDATION_AGE_SIZE_ERROR)
  @DecimalMax(value = User.AGE_VALUE_MAX, message = ErrorResponse.USER_VALIDATION_AGE_SIZE_ERROR)
  private int age;
}
