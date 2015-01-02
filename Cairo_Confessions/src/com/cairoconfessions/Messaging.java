package com.cairoconfessions;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Messaging extends Activity {
	
	private static List<ConversationListItem> conversationList = new ArrayList<ConversationListItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messaging);
		
		createConversationList();
		createListView();
	}
	
	private void createConversationList() {
		if (conversationList.size() == 0) {
			ArrayList<ConversationItem> convo1 = new ArrayList<ConversationItem>();
			convo1.add(new ConversationItem("I was in a very similar situation a year ago and I want to let you know that everything will be alright as long as you don't give up", "9:45 AM", 1));
			convo1.add(new ConversationItem("What can I do to keep going? Just sitting here being unable to think about it is making it worse.", "9:47 AM", 0));
			convo1.add(new ConversationItem("Try and create something, express yourself.", "9:48 AM", 1));
			convo1.add(new ConversationItem("Thank you", "9:48 AM", 0));
			convo1.add(new ConversationItem("If you have anything you want to discuss just let me know. I'm happy to help you get through this.", "9:48 AM", 1));
			convo1.add(new ConversationItem("test", "9:48 AM", 1));
			convo1.add(new ConversationItem("test2", "9:48 AM", 1));
			convo1.add(new ConversationItem("test3", "9:48 AM", 0));
			convo1.add(new ConversationItem("test4", "9:48 AM", 0));
			convo1.add(new ConversationItem("test5", "9:48 AM", 1));
			
			ArrayList<ConversationItem> convo2 = new ArrayList<ConversationItem>();
			convo2.add(new ConversationItem("I thought about what you said and you made a good point", "11:42 PM", 0));
			
			ArrayList<ConversationItem> convo3 = new ArrayList<ConversationItem>();
			convo3.add(new ConversationItem("Hello", "4:01 PM", 1));
			
			ArrayList<ConversationItem> convo4 = new ArrayList<ConversationItem>();
			convo4.add(new ConversationItem("Thank you for the advice! c:", "6:01 PM", 0));
			
			conversationList.add(new ConversationListItem("Ray", "love", convo1.get(convo1.size()-1).getMessage(), convo1));
			conversationList.add(new ConversationListItem("Gerald", "pain", convo2.get(convo2.size()-1).getMessage(), convo2));
			conversationList.add(new ConversationListItem("Ammon", "dream", convo3.get(convo3.size()-1).getMessage(), convo3));
			conversationList.add(new ConversationListItem("Rachel", "pain", convo4.get(convo4.size()-1).getMessage(), convo4));
		}
	}
	
	private void createListView() {
		ArrayAdapter<ConversationListItem> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.ConversationList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(onListClick);
	}
	
	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			Intent intent = new Intent(Messaging.this, Conversation.class);
			
			/*
			Log.d("Messaging", "position before putExtra:" + position);
			intent.putExtra("conversation", (Parcelable) conversationList.get(position).getConversation());
			Log.d("Messaging", "putExtra successful");
			*/
			
			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList("conversation", conversationList.get(position).getConversation());
			Log.d("Messaging", "Emotion: " + conversationList.get(position).getEmotion());
			bundle.putString("emotion", conversationList.get(position).getEmotion());
			intent.putExtras(bundle);
			
			startActivity(intent);
		}
	};
	
	private class MyListAdapter extends ArrayAdapter<ConversationListItem> {
		public MyListAdapter() {
			super(Messaging.this, R.layout.conv_listview, conversationList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.conv_listview, parent, false);
			}
			
			ConversationListItem currentConversation = conversationList.get(position);
			String emotion = currentConversation.getEmotion();
			
			if (emotion == "love") {
				itemView.setBackgroundResource(R.color.love);
			}
			else if (emotion == "pain") {
				itemView.setBackgroundResource(R.color.pain);
			}
			else if (emotion == "dream") {
				itemView.setBackgroundResource(R.color.dream);
			}
			
			TextView personText = (TextView) itemView.findViewById(R.id.convList_person);
			personText.setText(currentConversation.getPerson());
			
			TextView messageText = (TextView) itemView.findViewById(R.id.convList_message);
			messageText.setText(currentConversation.getRecentMessage());
			
			return itemView;
		}
		
		
	}
	
}
