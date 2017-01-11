package main;

public abstract class Post {
	
	protected final String id;
	protected final String autor;
	protected String contents;
	protected final String date;
	
	
	public String getContent() {
		return contents;
	}
	public void setContent(String contents) {
		this.contents = contents;
	}
	public String getId() {
		return id;
	}
	public String getAutor() {
		return autor;
	}
	public String getDate() {
		return date;
	}
	public Post(String id, String autor,String contents,String date ) {
		this.id = id;
		this.autor = autor;
		this.contents = contents;
		this.date = date;
		
	}
	
	
}
