package com.codetudes.macroplanapi.db.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetudes.macroplanapi.db.domain.Food;

@Repository
public interface FoodRepository extends PagingAndSortingRepository<Food, Long> {
	@Query(
		"SELECT f FROM com.codetudes.macroplanapi.db.domain.Food f WHERE " +
		"(f.isTemplate = true) AND " +
		"(" +
		    "LOWER(f.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
		    "LOWER(f.brand) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
		    "LOWER(f.styleOrFlavor) LIKE LOWER(CONCAT('%',:searchTerm, '%'))" +
	    ")"
    )
    List<Food> findAllTemplatesWithSearchAndSort(@Param("searchTerm") String searchTerm, Sort sort);
}
