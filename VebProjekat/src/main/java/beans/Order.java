package beans;

import java.util.Date;
import java.util.List;

import enums.OrderStatus;

public class Order {
	
	private String id;
	private List<CartItem> orderedItems;
	private Restaurant restaurant;
	private Date dateAndTime;
	private double price;
	private Customer customer;
	private OrderStatus status;
	private boolean isDeleted;
	
	public Order() {
		
	}

	public Order(String id, List<CartItem> orderedItems, Restaurant restaurant, Date dateAndTime, double price,
			Customer customer, OrderStatus status) {
		super();
		this.id = id;
		this.orderedItems = orderedItems;
		this.restaurant = restaurant;
		this.dateAndTime = dateAndTime;
		this.price = price;
		this.customer = customer;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<CartItem> getOrderedItems() {
		return orderedItems;
	}

	public void setOrderedItems(List<CartItem> orderedItems) {
		this.orderedItems = orderedItems;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
