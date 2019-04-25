package com.codetudes.macroplanapi.db.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codetudes.macroplanapi.db.domain.dish.DishTemplate;

@Repository
public interface DishTemplateRepository extends PagingAndSortingRepository<DishTemplate, Long> {
	@Query(
			"SELECT distinct dt FROM com.codetudes.macroplanapi.db.domain.dish.DishTemplate dt " +
			"JOIN dt.ingredients ingr " +
			"JOIN ingr.food ft " +
			"WHERE (" +
			    "LOWER(dt.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
			    "LOWER(ft.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
			    "LOWER(ft.brand) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
			    "LOWER(ft.styleOrFlavor) LIKE LOWER(CONCAT('%',:searchTerm, '%'))" +
		    ")"
	    )
	    List<DishTemplate> findAllViaSearchAndSort(@Param("searchTerm") String searchTerm, Sort sort);
}
