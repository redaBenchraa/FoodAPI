package com.food.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	
	@Autowired
	ProductService productService;

	@RequestMapping(method=RequestMethod.GET,value="/products/{codebar}")
	public Product getProductByCodebar(@PathVariable String codebar) {
		return productService.getProductByCodebar(codebar);
	}
	
}
