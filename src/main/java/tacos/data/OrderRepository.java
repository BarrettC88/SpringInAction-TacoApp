package tacos.data;

import tacos.Order;

public interface OrderRepository {
	
	public Order save(Order order);
}
