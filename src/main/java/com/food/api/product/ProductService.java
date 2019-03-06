package com.food.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.api.openFact.OpenFactObject;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private AdditiveRepository additiveRepository;
	
	@Autowired
	private OpenFactUtilities openFactUtilities;
	
	public Product getProductByCodebar(String barcode) {
		OpenFactObject factObject = openFactUtilities.get(barcode);
		Product product = openFactUtilities.mapOpenFactObjectToProduct(factObject);
		return product;
	}
	
	public ProductResultDTO getProductResultByCodebar(String barcode) {
		OpenFactObject factObject = openFactUtilities.get(barcode);
		Product product = openFactUtilities.mapOpenFactObjectToProduct(factObject);
		productRepository.save(product);
		return openFactUtilities.CreateProductResultDTO(product);
	}
	
	public Product saveProduct(Product product) {
		Product obj = productRepository.save(product);
		product.getAdditives().forEach(x -> {
			Additive additive = x;
			additive.setProduct(obj);
			additiveRepository.save(additive);
		});
		return obj;
	}
	
	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}
	
	public Product getProduct(Long id) {
		return productRepository.findById(id).get();
	}
	
}
