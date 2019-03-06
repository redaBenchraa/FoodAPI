package com.food.api.openFact;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFactProduct {
	private Nutriments nutriments;
	private String product_name;
	private String code;
	private ArrayList<String> additives_tags = new ArrayList<String>();
	public OpenFactProduct(){
		additives_tags = new ArrayList<String>();
		nutriments = new Nutriments();
	}
}
