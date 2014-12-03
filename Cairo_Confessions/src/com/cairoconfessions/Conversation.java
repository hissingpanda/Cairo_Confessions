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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		
		/*
		ConversationListItem passedConversation;
		
		if (savedInstanceState == null) {
			Log.d("Conversation", "savedInstanceState == null");
		    Bundle extras = getIntent().getExtras();
		    if(extras == null) {
		    	Log.d("Conversation", "extras == null");
		        passedConversation = null;
		    } else {
		    	Log.d("Conversation", "extras != null");
		    	passedConversation = extras.getParcelable("conversation");
		    }
		} else {
			Log.d("Conversation", "savedInstanceState != null");
			passedConversation = (ConversationListItem) savedInstanceState.getSerializable("c");
		}
		Log.d("Conversation", "bp4");
		conversation = passedConversation.getConversation();
		*/
		
		createConversation();
		createListView();
	}
	
	private void createConversation() {
		/*
		Intent intent = getIntent();
		Log.d("Conversation", "bp4");
		ConversationListItem passedConversation = intent.getParcelableExtra("c");
		Log.d("Conversation", "getParcelableExtra successful");
		conversation = passedConversation.getConversation();
		Log.d("Conversation", "conversation successfully created");
		*/
		
		Bundle bundle = getIntent().getExtras();
		ArrayList<ConversationItem> passedConversation = bundle.getParcelableArrayList("conversation");
		conversation = passedConversation;
	}
	
	private void createListView() {
		ArrayAdapter<ConversationItem> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.Conversation);
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
				}
				else {
					itemView = getLayoutInflater().inflate(R.layout.conv_right, parent, false);
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