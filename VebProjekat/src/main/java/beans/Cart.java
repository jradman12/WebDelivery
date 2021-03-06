package beans;

import java.util.List;

public class Cart {

	private List<CartItem> items;
	private String customerID;
	private double price;
	private boolean isDeleted;
	
	public Cart() {
		
	}
	
	public Cart(List<CartItem> items, String customerID, double price) {
		super();
		this.items = items;
		this.customerID = customerID;
		this.price = price;
		this.isDeleted = false;
	}


	public boolean  ciAlreadyExists(String productName) {
		for(CartItem ci : items) {
			if(ci != null && ci.getProduct().getName().equals(productName))
				return true;
		}
		return false;
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
	
	public boolean isDeleted() {
		return this.isDeleted;
	}

	public void delete() {
		this.isDeleted = true;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "cart's customerID - " + this.customerID;
	}
	
	
	
	
}
