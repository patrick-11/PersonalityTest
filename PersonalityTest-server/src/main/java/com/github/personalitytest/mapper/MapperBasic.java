package com.github.personalitytest.mapper;

public interface MapperBasic<E, D> {

  D toDto(E entity);

  E toEntity(D dto);
}
