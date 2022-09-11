package com.github.personalitytest.type;


public enum Gender {

  MALE("Male"),
  FEMALE("Female");

  private final String value;

  Gender(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static Gender fromName(String name) {
    if (name != null) {
      for (Gender type : Gender.values()) {
        if (type.name().equalsIgnoreCase(name.trim())) {
          return type;
        }
      }
    }
    return null;
  }
}
