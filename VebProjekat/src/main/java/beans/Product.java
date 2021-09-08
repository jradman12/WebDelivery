package beans;

import enums.ProductType;

public class Product {

	private String id;
	private String name;
	private double price;
	private ProductType type;
	private int quantity; // in g or mL
	private String description;
	private boolean isDeleted;
	private String logo;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" ;
	}

	public Product(String id, String name, double price, ProductType type, int quantity, String description,
			boolean isDeleted, String logo) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.type = type;
		this.quantity = quantity;
		this.description = description;
		this.isDeleted = isDeleted;
		this.logo = logo;
	}

	
	
}
