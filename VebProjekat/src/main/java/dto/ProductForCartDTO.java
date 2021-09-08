package dto;

import beans.Product;

public class ProductForCartDTO {

	private Product product;
	private int amount;
	
	public ProductForCartDTO() {
		// TODO Auto-generated constructor stub
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public ProductForCartDTO(Product product) {
		super();
		this.product = product;
		this.amount = 1;
	}

	@Override
	public String toString() {
		return "ProductForCartDTO [product=" + product + ", amount=" + amount + "]";
	}
	
	
}
