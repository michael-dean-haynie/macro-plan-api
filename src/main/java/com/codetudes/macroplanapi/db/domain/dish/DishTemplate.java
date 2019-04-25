package com.codetudes.macroplanapi.db.domain.dish;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;

import com.codetudes.macroplanapi.db.domain.Measurement;
import com.codetudes.macroplanapi.db.domain.ingredient.IngredientTemplate;

import lombok.Data;

@Data
@Entity(name="dish_template")
public class DishTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Long id;
	
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Measurement> measurements;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<IngredientTemplate> ingredients;
	
	private Boolean isTemplate;
	
	/**
	 * ---------------------------------------------------------------------------------
	 * Calculated fields (not returned, just used for sorting)
	 * 
	 * pls don't judge me too much (;
	 * 
	 * TODO: the calories, fat, carbs, and protein @Formula values are all mostly the same
	 * 	exept at the end of the select clause: "... * ft.{calories|fat|carbs|protein} ..."
	 * 	I may be able to dry this up by using a hibernate interceptor
	 * 	https://stackoverflow.com/questions/43635745/hibernate-formula-set-value-at-runtime
	 *
	 *	Same idea for percengate values
	 * ---------------------------------------------------------------------------------
	 */
	
	/**
	 * The total calories per serving of the dish template.
	 * (The sum of calories for each ingredient in the dish.)
	 */
	@Formula("COALESCE("
				+ "("
					+ "SELECT SUM("
						+ "(((itServingSize.amount * itServingSizeUnit.unit_type_ratio) "
						+ "/ "
						+ "(ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.calories) "
					+ ") "
					+ "FROM food_template ft "
						+ "INNER JOIN ingredient_template it ON ft.id = it.food_id "
						+ "INNER JOIN dish_template_ingredients dti ON it.id = dti.ingredients_id "
						+ "INNER JOIN dish_template dt ON dti.dish_template_id = dt.id "
						+ "INNER JOIN dish_template_measurements dtm ON dt.id = dtm.dish_template_id "
						+ "INNER JOIN measurement itServingSize ON it.measurement_id = itServingSize.id "
						+ "INNER JOIN unit itServingSizeUnit ON itServingSize.unit_id = itServingSizeUnit.id "
						+ "INNER JOIN food_template_measurements ftm ON ft.id = ftm.food_template_id "
						+ "INNER JOIN measurement ftServingSize ON ftm.measurements_id = ftServingSize.id "
						+ "INNER JOIN unit ftServingSizeUnit ON ftServingSize.unit_id = ftServingSizeUnit.id "
					+ "WHERE dt.id = id "
						+ "AND itServingSizeUnit.unit_type = ftServingSizeUnit.unit_type"
				+ ") "
			+ ",0)")
	private Double calories;
	
	/**
	 * The total fat per serving of the dish template.
	 * (The sum of fat for each ingredient in the dish.)
	 */
	@Formula("COALESCE("
			+ "("
				+ "SELECT SUM("
					+ "(((itServingSize.amount * itServingSizeUnit.unit_type_ratio) "
					+ "/ "
					+ "(ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.fat) "
				+ ") "
				+ "FROM food_template ft "
					+ "INNER JOIN ingredient_template it ON ft.id = it.food_id "
					+ "INNER JOIN dish_template_ingredients dti ON it.id = dti.ingredients_id "
					+ "INNER JOIN dish_template dt ON dti.dish_template_id = dt.id "
					+ "INNER JOIN dish_template_measurements dtm ON dt.id = dtm.dish_template_id "
					+ "INNER JOIN measurement itServingSize ON it.measurement_id = itServingSize.id "
					+ "INNER JOIN unit itServingSizeUnit ON itServingSize.unit_id = itServingSizeUnit.id "
					+ "INNER JOIN food_template_measurements ftm ON ft.id = ftm.food_template_id "
					+ "INNER JOIN measurement ftServingSize ON ftm.measurements_id = ftServingSize.id "
					+ "INNER JOIN unit ftServingSizeUnit ON ftServingSize.unit_id = ftServingSizeUnit.id "
				+ "WHERE dt.id = id "
					+ "AND itServingSizeUnit.unit_type = ftServingSizeUnit.unit_type"
			+ ") "
		+ ",0)")
	private Double fat;
	
	/**
	 * The total carbs per serving of the dish template.
	 * (The sum of carbs for each ingredient in the dish.)
	 */
	@Formula("COALESCE("
			+ "("
				+ "SELECT SUM("
					+ "(((itServingSize.amount * itServingSizeUnit.unit_type_ratio) "
					+ "/ "
					+ "(ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.carbs) "
				+ ") "
				+ "FROM food_template ft "
					+ "INNER JOIN ingredient_template it ON ft.id = it.food_id "
					+ "INNER JOIN dish_template_ingredients dti ON it.id = dti.ingredients_id "
					+ "INNER JOIN dish_template dt ON dti.dish_template_id = dt.id "
					+ "INNER JOIN dish_template_measurements dtm ON dt.id = dtm.dish_template_id "
					+ "INNER JOIN measurement itServingSize ON it.measurement_id = itServingSize.id "
					+ "INNER JOIN unit itServingSizeUnit ON itServingSize.unit_id = itServingSizeUnit.id "
					+ "INNER JOIN food_template_measurements ftm ON ft.id = ftm.food_template_id "
					+ "INNER JOIN measurement ftServingSize ON ftm.measurements_id = ftServingSize.id "
					+ "INNER JOIN unit ftServingSizeUnit ON ftServingSize.unit_id = ftServingSizeUnit.id "
				+ "WHERE dt.id = id "
					+ "AND itServingSizeUnit.unit_type = ftServingSizeUnit.unit_type"
			+ ") "
		+ ",0)")
	private Double carbs;
	
	/**
	 * The total protein per serving of the dish template.
	 * (The sum of protein for each ingredient in the dish.)
	 */
	@Formula("COALESCE("
			+ "("
				+ "SELECT SUM("
					+ "(((itServingSize.amount * itServingSizeUnit.unit_type_ratio) "
					+ "/ "
					+ "(ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.protein) "
				+ ") "
				+ "FROM food_template ft "
					+ "INNER JOIN ingredient_template it ON ft.id = it.food_id "
					+ "INNER JOIN dish_template_ingredients dti ON it.id = dti.ingredients_id "
					+ "INNER JOIN dish_template dt ON dti.dish_template_id = dt.id "
					+ "INNER JOIN dish_template_measurements dtm ON dt.id = dtm.dish_template_id "
					+ "INNER JOIN measurement itServingSize ON it.measurement_id = itServingSize.id "
					+ "INNER JOIN unit itServingSizeUnit ON itServingSize.unit_id = itServingSizeUnit.id "
					+ "INNER JOIN food_template_measurements ftm ON ft.id = ftm.food_template_id "
					+ "INNER JOIN measurement ftServingSize ON ftm.measurements_id = ftServingSize.id "
					+ "INNER JOIN unit ftServingSizeUnit ON ftServingSize.unit_id = ftServingSizeUnit.id "
				+ "WHERE dt.id = id "
					+ "AND itServingSizeUnit.unit_type = ftServingSizeUnit.unit_type"
			+ ") "
		+ ",0)")
	private Double protein;
	
	/**
	 * The percentage of calories that are fat in the dish
	 */
	@Formula("COALESCE("
			+ "("
				+ "SELECT "
					// Total calories of fat
					+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.fat * 9) "
					// divided by total calories from fat/carbs/protein
					+ "/ ("
						+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.fat * 9) +"
						+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.carbs * 4) +"
						+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.protein * 4) "
					+ ") * 100 "

				+ "FROM food_template ft "
					+ "INNER JOIN ingredient_template it ON ft.id = it.food_id "
					+ "INNER JOIN dish_template_ingredients dti ON it.id = dti.ingredients_id "
					+ "INNER JOIN dish_template dt ON dti.dish_template_id = dt.id "
					+ "INNER JOIN dish_template_measurements dtm ON dt.id = dtm.dish_template_id "
					+ "INNER JOIN measurement itServingSize ON it.measurement_id = itServingSize.id "
					+ "INNER JOIN unit itServingSizeUnit ON itServingSize.unit_id = itServingSizeUnit.id "
					+ "INNER JOIN food_template_measurements ftm ON ft.id = ftm.food_template_id "
					+ "INNER JOIN measurement ftServingSize ON ftm.measurements_id = ftServingSize.id "
					+ "INNER JOIN unit ftServingSizeUnit ON ftServingSize.unit_id = ftServingSizeUnit.id "
				+ "WHERE dt.id = id "
					+ "AND itServingSizeUnit.unit_type = ftServingSizeUnit.unit_type"
			+ ") "
		+ ",0)")
	private Double fatPercentage;
	
	/**
	 * The percentage of calories that are carbs in the dish
	 */
	@Formula("COALESCE("
			+ "("
				+ "SELECT "
					// Total calories of fat
					+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.carbs * 4) "
					// divided by total calories from fat/carbs/protein
					+ "/ ("
						+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.fat * 9) +"
						+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.carbs * 4) +"
						+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.protein * 4) "
					+ ") * 100 "

				+ "FROM food_template ft "
					+ "INNER JOIN ingredient_template it ON ft.id = it.food_id "
					+ "INNER JOIN dish_template_ingredients dti ON it.id = dti.ingredients_id "
					+ "INNER JOIN dish_template dt ON dti.dish_template_id = dt.id "
					+ "INNER JOIN dish_template_measurements dtm ON dt.id = dtm.dish_template_id "
					+ "INNER JOIN measurement itServingSize ON it.measurement_id = itServingSize.id "
					+ "INNER JOIN unit itServingSizeUnit ON itServingSize.unit_id = itServingSizeUnit.id "
					+ "INNER JOIN food_template_measurements ftm ON ft.id = ftm.food_template_id "
					+ "INNER JOIN measurement ftServingSize ON ftm.measurements_id = ftServingSize.id "
					+ "INNER JOIN unit ftServingSizeUnit ON ftServingSize.unit_id = ftServingSizeUnit.id "
				+ "WHERE dt.id = id "
					+ "AND itServingSizeUnit.unit_type = ftServingSizeUnit.unit_type"
			+ ") "
		+ ",0)")
	private Double carbsPercentage;
	
	/**
	 * The percentage of calories that are protein in the dish
	 */
	@Formula("COALESCE("
			+ "("
				+ "SELECT "
					// Total calories of fat
					+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.protein * 4) "
					// divided by total calories from fat/carbs/protein
					+ "/ ("
						+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.fat * 9) +"
						+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.carbs * 4) +"
						+ "SUM(( (itServingSize.amount * itServingSizeUnit.unit_type_ratio) / (ftServingSize.amount * ftServingSizeUnit.unit_type_ratio)) * ft.protein * 4) "
					+ ") * 100 "

				+ "FROM food_template ft "
					+ "INNER JOIN ingredient_template it ON ft.id = it.food_id "
					+ "INNER JOIN dish_template_ingredients dti ON it.id = dti.ingredients_id "
					+ "INNER JOIN dish_template dt ON dti.dish_template_id = dt.id "
					+ "INNER JOIN dish_template_measurements dtm ON dt.id = dtm.dish_template_id "
					+ "INNER JOIN measurement itServingSize ON it.measurement_id = itServingSize.id "
					+ "INNER JOIN unit itServingSizeUnit ON itServingSize.unit_id = itServingSizeUnit.id "
					+ "INNER JOIN food_template_measurements ftm ON ft.id = ftm.food_template_id "
					+ "INNER JOIN measurement ftServingSize ON ftm.measurements_id = ftServingSize.id "
					+ "INNER JOIN unit ftServingSizeUnit ON ftServingSize.unit_id = ftServingSizeUnit.id "
				+ "WHERE dt.id = id "
					+ "AND itServingSizeUnit.unit_type = ftServingSizeUnit.unit_type"
			+ ") "
		+ ",0)")
	private Double proteinPercentage;
}
