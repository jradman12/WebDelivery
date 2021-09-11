package dto;

import beans.Comment;

public class CommentDTO {

	private Comment comment;
	private String restName;
	
	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public String getRestName() {
		return restName;
	}

	public void setRestName(String restName) {
		this.restName = restName;
	}

	public CommentDTO(Comment comment, String restName) {
		super();
		this.comment = comment;
		this.restName = restName;
	}
	
	
	
}
