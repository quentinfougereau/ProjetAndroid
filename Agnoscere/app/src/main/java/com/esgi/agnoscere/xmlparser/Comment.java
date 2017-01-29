package com.esgi.agnoscere.xmlparser;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Comment extends Post implements Serializable{

	public Comment(String id, String autor, String contents, String date) {
		super(id, autor, contents, date);
	}
	@Override
	public String toString() {
		// TODO Module de remplacement de m?thode auto-g?n?r?
		return "ID : "+id+" Autor : "+autor+" Contents : "+contents+" Date : "+date;
	}

	/*@Override
	public int describeContents() {
		return 0;
	}

	public Comment(Parcel in) {
		super(in);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(autor);
		dest.writeString(contents);
		dest.writeString(date);
	}

	public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
		@Override
		public Comment createFromParcel(Parcel in) {
			return new Comment(in);
		}

		@Override
		public Comment[] newArray(int size) {
			return new Comment[size];
		}
	};*/
}
