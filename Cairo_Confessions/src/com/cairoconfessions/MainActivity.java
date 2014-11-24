/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cairoconfessions;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The launchpad activity for this sample project. This activity launches other
 * activities that demonstrate implementations of common animations.
 */
public class MainActivity extends FragmentActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	/**
	 * The number of pages (wizard steps) to show in this demo.
	 */
	private static final int NUM_PAGES = 3;
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next wizard steps.
	 */
	private ViewPager mPager;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	private PagerAdapter mPagerAdapter;
	private ArrayAdapter<String> adapter;
	private SwipeyTabsView mSwipeyTabs;
	private TabsAdapter mSwipeyTabsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = "Cairo Confessions";
		getActionBar().setTitle("Cairo Confessions");

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

//<<<<<<< HEAD

		//  Intent intent = new Intent(MainActivity.this, ConfessionListActivity.class);
	      //  startActivity(intent);
		
		//FragmentManager fm = getSupportFragmentManager();  
		/*  
		  if (fm.findFragmentById(android.R.id.content) == null) {  
		   ConfessionListFragment list = new ConfessionListFragment();  
		   fm.beginTransaction().add(android.R.id.content, list).commit();  
		  }*/  
/*
		Fragment list_fragment = new ConfessionListFragment();

		FragmentTransaction fm = getSupportFragmentManager().beginTransaction().add(android.R.id.content, list_fragment);
		//fm.replace(R.id.content, list_fragment);
		fm.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fm.commit();
		fm.hide(list_fragment);
		*/
