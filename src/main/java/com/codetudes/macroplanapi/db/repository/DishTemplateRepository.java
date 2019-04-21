package com.codetudes.macroplanapi.db.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.codetudes.macroplanapi.db.domain.dish.DishTemplate;

@Repository
public interface DishTemplateRepository extends PagingAndSortingRepository<DishTemplate, Long> {

}