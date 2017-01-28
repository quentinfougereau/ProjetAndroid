package com.esgi.agnoscere.xmlparser;



public class Comment extends Post {

	public Comment(String id, String autor, String contents, String date) {
		super(id, autor, contents, date);
	}
	@Override
	public String toString() {
		// TODO Module de remplacement de m?thode auto-g?n?r?
		return "ID : "+id+" Autor : "+autor+" Contents : "+contents+" Date : "+date;
	}
	
}
