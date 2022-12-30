package com.github.personalitytest.mapper;

import com.github.personalitytest.type.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;

@Mapper
public interface GenderMapper {

  @ValueMapping(source = Gender.Constants.MALE_VALUE, target = Gender.Constants.MALE)
  @ValueMapping(source = Gender.Constants.FEMALE_VALUE, target = Gender.Constants.FEMALE)
  Gender genderStringToGender(String genderString);

  @ValueMapping(source = Gender.Constants.MALE, target = Gender.Constants.MALE_VALUE)
  @ValueMapping(source = Gender.Constants.FEMALE, target = Gender.Constants.FEMALE_VALUE)
  String genderGenderToString(Gender genderGender);
}
