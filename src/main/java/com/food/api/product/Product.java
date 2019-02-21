package com.food.api.product;


import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
	@Id
	private int id;
	private String name;
	private String codebar;
	private int energy;
	private int saturated_fat;
	private int sugar;
	private int salt;
	private int fibers;
	private int proteins;
}
