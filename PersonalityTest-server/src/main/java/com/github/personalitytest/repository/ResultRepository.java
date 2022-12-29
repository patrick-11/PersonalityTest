package com.github.personalitytest.repository;

import com.github.personalitytest.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ResultRepository extends JpaRepository<Result, UUID> {

  @Query("SELECT r FROM results r WHERE r.user.id=?1")
  List<Result> findByUserId(UUID id);
}
