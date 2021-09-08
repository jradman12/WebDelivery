package beans;

import java.util.List;

public class Cart {

	private List<CartItem> items;
	private String customerID;
	private double price;
	
	public Cart() {
		
	}
	
	public Cart(List<CartItem> items, String customerID, double price) {
		super();
		this.items = items;
		this.customerID = customerID;
		this.price = price;
	}



	public String getCustomerID() {
		return customerID;
	}



	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}



	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
	
	
	
	
	
	
}
