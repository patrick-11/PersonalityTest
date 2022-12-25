package com.github.personalitytest.converter;

public interface ConverterBasic<E, D> {

  D convertEntityToDto(E entity);

  E convertDtoToEntity(D dto);
}
