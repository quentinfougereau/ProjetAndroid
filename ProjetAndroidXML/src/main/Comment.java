package main;

public class Comment extends Post {

	public Comment(String id, String autor, String contents, String date) {
		super(id, autor, contents, date);
	}
	@Override
	public String toString() {
		// TODO Module de remplacement de méthode auto-généré
		return "ID : "+id+" Autor : "+autor+" Contents : "+contents+" Date : "+date;
	}
	
}
