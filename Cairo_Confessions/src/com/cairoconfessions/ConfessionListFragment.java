package com.cairoconfessions;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.app.ListActivity; 

public class ConfessionListFragment extends ListFragment  
 {  
  
  String[] text = new String[] { "I�ve been living all my life abroad and I regret everything.",  "I�ve been living all my life abroad and I regret everything.",  "I�ve been living all my life abroad and I regret everything.",  "I�ve been living all my life abroad and I regret everything.",  
		  "I�ve been living all my life abroad and I regret everything. One Two three four five six One Two three four five six One Two three four five six One Two three four five six", "six", "seven", "eight", "nine", "ten", "eleven",  
    "twelve", "thirteen", "fourteen", "fifteen", "fifteen", "fifteen", "fifteen", "fifteen", "fifteen" };  
  /*
    String[] numbers_digits = new String[] { "1", "2", "3", "4", "5", "6", "7",  
    "8", "9", "10", "11", "12", "13", "14", "15" };  
  */
 // @Override  
  //public void onListItemClick(ListView l, View v, int position, long id) {  
   //new CustomToast(getActivity(), numbers_digits[(int) id]);     
  //}  
  
  @Override  
  public View onCreateView(LayoutInflater inflater, ViewGroup container,  
    Bundle savedInstanceState) {  
   ArrayAdapter<String> adapter = new ArrayAdapter<String>(  
     inflater.getContext(),	 R.layout.row_confession,  
     text);  
   setListAdapter(adapter);  
   setHasOptionsMenu(true);
   return super.onCreateView(inflater, container, savedInstanceState);  
  }  
/*
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	    MenuItem item= menu.findItem(R.id.menu_settings);
	    item.setVisible(true);
	    super.onPrepareOptionsMenu(menu);
	}*/
}  