package com.food.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.api.openFact.OpenFactObject;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OpenFactUtilities openFactUtilities;
	
	public Product getProductByCodebar(String barcode) {
		OpenFactObject factObject = openFactUtilities.get(barcode);
		Product product = openFactUtilities.mapOpenFactObjectToProduct(factObject);
		productRepository.save(product);
		return product;
	}
	
}
