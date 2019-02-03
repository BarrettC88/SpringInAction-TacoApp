package tacos.web.api;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  
  @PutMapping("/{id}")
   public void updateIngredient(@PathVariable String id, @RequestBody Ingredient ingredient) {
	  repo.save(ingredient);
  }
  
  @PostMapping
  public ResponseEntity<Ingredient> postIngredient(@RequestBody Ingredient ingredient){
	  Ingredient savedIngredient = repo.save(ingredient);
	  HttpHeaders headers = new HttpHeaders();
	  headers.setLocation(URI.create("http://localhost:8080/ingredients/" + ingredient.getId()));
	  return new ResponseEntity<>(savedIngredient, headers, HttpStatus.CREATED);
  }
  
  @DeleteMapping("/{id}")
  public void deleteIngredient(@PathVariable String id) {
	  repo.deleteById(id);
  }
  
}