package beans;

public class Comment {

	private String id;
	private Customer author;
	private Restaurant restaurant;
	private String text;
	private int rating;
	private boolean isApproved;
	private boolean isDeleted;
	
	public Comment() {
	}
	
	


	public Comment(String id, Customer author, Restaurant restaurant, String text, int rating, boolean isApproved,
			boolean isDeleted) {
		super();
		this.id = id;
		this.author = author;
		this.restaurant = restaurant;
		this.text = text;
		this.rating = rating;
		this.isApproved = isApproved;
		this.isDeleted = isDeleted;
	}




	public Customer getAuthor() {
		return author;
	}

	public void setAuthor(Customer author) {
		this.author = author;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
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

	
	public boolean isApproved() {
		return isApproved;
	}


	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
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