//=======

		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(1);
		mPager.setPageMargin(1);
		mPager.setOffscreenPageLimit(2);
		COUNTRIES = getResources().getStringArray(R.array.countries_array);
		mSwipeyTabs = (SwipeyTabsView) findViewById(R.id.swipey_tabs);
		mSwipeyTabsAdapter = new SwipeyTabsAdapter(this);
		mSwipeyTabs.setAdapter(mSwipeyTabsAdapter);
		mSwipeyTabs.setViewPager(mPager);

		/*mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mPager.getWindowToken(), 0);
				((AutoCompleteTextView) findViewById(R.id.addLocation)).setCursorVisible(false);
			}
		});*/		

	}

	private String[] COUNTRIES;

	public void addLocation(View view) {
		TextView newView = new TextView(this);
		AutoCompleteTextView addLoc = ((AutoCompleteTextView) findViewById(R.id.addLocation));
		String newLoc = addLoc.getText().toString();
		ViewGroup locList = ((ViewGroup) findViewById(R.id.locations));
		boolean notFound = true;
		for (int i = 0; i < locList.getChildCount(); ++i) {
			if (newLoc.equals(((TextView) locList.getChildAt(i)).getText()
					.toString()))
				notFound = false;
			break;
		}
		if (Arrays.asList(COUNTRIES).contains(newLoc) && notFound) {
			newView.setText(newLoc);
			newView.setClickable(true);
			newView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {
					addItem(view);
				}
			});
			float scale = getResources().getDisplayMetrics().density;
			newView.setGravity(17);
			newView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
			newView.setBackgroundColor(Color.RED);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, (int) (80 * scale));
			lp.setMargins((int) (3 * scale), (int) (3 * scale),
					(int) (3 * scale), (int) (3 * scale));
			newView.setLayoutParams(lp);
			locList.addView(newView, 0);
			addLoc.setText("");
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(addLoc.getWindowToken(), 0);
			addLoc.setCursorVisible(false);

		} else {
			Toast.makeText(this, "Invalid location", Toast.LENGTH_LONG).show();
		}
	}
	
	public void expandItem(View view) {
			TextView tx = (TextView) findViewById(R.id.text_main); //content_main?
			tx.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					Intent mainIntent = new Intent(MainActivity.this, ExpandedConfessionActivity.class );
					startActivity(mainIntent);
				}
			});			
	};
		
	public void addItem(View view) {
		// Instantiate a new "row" view.
		final ViewGroup mFilter = (ViewGroup) findViewById(R.id.filter_cat);
		final ViewGroup mFilterLoc = (ViewGroup) findViewById(R.id.filter_loc);
		final ViewGroup mFilterMain = (ViewGroup) findViewById(R.id.filter_main);
		final ViewGroup newView = (ViewGroup) LayoutInflater.from(this)
				.inflate(R.layout.list_item_example, null);
		final ViewGroup newViewLoc = (ViewGroup) LayoutInflater.from(this)
				.inflate(R.layout.list_item_example, null);
		final ViewGroup newViewMain = (ViewGroup) LayoutInflater.from(this)
				.inflate(R.layout.list_item_example, null);
		ArrayList<View> presentView = new ArrayList<View>();

		mFilter.findViewsWithText(presentView, ((TextView) view).getText(),1);
		
		if (presentView.size()==0) {
			// Set the text in the new row to a random country.
			((TextView) newView.findViewById(android.R.id.text1))
					.setText(((TextView) view).getText());
			((TextView) newViewLoc.findViewById(android.R.id.text1))
					.setText(((TextView) view).getText());
			((TextView) newViewMain.findViewById(android.R.id.text1))
					.setText(((TextView) view).getText());
			// Set a click listener for the "X" button in the row that will
			// remove the row.
			
			newView.findViewById(R.id.delete_button).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							mFilter.removeView(newView);
							mFilterLoc.removeView(newViewLoc);
							mFilterMain.removeView(newViewMain);
							if (mFilter.getChildCount() == 0) {
								findViewById(R.id.content_loc).setVisibility(
										View.GONE);
								findViewById(R.id.content_cat).setVisibility(
										View.GONE);
								findViewById(R.id.content_main).setVisibility(
										View.GONE);
							}
						}
					});
			newViewLoc.findViewById(R.id.delete_button).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							mFilterLoc.removeView(newViewLoc);
							mFilter.removeView(newView);
							mFilterMain.removeView(newViewMain);
							if (mFilterLoc.getChildCount() == 0) {
								findViewById(R.id.content_loc).setVisibility(
										View.GONE);
								findViewById(R.id.content_cat).setVisibility(
										View.GONE);
								findViewById(R.id.content_main).setVisibility(
										View.GONE);
							}
						}

					});
			newViewMain.findViewById(R.id.delete_button).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							mFilterLoc.removeView(newViewLoc);
							mFilter.removeView(newView);
							mFilterMain.removeView(newViewMain);
							if (mFilterMain.getChildCount() == 0) {
								findViewById(R.id.content_loc).setVisibility(
										View.GONE);
								findViewById(R.id.content_cat).setVisibility(
										View.GONE);
								findViewById(R.id.content_main).setVisibility(
										View.GONE);
							}
						}
					});
	
			// Because mFilter has android:animateLayoutChanges set to true,
			// adding this view is automatically animated.
			// mFilterCat.addView(newViewCat);
			mFilter.addView(newView);
			mFilterLoc.addView(newViewLoc);
			mFilterMain.addView(newViewMain);
			findViewById(R.id.content_loc).setVisibility(View.VISIBLE);
			findViewById(R.id.content_cat).setVisibility(View.VISIBLE);
			findViewById(R.id.content_main).setVisibility(View.VISIBLE);
			
		}else Toast.makeText(this, "Already added!", Toast.LENGTH_LONG).show();
	}

	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				/*
<<<<<<< HEAD
				//should be content_frame
				.replace(R.id.container,
=======
*/
				.replace(R.id.pager,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}
	
	
	
	public void onSectionAttached(int number) {
		//Intent intent;
		switch (number) {
		case 1:
			mTitle = "Feed";
			//intent = new Intent(MainActivity.this, ConfessionListActivity.class);
	        //startActivity(intent);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}
	/*
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    MenuItem item= menu.findItem(R.id.menu_settings);
	    item.setVisible(true);
	    super.onPrepareOptionsMenu(menu);
	}
	*/

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		if (mTitle != "Cairo Confessions")
			actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main_activity_actions, menu);
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return super.onCreateOptionsMenu(menu);
		}
		return super.onCreateOptionsMenu(menu);
	}

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.row_confession, container,
					false);

			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

	/**
	 * A simple pager adapter that represents 5 {@link ScreenSlidePageFragment}
	 * objects, in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			return ScreenSlidePageFragment.create(position);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}
}
