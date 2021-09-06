package dto;

import beans.Product;

public class ProductDTO {

	public Product product;
	public String restaurantID;
	
	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(String restaurantID) {
		this.restaurantID = restaurantID;
	}

	public ProductDTO(Product product, String restaurantID) {
		super();
		this.product = product;
		this.restaurantID = restaurantID;
	}
	
	
}
