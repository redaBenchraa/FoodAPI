package com.food.api.openFact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OpenFactObject {
	@SerializedName("product")
	private OpenFactProduct product;
	
	public OpenFactObject(){
		product = new OpenFactProduct();
	}
}
