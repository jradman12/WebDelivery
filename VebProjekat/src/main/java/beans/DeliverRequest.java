package beans;

public class DeliverRequest {

	String managerID;
	String delivererID;
	String orderID;
	boolean isApproved;
	
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

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	@Override
	public String toString() {
		return "DeliverRequest [managerID=" + managerID + ", delivererID=" + delivererID + ", orderID=" + orderID
				+ ", isApproved=" + isApproved + "]";
	}

	public DeliverRequest(String managerID, String delivererID, String orderID, boolean isApproved) {
		super();
		this.managerID = managerID;
		this.delivererID = delivererID;
		this.orderID = orderID;
		this.isApproved = isApproved;
	}
	
	
}
