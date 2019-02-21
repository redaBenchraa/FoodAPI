package com.food.api.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.food.api.Application;
import com.food.api.openFact.Nutriments;
import com.food.api.openFact.OpenFactObject;
@Service
public class OpenFactUtilities {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static final String  PATH = "https://fr.openfoodfacts.org/api/v0/produit/";
	public static final double[] energyMetric = {335.0, 370.0, 1005.0, 1340.0, 1675.0, 2010.0, 2345.0, 2680.0, 3350.0}; 
	public static final double[] saturatedFatMetric = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
	public static final double[] sugarMetric = {4.5, 9.0, 13.5, 18.0, 22.5, 27.0, 31.0, 36.0, 40.0, 45.0};
	public static final double[] soduimMetric = {90, 180, 270, 360, 450, 540, 630, 720, 810, 900};
	public static final double[] fiberMetric = {0.9, 1.9, 2.8, 3.7, 4.7};
	public static final double[] proteinMetric = {1.6, 3.2, 4.8, 6.4, 8.0};
	
	
	public Product mapOpenFactObjectToProduct(OpenFactObject object) {
		if(object == null || object.getProduct() == null || object.getProduct().getNutriments() == null) 
			return null; 
		Nutriments nutriments = object.getProduct().getNutriments();
		
		Product product = new Product();
		Float energy_100 = Float.parseFloat(nutriments.getEnergy_100g());
		Float saturated_fat_100 = nutriments.getSaturated_fat_100g();
		Float sugar_100g = nutriments.getSugars_100g();
		Float salt_100g = nutriments.getSalt_100g();
		Float fiber_100g = nutriments.getFiber_100g();
		Float proteins_100 = nutriments.getProteins_100g();
		
		product.setEnergy(getPoint(energy_100, energyMetric));
		product.setSaturated_fat(getPoint(saturated_fat_100, saturatedFatMetric));
		product.setSalt(getPoint(salt_100g, soduimMetric));
		product.setFibers(getPoint(fiber_100g, fiberMetric));
		product.setProteins(getPoint(proteins_100, proteinMetric));
		product.setSugar(getPoint(sugar_100g, sugarMetric));
		
		return product;
	}
	

	
	public static int getPoint(double value,double[] array) {
		int i = 0;
		for(i=0; i < array.length; i++) {
			if(value <= array[i]) return i;
		}
		return i;
	}
	public OpenFactObject get(String barcode) {
		RestTemplate restTemplate = new RestTemplate();
        OpenFactObject object = restTemplate.getForObject(buildPath(barcode), OpenFactObject.class);
        log.info(object.toString());
		return object;
	}
	
	public static String buildPath(String barcode) {
		return barcode.contains(".json") ? PATH + barcode : PATH + barcode + ".json";
	}
}
