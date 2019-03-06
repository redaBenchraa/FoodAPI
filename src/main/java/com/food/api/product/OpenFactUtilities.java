package com.food.api.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.food.api.openFact.Nutriments;
import com.food.api.openFact.OpenFactObject;
import com.food.api.rules.Rule;
import com.food.api.rules.RuleService;
@Service
public class OpenFactUtilities {

    private static final String  PATH = "https://fr.openfoodfacts.org/api/v0/produit/";
	public static double[] energyMetric = {335.0, 370.0, 1005.0, 1340.0, 1675.0, 2010.0, 2345.0, 2680.0, 3350.0}; 
	public static double[] saturatedFatMetric = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
	public static double[] sugarMetric = {4.5, 9.0, 13.5, 18.0, 22.5, 27.0, 31.0, 36.0, 40.0, 45.0};
	public static double[] soduimMetric = {90, 180, 270, 360, 450, 540, 630, 720, 810, 900};
	public static double[] fiberMetric = {0.9, 1.9, 2.8, 3.7, 4.7};
	public static double[] proteinMetric = {1.6, 3.2, 4.8, 6.4, 8.0};
	public static List<Rule> rules;
	
	@Autowired
	public OpenFactUtilities(RuleService ruleService) {
		rules = ruleService.GetAllRules();
		energyMetric = rules.stream().filter(x -> x.getName().equals("energy_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		saturatedFatMetric = rules.stream().filter(x -> x.getName().equals("saturated-fat_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		sugarMetric = rules.stream().filter(x -> x.getName().equals("sugars_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		soduimMetric = rules.stream().filter(x -> x.getName().equals("salt_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		fiberMetric = rules.stream().filter(x -> x.getName().equals("fiber_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		proteinMetric = rules.stream().filter(x -> x.getName().equals("proteins_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
	}
	
	public Product mapOpenFactObjectToProduct(OpenFactObject object) {
		if(object == null || object.getProduct() == null || object.getProduct().getNutriments() == null) 
			return null; 
		Nutriments nutriments = object.getProduct().getNutriments();
		
		Product product = new Product();
		
		product.setEnergy(getPoint(Float.parseFloat(nutriments.getEnergy_100g()), energyMetric));
		product.setSaturated_fat(getPoint(nutriments.getSaturated_fat_100g(), saturatedFatMetric));
		product.setSalt(getPoint(nutriments.getSalt_100g(), soduimMetric));
		product.setFibers(getPoint(nutriments.getFiber_100g(), fiberMetric));
		product.setProteins(getPoint(nutriments.getProteins_100g(), proteinMetric));
		product.setSugar(getPoint(nutriments.getSugars_100g(), sugarMetric));
		product.setName(object.getProduct().getProduct_name());
		product.setCodebar(object.getProduct().getCode());
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
		return object;
	}
	
	public static String buildPath(String barcode) {
		return barcode.contains(".json") ? PATH + barcode : PATH + barcode + ".json";
	}
}
