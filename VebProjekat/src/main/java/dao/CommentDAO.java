package dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import enums.StatusOfComment;
import beans.Comment;

public class CommentDAO {
	
	public static Map<String, Comment> comments = new HashMap<>();


	
	
	public CommentDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	public CommentDAO(String contextPath) {
		loadComments(contextPath);
	}
		
	public Collection<Comment> findAll() {
		return comments.values();
	}
	
	public static void loadComments(String contextPath) {
		
			
				Gson gs = new Gson();
				String commentsJson = "";
				try {
					commentsJson = new String(Files.readAllBytes(Paths.get("C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\comments.json")));	
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Type type = new TypeToken<Map<String, Comment>>() {}.getType();
				
				comments.clear();
				
				comments = gs.fromJson(commentsJson, type);
			
	}
	
	
	
	public void saveCommentsJSON() {

		String path="C:\\Users\\mx\\Desktop\\WebDelivery\\VebProjekat\\src\\main\\java\\data\\comments.json";
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
		newComment.setRestaurant(comment.getRestaurant());
		newComment.setText(comment.getText());
		addComment(newComment);
		saveCommentsJSON();
	}
	
	
		
	
	public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("dd.MM.yyyy.").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	public static String generateNextId() {
		return Integer.toString(comments.size() + 1);
	}
	
	public static boolean changeStatus(StatusOfComment soc,String id) {
		loadComments("");
		for(Comment c : comments.values()) {
			if(c.getId().equals(id)) {
				c.setStatus(soc);
				return true;
			}
		}
		
		return false;
	}
	

}
