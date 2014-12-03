package com.cairoconfessions;

import java.util.List;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class ConversationListItem implements Parcelable {
	private String person;
	private String recentMessage;
	private ArrayList<ConversationItem> conversation;
	
	public ConversationListItem(String person, String recentMessage, ArrayList<ConversationItem> conversation) {
		super();
		this.person = person;
		this.recentMessage = recentMessage;
		this.conversation = conversation;
	}
	
	public ConversationListItem(Parcel in) {
		person = in.readString();
		recentMessage = in.readString();
		in.readList(conversation, ConversationListItem.class.getClassLoader());
	}
	
	public String getPerson() {
		return person;
	}
	public String getRecentMessage() {
		return recentMessage;
	}
	
	public ArrayList<ConversationItem> getConversation() {
		return conversation;
	}
	
	public static final Parcelable.Creator<ConversationListItem> CREATOR
    	= new Parcelable.Creator<ConversationListItem>() {
		public ConversationListItem createFromParcel(Parcel in) {
			return new ConversationListItem(in);
		}

		public ConversationListItem[] newArray(int size) {
			return new ConversationListItem[size];
		}
	};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(person);
		dest.writeString(recentMessage);
		dest.writeList(conversation);
	}
	
}
