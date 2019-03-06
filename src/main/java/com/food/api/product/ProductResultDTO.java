package com.food.api.product;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.food.api.rules.NutritionScore;
import com.food.api.rules.NutritionScoreService;

import lombok.Data;

@Data
public class ProductResultDTO {
	@Autowired
	NutritionScoreService nutritionScoreService;
	public static List<NutritionScore> nutritionScores;
	private String productName;
	private String barcode;
	private String status;
	private String color;
	private int score;
	private ArrayList<String> qualities;
	private ArrayList<String> flaws;

	public ProductResultDTO(Product product) {
		nutritionScores = nutritionScoreService.GetAllNutritionScores();

		if(product == null)
			return;
		
		barcode = product.getCodebar();
		productName = product.getName();
		qualities = new ArrayList<>();
		flaws = new ArrayList<>();
		score = product.getEnergy() + product.getSaturated_fat() + product.getSugar() + product.getSalt() 
		- (product.getFibers() + product.getProteins());
		
		nutritionScores.forEach(x -> {
			if(score >= x.getLower_bound() && score <= x.getUpper_bound()) {
				status = x.getClasse();
				color = x.getColor();
			}
		});
		
		
		if(product.getEnergy() <= 3) {
			qualities.add("Energy");
		}
		
		if(product.getEnergy() >= 7) {
			flaws.add("Enegery");
		}
		
		if(product.getSaturated_fat() <= 3) {
			qualities.add("Saturated fats");
		}
		
		if(product.getSaturated_fat() >= 7) {
			flaws.add("Saturated fats");
		}
		
		if(product.getSugar() <= 3) {
			qualities.add("Sugar");
		}
		
		if(product.getSugar() >= 7) {
			flaws.add("Sugar");
		}
		
		if(product.getSalt() <= 3) {
			qualities.add("Salt");
		}
		
		if(product.getSalt() >= 7) {
			flaws.add("Salt");
		}
		
		if(product.getFibers() >= 2) {
			qualities.add("Fibers");
		}
		
		if(product.getFibers() <= 0) {
			flaws.add("Fibers");
		}
		
		if(product.getProteins() >= 2) {
			qualities.add("Proteins");
		}
		
		if(product.getProteins() <= 0) {
			flaws.add("Proteins");
		}
	}
}
