package beans;

import enums.StatusOfComment;

public class Comment {

	private String id;
	private String author;
	private String restaurant;
	private String text;
	private int rating;
	private StatusOfComment status;
	private boolean isDeleted;
	
	public Comment() {
	}
	

	public Comment(String id, String author, String restaurant, String text, int rating, StatusOfComment status,
			boolean isDeleted) {
		super();
		this.id = id;
		this.author = author;
		this.restaurant = restaurant;
		this.text = text;
		this.rating = rating;
		this.status = status;
		this.isDeleted = isDeleted;
	}


	public StatusOfComment getStatus() {
		return status;
	}

	public void setStatus(StatusOfComment status) {
		this.status = status;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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
	
	
	
}
