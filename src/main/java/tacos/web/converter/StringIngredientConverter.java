package tacos.web.converter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.Ingredient;
import tacos.data.IngredientRepository;
import tacos.data.JdbcIngredientRepository;

@Component
public class StringIngredientConverter implements Converter<String, Ingredient>{
	
	private IngredientRepository ingredRepo;
	
	public StringIngredientConverter(IngredientRepository ingredRepo) {
		this.ingredRepo = ingredRepo;
	}
	
	@Override
	public Ingredient convert(String id) {
		// TODO Auto-generated method stub
		return ingredRepo.findOne(id); 
	}

}
