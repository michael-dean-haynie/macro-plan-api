package com.codetudes.macroplanapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codetudes.macroplanapi.db.domain.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {
	
}
