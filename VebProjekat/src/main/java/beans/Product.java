package beans;

import enums.ProductType;

public class Product {

	private String name;
	private double price;
	private ProductType type;
	private int quantity; // in g or mL
	private String description;
	private String restaurantID;
	private boolean isDeleted;
	private String logo;
	
	public Product() {
		// TODO Auto-generated constructor stub
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	public String getRestaurantID() {
		return restaurantID;
	}


	public void setRestaurantID(String restaurantID) {
		this.restaurantID = restaurantID;
	}


	public Product(String name, double price, ProductType type, int quantity, String description, String restaurantID,
			boolean isDeleted, String logo) {
		super();
		this.name = name;
		this.price = price;
		this.type = type;
		this.quantity = quantity;
		this.description = description;
		this.restaurantID = restaurantID;
		this.isDeleted = isDeleted;
		this.logo = logo;
	}


	
	
}
