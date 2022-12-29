package com.github.personalitytest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Personality Test"))
public class PersonalityTestApplication {
  public static void main(String[] args) {
    SpringApplication.run(PersonalityTestApplication.class, args);
  }
}
