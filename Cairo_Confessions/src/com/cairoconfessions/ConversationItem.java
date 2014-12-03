package com.cairoconfessions;

import android.os.Parcel;
import android.os.Parcelable;

public class ConversationItem implements Parcelable {
	private String message;
	private String timeStamp;
	private int isMyMessage;
	
	public ConversationItem(String message, String timeStamp, int isMyMessage) {
		super();
		this.message = message;
		this.timeStamp = timeStamp;
		this.isMyMessage = isMyMessage;
	}
	
	public ConversationItem(Parcel in) {
		message = in.readString();
		timeStamp = in.readString();
		isMyMessage = in.readInt();
	}

	public String getMessage() {
		return message;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}

	public int isMyMessage() {
		return isMyMessage;
	}
	
	public static final Parcelable.Creator<ConversationItem> CREATOR
		= new Parcelable.Creator<ConversationItem>() {
		public ConversationItem createFromParcel(Parcel in) {
			return new ConversationItem(in);
		}

		public ConversationItem[] newArray(int size) {
			return new ConversationItem[size];
		}
	};	
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(message);
		dest.writeString(timeStamp);
		dest.writeInt(isMyMessage);
	}

}
