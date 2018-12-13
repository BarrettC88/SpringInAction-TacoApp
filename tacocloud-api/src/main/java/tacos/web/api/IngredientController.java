package tacos.web.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tacos.Ingredient;
import tacos.Taco;
import tacos.data.IngredientRepository;

@RestController
@RequestMapping(path="/ingredientsx", produces="application/json")
@CrossOrigin(origins="*")
public class IngredientController {

  private IngredientRepository repo;

  @Autowired
  public IngredientController(IngredientRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public Iterable<Ingredient> allIngredients() {
    return repo.findAll();
  }
  
  @GetMapping("/{id}")
	public ResponseEntity<Ingredient> tacoById(@PathVariable("id") String id) {
		Optional<Ingredient> optIngredient = repo.findById(id);
		if (optIngredient.isPresent()) {
			return new ResponseEntity<>(optIngredient.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
  
}