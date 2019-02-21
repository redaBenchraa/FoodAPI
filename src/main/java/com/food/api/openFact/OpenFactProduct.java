package com.food.api.openFact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFactProduct {
	private Nutriments nutriments;
	public OpenFactProduct(){
		nutriments = new Nutriments();
	}
}
