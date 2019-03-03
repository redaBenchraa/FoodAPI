package com.food.api.basket;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.product.Product;
import com.food.api.product.ProductResultDTO;
import com.food.api.product.ProductService;
import com.food.api.user.User;

@RestController
public class BasketController {

	
	@Autowired
	BasketService basketService;
	
	@Autowired
	ProductService productService;

	@RequestMapping(method=RequestMethod.GET,value="/baskets/{id}")
	public Basket getBasket(@PathVariable Long id){
		return basketService.getBasket(id);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/baskets/{id}/synthese")
	public List<ProductResultDTO> getBasketSynthese(@PathVariable Long id){
		return basketService
				.getBasket(id)
				.getProducts()
				.stream().map(x -> new ProductResultDTO(x))
				.collect(Collectors.toList());
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/baskets/{id}/products/{barcode}")
	public Product addProduct(@PathVariable Long id, @PathVariable String barcode){
		Basket basket = basketService.getBasket(id);
		Product product = productService.getProductByCodebar(barcode);
		product.setBasket(basket);
		return productService.saveProduct(product);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/baskets/{id}/products/{productId}")
	public void deleteProduct(@PathVariable Long id, @PathVariable Long productId){
		Basket basket = basketService.getBasket(id);
		Product product = productService.getProduct(productId);
		productService.deleteProduct(product);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/baskets/{id}/products/{productId}")
	public ProductResultDTO getProductSynthese(@PathVariable Long id, @PathVariable Long productId){
		Product product = productService.getProduct(productId);
		return new ProductResultDTO(product);
	}

}
