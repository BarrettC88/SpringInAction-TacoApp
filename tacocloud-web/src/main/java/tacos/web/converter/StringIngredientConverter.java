package tacos.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;

@Component
public class StringIngredientConverter implements Converter<String, Ingredient>{
	
	private IngredientRepository ingredRepo;
	
	public StringIngredientConverter(IngredientRepository ingredRepo) {
		this.ingredRepo = ingredRepo;
	}
	
	@Override
	public Ingredient convert(String id) {
		return ingredRepo.findById(id).get(); 
	}

}
