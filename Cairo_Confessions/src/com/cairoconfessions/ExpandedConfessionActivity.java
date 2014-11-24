package com.cairoconfessions;

import android.app.Activity;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
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
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.app.ListActivity; 

public class ExpandedConfessionActivity extends ListActivity
 {  
  
  String[] text = new String[3];  
  /*
    String[] numbers_digits = new String[] { "1", "2", "3", "4", "5", "6", "7",  
    "8", "9", "10", "11", "12", "13", "14", "15" };  
  */
 // @Override  
  //public void onListItemClick(ListView l, View v, int position, long id) {  
   //new CustomToast(getActivity(), numbers_digits[(int) id]);     
  //}  

private NavigationDrawerFragment mNavigationDrawerFragment;
  @Override  
  protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      //ListAdapter adapter = new SimpleCursorAdapter(this, R.layout.row_confession, rows);
      setContentView(R.layout.row_confession);
  	 // setListAdapter(adapter);  
     /*
      mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
      mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
*/
   }
  
/*
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
	    MenuItem item= menu.findItem(R.id.menu_settings);
	    item.setVisible(true);
	    super.onPrepareOptionsMenu(menu);
	}*/
}  