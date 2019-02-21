package com.food.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.food.api.openFact.OpenFactObject;
import com.food.api.product.OpenFactUtilities;
import com.food.api.product.Product;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenFactTest {
	@Autowired
	OpenFactUtilities openFactUtilities;
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
		OpenFactObject object = new OpenFactObject();
		object.getProduct().getNutriments().setEnergy_100g("1110");
		object.getProduct().getNutriments().setSalt_100g((float) 1.2);
		object.getProduct().getNutriments().setProteins_100g((float) 8.9);
		object.getProduct().getNutriments().setFiber_100g((float) 5.5);
		object.getProduct().getNutriments().setSugars_100g((float) 7.2);
		object.getProduct().getNutriments().setSaturated_fat_100g((float) 0.4);
		Product product = openFactUtilities.mapOpenFactObjectToProduct(object);
		assertEquals(product.getEnergy(), 3);
		assertEquals(product.getFibers(), 5);
		assertEquals(product.getSalt(), 0);
		assertEquals(product.getSaturated_fat(), 0);
		assertEquals(product.getSugar(), 1);
		assertEquals(product.getProteins(), 5);
		
	}
}
