package beans;

import enums.CartItemType;

public class CartItem {

	private String name;
	private double price;
	private CartItemType type;
	private Restaurant restaurant;
	private int quantity;
	private String description;
	//slika artikla
	private int amount;
	
	
	public CartItem() {
		
	}

	public CartItem(String name, double price, CartItemType type, Restaurant restaurant, int quantity,
			String description, int amount) {
		super();
		this.name = name;
		this.price = price;
		this.type = type;
		this.restaurant = restaurant;
		this.quantity = quantity;
		this.description = description;
		this.amount = amount;
	}











	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public CartItemType getType() {
		return type;
	}

	public void setType(CartItemType type) {
		this.type = type;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	
	
	
	
	
}
