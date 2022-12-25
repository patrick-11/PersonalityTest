package com.github.personalitytest.mapper;

public interface MapperBasic<E, D> {

  D convertEntityToDto(E entity);

  E convertDtoToEntity(D dto);
}
