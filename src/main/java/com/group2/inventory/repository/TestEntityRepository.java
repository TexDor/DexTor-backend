package com.group2.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group2.inventory.model.TestEntity;

@Repository
public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}

