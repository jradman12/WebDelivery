package beans;

import java.util.List;

public class Customer extends User {
	
	private List<Order> orders;
	private Cart cart;
	private int points;
	private CustomerType type;
	
	public Customer() {
		super();
	}

	public Customer(List<Order> orders, Cart cart, int points, CustomerType type) {
		super();
		this.orders = orders;
		this.cart = cart;
		this.points = points;
		this.type = type;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}
	
	
	

}
