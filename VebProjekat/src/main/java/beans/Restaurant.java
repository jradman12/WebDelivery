package beans;

import java.util.List;

import enums.RestaurantStatus;

public class Restaurant {
	private String id;
	private String name;
	private String typeOfRestaurant;
	private List<CartItem> menu;
	private RestaurantStatus status;
	private Location location;
	private boolean isDeleted;
	private String logo;
	private double averageRating;
	
	
	public Restaurant() {
		
	}
	
	
	public Restaurant(String id, String name, String typeOfRestaurant, List<CartItem> menu, RestaurantStatus status,
			Location location, boolean isDeleted, String logo, double averageRating) {
		super();
		this.id = id;
		this.name = name;
		this.typeOfRestaurant = typeOfRestaurant;
		this.menu = menu;
		this.status = status;
		this.location = location;
		this.isDeleted = isDeleted;
		this.logo = logo;
		this.averageRating = averageRating;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeOfRestaurant() {
		return typeOfRestaurant;
	}

	public void setTypeOfRestaurant(String typeOfRestaurant) {
		this.typeOfRestaurant = typeOfRestaurant;
	}

	public List<CartItem> getMenu() {
		return menu;
	}

	public void setMenu(List<CartItem> menu) {
		this.menu = menu;
	}

	public RestaurantStatus getStatus() {
		return status;
	}

	public void setStatus(RestaurantStatus status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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


	public String getLogo() {
		return logo;
	}


	public void setLogo(String logo) {
		this.logo = logo;
	}


	public double getAverageRating() {
		return averageRating;
	}


	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
	
	
	
	
	
}
