package beans;

import enums.RequestStatus;

public class DeliverRequest {

	private String id;
	private String managerID;
	private String delivererID;
	private String orderID;
	private String restaurantID;
	private RequestStatus status;
	
	public DeliverRequest() {
		// TODO Auto-generated constructor stub
	}

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	public String getDelivererID() {
		return delivererID;
	}

	public void setDelivererID(String delivererID) {
		this.delivererID = delivererID;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DeliverRequest [managerID=" + managerID + ", delivererID=" + delivererID + ", orderID=" + orderID
				+ ", status=" + status + ", restaurantID=" + restaurantID+"]";
	}

	public DeliverRequest(String managerID, String delivererID, String orderID, RequestStatus status,String restaurantID,String id) {
		super();
		this.managerID = managerID;
		this.delivererID = delivererID;
		this.orderID = orderID;
		this.status = status;
		this.restaurantID=restaurantID;
		this.id=id;
	}

	public String getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(String restaurantID) {
		this.restaurantID = restaurantID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
	
	
	
}
