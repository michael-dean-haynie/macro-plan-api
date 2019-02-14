package com.codetudes.macroplanapi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codetudes.macroplanapi.db.domain.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long>{

}
