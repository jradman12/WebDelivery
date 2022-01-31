	package beans;

import java.util.Date;
import java.util.List;

import enums.OrderStatus;

public class Order {
	
	private String id;
	private List<CartItem> orderedItems;
	private String restaurant;
	private Date dateAndTime;
	private double price;
	private String customerID;
	private OrderStatus status = OrderStatus.PENDING;
	private boolean isDeleted;
	
	public Order() {
		
	}

	public Order(String id, List<CartItem> orderedItems, String restaurant, Date dateAndTime, double price,
			String customer, OrderStatus status) {
		super();
		this.id = id;
		this.orderedItems = orderedItems;
		this.restaurant = restaurant;
		this.dateAndTime = dateAndTime;
		this.price = price;
		this.customerID = customer;
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

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
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

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customer) {
		this.customerID = customer;
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

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerID=" + customerID + "]";
	}

	
}
