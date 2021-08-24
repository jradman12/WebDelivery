package beans;

import java.util.List;

public class Cart {

	private List<CartItem> items;
	private Customer customer;
	private double price;
	
	public Cart() {
		
	}

	public Cart(List<CartItem> items, Customer customer, double price) {
		super();
		this.items = items;
		this.customer = customer;
		this.price = price;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
	
	
	
	
	
	
}
