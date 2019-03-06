package com.food.api.rules;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NutritionScoreService {
	@Autowired
	NutritionScoreRepository nutritionScoreRepository;
	public List<NutritionScore> GetAllNutritionScores(){
		return (List<NutritionScore>) nutritionScoreRepository.findAll(); 
	}
}
