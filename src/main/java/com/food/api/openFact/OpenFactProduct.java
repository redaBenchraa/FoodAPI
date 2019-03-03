package com.food.api.openFact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFactProduct {
	private Nutriments nutriments;
	private String product_name;
	private String code;
	public OpenFactProduct(){
		nutriments = new Nutriments();
	}
}
