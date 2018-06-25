package tacos.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.data.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo) {
		this.ingredientRepo = ingredientRepo;
	}
	
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
		
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType (ingredients, type));
		}
		
		model.addAttribute("taco", new Taco());
		
		return "design";
	}
	
	public List<Ingredient> filterByType(List<Ingredient> ingredient, Type type){
		List<Ingredient> filteredList = new ArrayList<>();
		ingredient.forEach(
				(Ingredient a) -> {if (a.getType() == type) {filteredList.add(a);}});
		return filteredList;
		
	}
	
	@PostMapping
	public String processDesign(@Valid Taco tacol, Errors errors) {
		
		if (errors.hasErrors()) {
			return "design";
		}
		
		log.info("Process design: " + tacol);	
		return "redirect:/orders/current";
	}
	
	/*@PostMapping
	public String processDesign(Taco design) {
				
		log.info("Process design: " + design);	
		return "redirect:/orders/current";
	}*/

}
