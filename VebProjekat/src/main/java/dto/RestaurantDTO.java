package dto;

import java.util.List;

import beans.Location;
import beans.Product;
import enums.RestaurantStatus;

public class RestaurantDTO {
	
	private String id;
	private String name;
	private String typeOfRestaurant;
	private List<Product> menu;
	private RestaurantStatus status;
	private Location location;
	private boolean isDeleted = false;
	private String logo;
	private double averageRating;
	private String managerID;
	
	public RestaurantDTO() {
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

	public String getTypeOfRestaurant() {
		return typeOfRestaurant;
	}

	public void setTypeOfRestaurant(String typeOfRestaurant) {
		this.typeOfRestaurant = typeOfRestaurant;
	}

	public List<Product> getMenu() {
		return menu;
	}

	public void setMenu(List<Product> menu) {
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

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	public RestaurantDTO(String id, String name, String typeOfRestaurant, List<Product> menu, RestaurantStatus status,
			Location location, boolean isDeleted, String logo, double averageRating, String managerID) {
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
		this.managerID = managerID;
	}
	
	 
	
}
