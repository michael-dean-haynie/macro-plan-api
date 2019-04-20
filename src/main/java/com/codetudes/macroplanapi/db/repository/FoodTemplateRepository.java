package com.codetudes.macroplanapi.db.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetudes.macroplanapi.db.domain.FoodTemplate;

@Repository
public interface FoodTemplateRepository extends PagingAndSortingRepository<FoodTemplate, Long> {
	@Query(
		"SELECT f FROM com.codetudes.macroplanapi.db.domain.FoodTemplate f WHERE " +
		"(" +
		    "LOWER(f.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
		    "LOWER(f.brand) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
		    "LOWER(f.styleOrFlavor) LIKE LOWER(CONCAT('%',:searchTerm, '%'))" +
	    ")"
    )
    List<FoodTemplate> findAllViaSearchAndSort(@Param("searchTerm") String searchTerm, Sort sort);
}
