package com.food.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.food.api.openFact.OpenFactObject;
import com.food.api.openFact.OpenFactProduct;
import com.food.api.product.OpenFactUtilities;
import com.food.api.product.Product;
import com.food.api.product.ProductResultDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenFactTest {
	@Autowired
	OpenFactUtilities openFactUtilities;

	OpenFactObject openFactProduct;

	@Before
	public void beforeEach() {
		openFactProduct = new OpenFactObject();
		openFactProduct.getProduct().getNutriments().setEnergy_100g("1110");
		openFactProduct.getProduct().getNutriments().setSalt_100g((float) 1.2);
		openFactProduct.getProduct().getNutriments().setProteins_100g((float) 8.9);
		openFactProduct.getProduct().getNutriments().setFiber_100g((float) 5.5);
		openFactProduct.getProduct().getNutriments().setSugars_100g((float) 7.2);
		openFactProduct.getProduct().getNutriments().setSaturated_fat_100g((float) 0.4);
	}

	@Test
	public void TestGetOpenFactProduct() {
		OpenFactObject object = openFactUtilities.get("3029330003533");
		assertNotNull(object);
		assertNotNull(object.getProduct());
		assertNotNull(object.getProduct().getNutriments());
		assertEquals(object.getProduct().getNutriments().getEnergy_100g(), "1110");
	}

	@Test
	public void TestMapOpenFactProduct() {
		Product product = openFactUtilities.mapOpenFactObjectToProduct(openFactProduct);
		assertEquals(product.getEnergy(), 3);
		assertEquals(product.getFibers(), 5);
		assertEquals(product.getSalt(), 0);
		assertEquals(product.getSaturated_fat(), 0);
		assertEquals(product.getSugar(), 1);
		assertEquals(product.getProteins(), 5);

	}

	@Test
	public void TestProductResults() {
		Product product = openFactUtilities.mapOpenFactObjectToProduct(openFactProduct);
		ProductResultDTO result = openFactUtilities.CreateProductResultDTO(product);
		assertEquals(result.getQualities().size(), 6);
		assertTrue(result.getQualities().contains("Energy"));
		assertTrue(result.getQualities().contains("Saturated fats"));
		assertTrue(result.getQualities().contains("Sugar"));
		assertTrue(result.getQualities().contains("Salt"));
		assertTrue(result.getQualities().contains("Fibers"));
		assertTrue(result.getQualities().contains("Proteins"));
		assertEquals(result.getScore(), -6);
		assertEquals(result.getColor(), "Green");
		assertEquals(result.getStatus(), "Trop bon");

	}
}
