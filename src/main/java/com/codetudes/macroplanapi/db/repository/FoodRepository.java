package com.codetudes.macroplanapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetudes.macroplanapi.db.domain.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

}
