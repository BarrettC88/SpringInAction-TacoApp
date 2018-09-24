package tacos.web.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import tacos.Order;
import tacos.Taco;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;

@RestController("B")
@RequestMapping(path="/design", produces="application/json")
@CrossOrigin(origins="*")
@Service()
public class DesignTacoController{
	private TacoRepository tacoRepo;
	private OrderRepository orderRepo;
	
	/*@Autowired
	EntityLinks entityLinks;*/
	
	public DesignTacoController(TacoRepository tacoRepo, OrderRepository orderRepo) {
		this.tacoRepo = tacoRepo;
		this.orderRepo = orderRepo;
	}
	
	@GetMapping("/recent")
	public Resources<Resource<Taco>> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		List<Taco> tacos = tacoRepo.findAll(page).getContent();
		Resources<Resource<Taco>> recentResources = Resources.wrap(tacos);
		
		//The below is a bad ideas as the link is hardcoded with localhost
		//recentResources.add(new Link("http://localhost:8080/design/recent", "recents"));
		recentResources.add(ControllerLinkBuilder.linkTo(DesignTacoController.class).slash("recent").withRel("recents"));
		//Another way we could achieve the above is the following:
		//recentResources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(DesignTacoController.class).recentTacos()).withRel("recents"));
		return recentResources;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepo.findById(id);
		if (optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path="/make/", consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Taco postTaco(@RequestBody Taco taco) {
		return tacoRepo.save(taco);
	}
	
	@DeleteMapping("/{orderId}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteOrder(@PathVariable("orderId") Long orderId) {
	try {
	tacoRepo.deleteById(orderId);
	} catch (EmptyResultDataAccessException e) {}
	}
	
	@PutMapping("/{orderId}")
	public Order putOrder(@RequestBody Order order) {
	return orderRepo.save(order);
	}
	
	@PatchMapping(path="/{orderId}", consumes="application/json")
	public Order patchOrder(@PathVariable("orderId") Long orderId,
	@RequestBody Order patch) {
	Order order = orderRepo.findById(orderId).get();
	if (patch.getCustomerName() != null) {
	order.setCustomerName(patch.getCustomerName());
	}
	if (patch.getStreet() != null) {
	order.setStreet(patch.getStreet());
	}
	if (patch.getCity() != null) {
	order.setCity(patch.getCity());
	}
	if (patch.getState() != null) {
	order.setState(patch.getState());
	}
	if (patch.getZip() != null) {
	order.setZip(patch.getState());
	}
	if (patch.getCcNumber() != null) {
	order.setCcNumber(patch.getCcNumber());
	}
	if (patch.getCcExpiration() != null) {
	order.setCcExpiration(patch.getCcExpiration());
	}
	if (patch.getCcCVV() != null) {
	order.setCcCVV(patch.getCcCVV());
	}
	return orderRepo.save(order);
	}

}
