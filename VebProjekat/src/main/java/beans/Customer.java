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
	
	
	public void setType() {
		this.type = new CustomerType("PLATINUM", 0, 0);
		if (this.points > 3000)
			this.type = new CustomerType("SILVER", 3, 3000);
		if (this.points > 5000)
			this.type = new CustomerType("GOLD", 5, 5000);
	}
	
	public void addPoints(int pointsToAdd) {
		this.points += pointsToAdd;		
		setType();	
	}
	
	public void removePoints(int pointsToRemove) {
		this.points -= pointsToRemove;
		if (this.points < 0)
			this.points = 0;
		
		setType();		
	}

}
