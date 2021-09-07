package beans;

import enums.StatusOfComment;

public class Comment {

	private String id;
	private String author;
	private String restaurantID;
	private String text;
	private int rating;
	private StatusOfComment status;
	private boolean isDeleted;
	
	public Comment() {
	}
	

	public Comment(String id, String author, String restaurantID, String text, int rating, StatusOfComment status,
			boolean isDeleted) {
		super();
		this.id = id;
		this.author = author;
		this.restaurantID = restaurantID;
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

	public String getRestaurantID() {
		return restaurantID;
	}

	public void setRestaurantID(String restaurantID) {
		this.restaurantID = restaurantID;
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


	@Override
	public String toString() {
		return "Comment [id=" + id + ", author=" + author + ", restaurantID=" + restaurantID + ", text=" + text
				+ ", rating=" + rating + ", status=" + status + ", isDeleted=" + isDeleted + "]";
	}
	
	
	
}
