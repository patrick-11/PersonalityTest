package com.github.personalitytest.repository;

import com.github.personalitytest.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ResultRepository extends JpaRepository<Result, UUID> {
}
