package com.cairoconfessions;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Conversation extends Activity {
	
	private ArrayList<ConversationItem> conversation = new ArrayList<ConversationItem>();
	private String emotion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		
		createConversation();
		createListView();
	}
	
	private void createConversation() {
		Bundle bundle = getIntent().getExtras();
		ArrayList<ConversationItem> passedConversation = bundle.getParcelableArrayList("conversation");
		conversation = passedConversation;
		String passedEmotion = bundle.getString("emotion");
		Log.d("Conversation", "Emotion: " + passedEmotion);
		emotion = passedEmotion;
		
	}
	
	private void createListView() {
		ArrayAdapter<ConversationItem> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.Conversation);
		list.setDivider(this.getResources().getDrawable(R.drawable.transparent_color));
		list.setDividerHeight(15);
		list.setAdapter(adapter);
		//list.setOnItemClickListener(onListClick);
	}
	/*
	private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent intent = new Intent(.this, Conversation.class);
			startActivity(intent);
		}
	};
	*/
	private class MyListAdapter extends ArrayAdapter<ConversationItem> {
		public MyListAdapter() {
			super(Conversation.this, R.layout.conv_left, conversation);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ConversationItem currentConversation = conversation.get(position);
			
			View itemView = convertView;
			if (itemView == null) {
				if (currentConversation.isMyMessage() == 0) {
					itemView = getLayoutInflater().inflate(R.layout.conv_left, parent, false);
					itemView.setPadding(0, 0, 130, 0);
					Log.d("Conversation", "Emotion is being set...");
					if (emotion == "love") {
						Log.d("Conversation", "Emotion = " + emotion);
						itemView.setBackgroundResource(R.color.love);
					}
					else if (emotion == "pain") {
						itemView.setBackgroundResource(R.color.pain);
					}
					else if (emotion == "dream") {
						itemView.setBackgroundResource(R.color.dream);
					}
				}
				else {
					itemView = getLayoutInflater().inflate(R.layout.conv_right, parent, false);
					itemView.setPadding(130, 0, 0, 0);
				}
			}
					
			TextView messageText = (TextView) itemView.findViewById(R.id.conv_message);
			messageText.setText(currentConversation.getMessage());
			
			TextView timeStampText = (TextView) itemView.findViewById(R.id.conv_timestamp);
			timeStampText.setText(currentConversation.getTimeStamp());
			
			return itemView;
		}
		
		
	}
	
}