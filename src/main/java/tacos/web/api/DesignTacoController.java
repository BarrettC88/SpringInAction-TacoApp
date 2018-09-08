package tacos.web.api;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import tacos.data.TacoRepository;

@RestController("B")
@RequestMapping(path="/design", produces="application/json")
@CrossOrigin(origins="*")
@Service()
public class DesignTacoController{
	private TacoRepository tacoRepo;
	
	/*@Autowired
	EntityLinks entityLinks;*/
	
	public DesignTacoController(TacoRepository tacoRepo) {
		this.tacoRepo = tacoRepo;
	}
	
	@GetMapping("/recent")
	public Iterable<Taco> recentTacos() {
		PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
		return tacoRepo.findAll(page);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepo.findById(id);
		if (optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(consumes="application/jason")
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
	
	/*@PutMapping("/{orderId}")
	public Order putOrder(@RequestBody Order order) {
	return tacoRepo.save(order);
	}*/
	
	/*@PatchMapping(path="/{orderId}", consumes="application/json")
	public Order patchOrder(@PathVariable("orderId") Long orderId,
	@RequestBody Order patch) {
	Order order = repo.findById(orderId).get();
	if (patch.getCustomerName() != null) {
	order.setDeliveryName(patch.getCustomerName());
	}
	if (patch.getStreet() != null) {
	order.setDeliveryStreet(patch.getStreet());
	}
	if (patch.getCity() != null) {
	order.setDeliveryCity(patch.getCity());
	}
	if (patch.getState() != null) {
	order.setDeliveryState(patch.getState());
	}
	if (patch.getZip() != null) {
	order.setDeliveryZip(patch.getState());
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
	return repo.save(order);
	}*/

}
