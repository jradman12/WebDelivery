package beans;


public class CartItem {

	private Product product;
	private int amount;
	private boolean isDeleted; // delete if not needed
	
	public CartItem() {		
	}

	public CartItem(Product product, int amount, boolean isDeleted) {
		super();
		this.product = product;
		this.amount = amount;
		this.isDeleted = isDeleted;
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

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	
}
