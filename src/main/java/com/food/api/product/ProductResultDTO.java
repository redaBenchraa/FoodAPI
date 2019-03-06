package com.food.api.product;


import java.util.ArrayList;

import lombok.Data;

@Data
public class ProductResultDTO {
	
	private String productName;
	private String barcode;
	private String status;
	private String color;
	private int score;
	private ArrayList<String> qualities;
	private ArrayList<String> flaws;
	private ArrayList<String> additives;

	public ProductResultDTO() {
		qualities = new ArrayList<String>();
		flaws = new ArrayList<>();
		additives = new ArrayList<String>();
		
	}
	
}
