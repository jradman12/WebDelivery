package beans;

public class CustomerType {

	private String typeName;
	private int discount;
	private int points;
	
	public CustomerType() {
		
	}

	public CustomerType(String typeName, int discount, int points) {
		super();
		this.typeName = typeName;
		this.discount = discount;
		this.points = points;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
	
	
}
