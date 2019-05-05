package com.codetudes.macroplanapi.db.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.codetudes.macroplanapi.db.domain.plan.PlanTemplate;

@Repository
public interface PlanTemplateRepository extends PagingAndSortingRepository<PlanTemplate, Long> {

}
