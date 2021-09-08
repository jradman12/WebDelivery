package beans;

public class CustomerType {

	private String typeName;
	private int discount;
	private int minPoints;
	
	public CustomerType() {
		
	}

	public CustomerType(String typeName, int discount, int minPoints) {
		super();
		this.typeName = typeName;
		this.discount = discount;
		this.minPoints = minPoints;
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

	public int getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(int points) {
		this.minPoints = points;
	}
	
	
	
	
}
