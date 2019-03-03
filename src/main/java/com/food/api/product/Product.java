package com.food.api.product;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.food.api.basket.Basket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Product")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String codebar;
	private int energy;
	private int saturated_fat;
	private int sugar;
	private int salt;
	private int fibers;
	private int proteins;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="basket_id")
	@JsonBackReference
	private Basket basket;
}
