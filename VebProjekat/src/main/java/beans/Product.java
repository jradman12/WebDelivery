package beans;

import enums.ProductType;

public class Product {

	//dodatno polje za id
	private String id;
	private String name;
	private double price;
	private ProductType type;
	private Restaurant restaurant;
	private int quantity; // in g or mL
	private String description;
	private boolean isDeleted;
	//slika artikla
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	

	public Product(String id, String name, double price, ProductType type, Restaurant restaurant, int quantity,
			String description, boolean isDeleted) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
		this.restaurant = restaurant;
		this.quantity = quantity;
		this.description = description;
		this.isDeleted = isDeleted;
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

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
}
