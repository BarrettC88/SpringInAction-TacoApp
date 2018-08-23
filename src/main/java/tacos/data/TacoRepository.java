package tacos.data;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import tacos.Taco;

public interface TacoRepository extends CrudRepository<Taco, Long>{
	
	/*Taco save(Taco taco);*/
	
	public Iterable<Taco> findAll(Pageable pageable);
	
}