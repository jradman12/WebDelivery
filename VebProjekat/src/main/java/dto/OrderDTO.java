package dto;

import beans.Order;

public class OrderDTO {

	private Order order;
	private String restName;
	private String restType;
	
	public OrderDTO() {
		// TODO Auto-generated constructor stub
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getRestName() {
		return restName;
	}

	public void setRestName(String restName) {
		this.restName = restName;
	}

	public String getRestType() {
		return restType;
	}

	public void setRestType(String restType) {
		this.restType = restType;
	}

	public OrderDTO(Order order, String restName, String restType) {
		super();
		this.order = order;
		this.restName = restName;
		this.restType = restType;
	}

	@Override
	public String toString() {
		return "OrderDTO [order=" + order + ", restName=" + restName + ", restType=" + restType + "]";
	}
	
}
