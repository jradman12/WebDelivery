package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.Comment;
import enums.StatusOfComment;

public class CommentDAO {
	
	public  Map<String, Comment> comments = new HashMap<>();
	public String path = "C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\comments.json";


	public CommentDAO() {
	}
	
	public CommentDAO(String contextPath) {
		loadComments(contextPath);
	}
		
	public Collection<Comment> findAll() {
		return comments.values();
	}
	
	public Collection<Comment> getAllAvailable(){
		Collection<Comment> availableUsers = new ArrayList<Comment>();
		for(Comment u : comments.values()) {
			if(!u.isDeleted()) 
				availableUsers.add(u);
		}
		return availableUsers;
	}
	
	public void deleteUser(String userID) {
		comments.get(userID).setDeleted(true);
	}
	
	public  void loadComments(String contextPath) {
		
			
				Gson gs = new Gson();
				String commentsJson = "";
				try {
					commentsJson = new String(Files.readAllBytes(Paths.get(path)));	
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Comment>>() {}.getType();
				
				comments.clear();
				
				comments = gs.fromJson(commentsJson, type);
			
	}
	
	
	
	public  void saveCommentsJSON() {

		Map<String, Comment> allComments = new HashMap<>();
		for (Comment c : findAll()) {
			allComments.put(c.getId(),c);
		}
		Gson gs = new Gson();
		String json = gs.toJson(allComments);
		byte[] inBytes = json.getBytes();
		
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(path);
		}catch (FileNotFoundException e) {
			// TODO: handle exception
			System.out.println("Check the path u gave me!!");
		}
		try {
			fos.write(inBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void addComment(Comment comment) {
		if (!comments.containsValue(comment)) {
			comments.put(comment.getId(), comment);
		}
		
	}
	
	public void addNewComment(Comment comment) {
		Comment newComment = new Comment();
		newComment.setDeleted(false);
		newComment.setStatus(StatusOfComment.WAITING);
		newComment.setAuthor(comment.getAuthor());
		newComment.setId(generateNextId());
		newComment.setRating(comment.getRating());
		newComment.setRestaurantID(comment.getRestaurantID());
		newComment.setText(comment.getText());
		addComment(newComment);
		saveCommentsJSON();
	}
	
	
	public  Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public String generateNextId() {
		return Integer.toString(comments.size() + 1);
	}
	
	public  boolean changeStatus(StatusOfComment soc,String id) {
		for(Comment c : comments.values()) {
			if(c.getId().equals(id)) {
				c.setStatus(soc);
				saveCommentsJSON();
				return true;
			}
		}
		
		return false;
	}
	
	
	public  Collection<Comment> getCommentsForRestaurant(String id){
		List<Comment> commentsForRestaurant = new ArrayList<Comment>();
		for(Comment c : getAllAvailable()) {
			if(c.getRestaurantID().equals(id)) {
				commentsForRestaurant.add(c);
				
			}
		}
		
		return commentsForRestaurant;
		
	}
	
	private int numberOfCommentsForRestaurant(String id) {
		int counter=0;
		for(Comment c : comments.values()) {
			if(c.getRestaurantID().equals(id) && c.getStatus().equals(StatusOfComment.APPROVED)) {
				counter+=1;
			}
			
		}
		
		System.out.println(" Cetvrta - Sum je " + counter);
		return counter;
	}
	
	private int sumOfRatingsForRestaurant(String id) {
		
		int sum=0;
		for(Comment c : comments.values()) {
			if(c.getRestaurantID().equals(id) && c.getStatus().equals(StatusOfComment.APPROVED)) {
				sum+=c.getRating();
			}
		}
		
		System.out.println(" TRECA - Sum je " + sum);
		return sum;
	}
	
	public double averageRatingForRestaurant(String id) {
		System.out.println("DRUGA");
		return roundAvoid(((double)sumOfRatingsForRestaurant(id))/numberOfCommentsForRestaurant(id),2);
	}
	
	
	public Comment findOne(String id) {
		return comments.get(id);
	}
	
	public  double roundAvoid(double value, int places) {
	    double scale = Math.pow(10, places);
	    return Math.round(value * scale) / scale;
	}
	

}
