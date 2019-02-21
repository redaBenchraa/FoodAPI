package com.food.api.openFact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nutriments {
 private float salt_100g;
 @SerializedName("saturated-fat_100g")
 private float saturated_fat_100g;
 private String fiber_value;
 private float carbohydrates_serving;
 private String carbohydrates_100g;
 @SerializedName("saturated-fat_serving")
 private float saturated_fat_serving;
 private String fat_value;
 @SerializedName("nutrition-score-uk")
 private float nutrition_score_uk;
 @SerializedName("nutrition-score-fr_100g")
 private float nutrition_score_fr_100g;
 private String energy_unit;
 private String fat_unit;
 private float salt;
 private String sodium_unit;
 private String proteins_unit;
 private float fiber_100g;
 private float fiber;
 private String energy_serving;
 private String energy_value;
 private String salt_unit;
 private String sugars_value;
 private float proteins_serving;
 private float salt_serving;
 private String energy;
 private String fiber_unit;
 private float sugars_100g;
 private String nova_group_100g;
 private String proteins_value;
 @SerializedName("saturated-fat_value")
 private String saturated_fat_value;
 @SerializedName("saturated-fat")
 private float saturated_fat;
 private float sodium_serving;
 private String energy_100g;
 private float proteins;
 @SerializedName("nutrition-score-uk_100g")
 private float nutrition_score_uk_100g;
 private float sugars_serving;
 private String carbohydrates_value;
 private String sodium_value;
 private float sugars;
 private String carbohydrates;
 private float proteins_100g;
 private float fat_100g;
 @SerializedName("nova-group_serving")
 private String nova_group_serving;
 private String salt_value;
 @SerializedName("nova-group")
 private String nova_group;
 @SerializedName("nutrition-score-fr")
 private float nutrition_score_fr;
 private String sugars_unit;
 private float sodium;
 @SerializedName("saturated-fat_unit")
 private String saturated_fat_unit;
 private float fat_serving;
 private float fiber_serving;
 private String carbohydrates_unit;
 private float fat;
 private float sodium_100g;


}