package beans;

import java.util.List;

import enums.RestaurantStatus;

public class Restaurant {

	private String name;
	private String typeOfRestaurant;
	private List<CartItem> menu;
	private RestaurantStatus status;
	private Location location;
	private boolean isDeleted;
	//logo restorana - slika
	
	
	public Restaurant() {
		
	}
	
	public Restaurant(String name, String typeOfRestaurant, List<CartItem> menu, RestaurantStatus status,
			Location location) {
		super();
		this.name = name;
		this.typeOfRestaurant = typeOfRestaurant;
		this.menu = menu;
		this.status = status;
		this.location = location;
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
	
}
