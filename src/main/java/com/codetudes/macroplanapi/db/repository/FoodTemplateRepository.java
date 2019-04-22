package com.codetudes.macroplanapi.db.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetudes.macroplanapi.db.domain.food.FoodTemplate;

@Repository
public interface FoodTemplateRepository extends PagingAndSortingRepository<FoodTemplate, Long> {
	@Query(
		"SELECT ft FROM com.codetudes.macroplanapi.db.domain.food.FoodTemplate ft WHERE " +
		"(" +
		    "LOWER(ft.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
		    "LOWER(ft.brand) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
		    "LOWER(ft.styleOrFlavor) LIKE LOWER(CONCAT('%',:searchTerm, '%'))" +
	    ")"
    )
    List<FoodTemplate> findAllViaSearchAndSort(@Param("searchTerm") String searchTerm, Sort sort);
}
