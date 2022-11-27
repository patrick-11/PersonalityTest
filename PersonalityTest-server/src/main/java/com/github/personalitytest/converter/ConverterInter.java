package com.github.personalitytest.converter;

public interface ConverterInter<E, D> {

  D convertEntityToDto(E entity);

  E convertDtoToEntity(D dto);
}
