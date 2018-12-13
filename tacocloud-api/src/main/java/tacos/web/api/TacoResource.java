package tacos.web.api;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;
import tacos.Ingredient;
import tacos.Taco;

@Relation(value="taco", collectionRelation="tacos")
public class TacoResource extends ResourceSupport {
	
	private IngredientResourceAssembler 
	ingredientResourceAssembler = new IngredientResourceAssembler();
	
	@Getter
	private final String name;
	
	@Getter
	private final Date createdAt;
	
	@Getter
	private final List<IngredientResource> ingredients;
	
	public TacoResource(Taco taco) {
		this.name = taco.getName();
		this.createdAt = taco.getCreatedAt();
		this.ingredients = ingredientResourceAssembler.toResources(taco.getIngredients());
	}
	
}
