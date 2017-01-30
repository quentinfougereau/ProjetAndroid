package com.esgi.agnoscere.xmlparser;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public abstract class Post implements Serializable{
	
	protected final String id;
	protected final String autor;
	protected String contents;
	protected final String date;

    public Post(String id, String autor,String contents,String date ) {
        this.id = id;
        this.autor = autor;
        this.contents = contents;
        this.date = date;

    }

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

   /* @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(autor);
        dest.writeString(contents);
        dest.writeString(date);
    }

    protected Post(Parcel in) {
        id = in.readString();
        autor = in.readString();
        contents = in.readString();
        date = in.readString();
    }
*/

}
