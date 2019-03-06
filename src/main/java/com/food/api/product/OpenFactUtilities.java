package com.food.api.product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.food.api.openFact.Nutriments;
import com.food.api.openFact.OpenFactObject;
import com.food.api.rules.NutritionScore;
import com.food.api.rules.NutritionScoreService;
import com.food.api.rules.Rule;
import com.food.api.rules.RuleService;
@Service
public class OpenFactUtilities {

    private static final String  PATH = "https://fr.openfoodfacts.org/api/v0/produit/";
    private static final String  ADDITIVE_FILE_NAME = "additifs.csv";
	public static double[] energyMetric = {335.0, 370.0, 1005.0, 1340.0, 1675.0, 2010.0, 2345.0, 2680.0, 3350.0}; 
	public static double[] saturatedFatMetric = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
	public static double[] sugarMetric = {4.5, 9.0, 13.5, 18.0, 22.5, 27.0, 31.0, 36.0, 40.0, 45.0};
	public static double[] soduimMetric = {90, 180, 270, 360, 450, 540, 630, 720, 810, 900};
	public static double[] fiberMetric = {0.9, 1.9, 2.8, 3.7, 4.7};
	public static double[] proteinMetric = {1.6, 3.2, 4.8, 6.4, 8.0};
	public static List<Rule> rules;
	public static List<Additive> additives;
	NutritionScoreService nutritionScoreService;
	@Autowired
	public OpenFactUtilities(RuleService ruleService, NutritionScoreService nutritionScoreService) {
		this.nutritionScoreService = nutritionScoreService;
		rules = ruleService.GetAllRules();
		energyMetric = rules.stream().filter(x -> x.getName().equals("energy_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		saturatedFatMetric = rules.stream().filter(x -> x.getName().equals("saturated-fat_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		sugarMetric = rules.stream().filter(x -> x.getName().equals("sugars_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		soduimMetric = rules.stream().filter(x -> x.getName().equals("salt_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		fiberMetric = rules.stream().filter(x -> x.getName().equals("fiber_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		proteinMetric = rules.stream().filter(x -> x.getName().equals("proteins_100g")).mapToDouble(x -> x.getMin_bound()).toArray();
		additives = loadAdditives();
		
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
		product.setAdditives(new ArrayList<>());
		object.getProduct().getAdditives_tags().forEach(x -> {
			if(!x.isEmpty() && x.split(":").length >= 2) {
				String code =  x.split(":")[1];
				Additive additive = additives.stream().filter(z -> z.getCode().toLowerCase().equals(code.toLowerCase())).findFirst().orElse(null);
				if(additive != null) {
					product.getAdditives().add(additive);
				}
			}
		});
		return product;
	}
	
	public ProductResultDTO CreateProductResultDTO(Product product) {
		if(product == null)
			return null;
		
		ProductResultDTO productResultDTO = new ProductResultDTO();
		List<NutritionScore> nutritionScores = nutritionScoreService.GetAllNutritionScores();
		productResultDTO.setBarcode(product.getCodebar());
		productResultDTO.setProductName(product.getName());
		productResultDTO.setQualities(new ArrayList<>());
		productResultDTO.setFlaws(new ArrayList<>());
		productResultDTO.setAdditives(new ArrayList<>());
		productResultDTO.setScore(product.getEnergy() + product.getSaturated_fat() + product.getSugar() + product.getSalt() 
		- (product.getFibers() + product.getProteins()));
		
		nutritionScores.forEach(x -> {
			if(productResultDTO.getScore() >= x.getLower_bound() && productResultDTO.getScore() <= x.getUpper_bound()) {
				productResultDTO.setStatus(x.getClasse());
				productResultDTO.setColor(x.getColor());
			}
		});
		
		
		if(product.getEnergy() <= 3) {
			productResultDTO.getQualities().add("Energy");
		}
		
		if(product.getEnergy() >= 7) {
			productResultDTO.getFlaws().add("Enegery");
		}
		
		if(product.getSaturated_fat() <= 3) {
			productResultDTO.getQualities().add("Saturated fats");
		}
		
		if(product.getSaturated_fat() >= 7) {
			productResultDTO.getFlaws().add("Saturated fats");
		}
		
		if(product.getSugar() <= 3) {
			productResultDTO.getQualities().add("Sugar");
		}
		
		if(product.getSugar() >= 7) {
			productResultDTO.getFlaws().add("Sugar");
		}
		
		if(product.getSalt() <= 3) {
			productResultDTO.getQualities().add("Salt");
		}
		
		if(product.getSalt() >= 7) {
			productResultDTO.getFlaws().add("Salt");
		}
		
		if(product.getFibers() >= 2) {
			productResultDTO.getQualities().add("Fibers");
		}
		
		if(product.getFibers() <= 0) {
			productResultDTO.getFlaws().add("Fibers");
		}
		
		if(product.getProteins() >= 2) {
			productResultDTO.getQualities().add("Proteins");
		}
		
		if(product.getProteins() <= 0) {
			productResultDTO.getFlaws().add("Proteins");
		}
		
		product.getAdditives().forEach(x -> {
			String message = x.getCode() + "(" + x.getName() + ") : " + x.getAdvice();
			productResultDTO.getAdditives().add(message);
		});
		return productResultDTO;
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
	
	public List<Additive> loadAdditives() {
		try {
			File file = new ClassPathResource(ADDITIVE_FILE_NAME).getFile();
			BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;
		    List<Additive> additives = new ArrayList<Additive>();
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(";");
		        Additive additive = new Additive();
		        additive.setCode(values[0]);
		        additive.setName(values.length > 1 ? values[1] : "");
		        additive.setAdvice(values.length > 2 ? values[2] : "");
		        additives.add(additive);
		    }
		    br.close();
		    return additives;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	public static String buildPath(String barcode) {
		return barcode.contains(".json") ? PATH + barcode : PATH + barcode + ".json";
	}
}
