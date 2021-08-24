package beans;

import java.util.List;

public class Deliverer extends User{
	
	private List<Order> orders;
	
	

	public Deliverer() {
		super();
	}



	public Deliverer(List<Order> orders) {
		super();
		this.orders = orders;
	}



	public List<Order> getOrders() {
		return orders;
	}



	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	
	
	
	
	
}
