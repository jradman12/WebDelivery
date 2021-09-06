package beans;

public class Manager extends User {

	private String restaurantID;
	
	

	public Manager() {
		super();
	}



	public String getRestaurantID() {
		return restaurantID;
	}



	public void setRestaurantID(String restaurantID) {
		this.restaurantID = restaurantID;
	}



	public Manager(String restaurantID) {
		super();
		this.restaurantID = restaurantID;
	}
	 
	
	
}
