package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tacos.Ingredient;
import tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository{

	private JdbcTemplate jdbc;
	private SimpleJdbcInsert tacoInserter;
	private ObjectMapper objectMapper;
	
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
		tacoInserter = new SimpleJdbcInsert(jdbc).withTableName("TACO").usingGeneratedKeyColumns("id");
		this.objectMapper = new ObjectMapper();
	}
	
	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		for (Ingredient ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}
		
		return taco;
	}
	
	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		Map<String, Object> values = new HashMap<>();
		values.put("name", taco.getName());
		values.put("CREATEDAT", new Timestamp(taco.getCreatedAt().getTime()));
		long orderId = tacoInserter.executeAndReturnKey(values).longValue();
		return orderId;
	}
	
	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update("insert into Taco_Ingredients (taco, ingredient) " +
				"values (?,?)",
				tacoId, ingredient.getId());
				
	}
}
