package com.cairoconfessions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
	public void postComment(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		final LinearLayout linLayout = (LinearLayout) findViewById(R.id.conv_layout);

		final TextView tx = new TextView(Conversation.this);
		Intent intent = getIntent();
		EditText editText = (EditText) findViewById(R.id.reply);
		String message = editText.getText().toString();
		intent.putExtra("Result", message);
		setResult(1, intent);
		final float scale = getResources().getDisplayMetrics().density;
		tx.setText(message);
		tx.setBackgroundColor(Color.parseColor("#009933"));
		tx.setTextColor(getResources().getColor(R.color.confession));
		tx.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tx.setTypeface(Typeface.SERIF, Typeface.NORMAL);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.setMargins((int) (0), (int) (0),
				(int) (scale * 2 + 0.5f), (int) (scale * 2 + 0.5f));
		tx.setLayoutParams(lp); // close to 100dp

		tx.setPadding((int) (scale * 5 + 0.5f), (int) (scale * 5 + 0.5f),
				(int) (scale * 5 + 0.5f), (int) (scale * 5 + 0.5f));
		linLayout.addView(tx);
		editText.setText("");
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				((ScrollView) findViewById(R.id.comment_scroll))
						.fullScroll(ScrollView.FOCUS_DOWN);
			}
		}, 500);
		/*
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				linLayout.post(new Runnable() {
							public void run() {
								tx.setBackgroundResource(R.drawable.back);
							}
						});
			}
		}, 5000); */
		setResult(1, intent);
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