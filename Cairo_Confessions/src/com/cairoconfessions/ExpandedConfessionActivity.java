package com.cairoconfessions;

import com.cairoconfessions.MainActivity.PlaceholderFragment;

import android.app.Activity;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.app.ListActivity; 

public class ExpandedConfessionActivity extends FragmentActivity 
{  
  @Override  
  protected void onCreate(Bundle savedInstanceState){

  		super.onCreate(savedInstanceState);
  		setContentView(R.layout.row_confession);
  		getActionBar().setTitle("Cairo Confessions");

  		EditText editText = (EditText) findViewById(R.id.reply);
  		final TextView tx = new TextView(this);
  		editText.setOnEditorActionListener(new OnEditorActionListener() {
  		    @Override
  		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
  		        boolean handled = false;
  				//Intent intent = getIntent();
  		        if (actionId == EditorInfo.IME_ACTION_SEND) {
  		            sendMessage(tx);
  		        	//EditText comment = (EditText) findViewById(R.id.reply);
  		        	//String message = comment.getText().toString();
  		          	//intent.putExtra("result", message);
  		            //setResult(1,intent);
  		            handled = true;
  		        }
  		        return handled;
  		    }

			private void sendMessage(TextView tx) {
				 LinearLayout linLayout = (LinearLayout)findViewById(R.id.linlayout);
				 Intent intent = getIntent();
			     EditText editText = (EditText) findViewById(R.id.reply);
			     String message = editText.getText().toString();
			     intent.putExtra("Result", message);
			     setResult(1,intent);
			     tx.setText(message);
			     tx.setGravity(Gravity.CENTER | Gravity.TOP);
			     /*
	     			android:textSize="18sp"
	    	        android:typeface="serif"
	    	        android:textColor="@color/confession"
	    	        android:textStyle="normal" 
	    	        */
			     tx.setBackgroundResource(R.drawable.back);
			    // tx.setTextColor(222222);
			     tx.setTextSize(18);
			     
			     tx.setLayoutParams(new LayoutParams(
			     		 LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			     linLayout.addView(tx);
				
			}
  		});      	
  		}
  /*
  		public void sendMessage() {
	      Intent intent = getIntent();
	      EditText editText = (EditText) findViewById(R.id.reply);
	      String message = editText.getText().toString();
	      intent.putExtra("Result", message);
	      setResult(1,intent);
  		}*/
          @Override
          public boolean onOptionsItemSelected(MenuItem item) {
              // Handle action bar item clicks here. The action bar will
              // automatically handle clicks on the Home/Up button, so long
              // as you specify a parent activity in AndroidManifest.xml.
              int id = item.getItemId();
              if (id == R.id.action_settings) {
                  return true;
              }
              return super.onOptionsItemSelected(item);
          }
          

      }
