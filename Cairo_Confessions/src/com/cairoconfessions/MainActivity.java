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
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
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

		// Instantiate a ViewPager and a PagerAdapter.
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setCurrentItem(1);
		mPager.setPageMargin(1);
		mPager.setOffscreenPageLimit(6);
		COUNTRIES = getResources().getStringArray(R.array.countries_array);
		mSwipeyTabs = (SwipeyTabsView) findViewById(R.id.swipey_tabs);
		mSwipeyTabsAdapter = new SwipeyTabsAdapter(this);
		mSwipeyTabs.setAdapter(mSwipeyTabsAdapter);
		mSwipeyTabs.setViewPager(mPager);
		Confessions = getResources().getStringArray(R.array.confession_array);
		for (int i = 0; i < Confessions.length; i++)
			addConfession(Confessions[i].toString(), "");

	}

	private String[] COUNTRIES, Confessions;

	public void follow(View view) {
		if (((TextView) view).getText().equals("Follow"))
			((TextView) view).setText("Unfollow");
		else {
			((TextView) view).setText("Follow");
			if (mTitle.equals("Followers"))
				((LinearLayout) view.getParent().getParent().getParent())
						.setVisibility(View.GONE);
		}
	}

	public void sendShare(View view) {
		Intent sendIntent = new Intent();
		String shareMessage = ((TextView) ((LinearLayout) view.getParent()
				.getParent()).findViewById(R.id.text_main)).getText()
				.toString();
		sendIntent.setAction(Intent.ACTION_SEND);
		shareMessage = "I would like to share this confession with you:\n\""
				+ shareMessage + '"';
		sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}

	public void report(View view) {

		final EditText edit = new EditText(this);
		final RadioGroup choices = new RadioGroup(this);
		edit.setText("I would like to report this confession");
		final String[] selectedItem = getResources().getStringArray(
				R.array.report_choices);

		for (int i = 0; i < selectedItem.length; i++) {
			RadioButton choice = new RadioButton(this);
			choice.setText(selectedItem[i]);
			choices.addView(choice);
		}
		choices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// checkedId is the RadioButton selected
				edit.setText("I would like to report this confession as "
						+ ((RadioButton) group.findViewById(checkedId))
								.getText().toString());

			}
		});
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.addView(choices);
		ll.addView(edit);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Choose which categories:")
				.setView(ll)
				.setPositiveButton(R.string.send,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User clicked OK button
								reportReceived();
							}
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User clicked Cancel button
							}
						}).show();
	}

	public void reportReceived() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage("Your report was sent!")
				.setPositiveButton(R.string.close,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User clicked Close button
							}
						}).show();
	}

	public void addLocation(View view) {
		TextView newView = new TextView(this);
		AutoCompleteTextView addLoc = ((AutoCompleteTextView) findViewById(R.id.addLocation));
		String newLoc = addLoc.getText().toString();
		ViewGroup locList = ((ViewGroup) findViewById(R.id.locations));
		boolean notFound = true;
		for (int i = 0; i < locList.getChildCount(); i++) {
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

		Intent mainIntent = new Intent(MainActivity.this,
				ExpandedConfessionActivity.class);
		mainIntent.putExtra("confession", ((TextView) view).getText()
				.toString());
		mainIntent.putExtra("category", ((LinearLayout) view.getParent()
				.getParent().getParent()).getContentDescription().toString());
		startActivity(mainIntent);

	};

	private int rand = 0;

	public View addConfession(String confess, String desc) {
		final ViewGroup confession = (ViewGroup) LayoutInflater.from(this)
				.inflate(R.layout.confession_list_item_example, null);
		final TextView newConfess = (TextView) confession
				.findViewById(R.id.text_main);
		newConfess.setText(confess);
		final float scale = getResources().getDisplayMetrics().density;
		if (!desc.equals("")) {
			confession.setContentDescription(desc);
			if (desc.equals("Love"))
				newConfess.setBackgroundResource(R.color.love);
			if (desc.equals("Pain"))
				newConfess.setBackgroundResource(R.color.pain);
			if (desc.equals("Guilt"))
				newConfess.setBackgroundResource(R.color.guilt);
			if (desc.equals("Fantasy"))
				newConfess.setBackgroundResource(R.color.fantasy);
			if (desc.equals("Dream"))
				newConfess.setBackgroundResource(R.color.dream);
		} else {
			switch ((new Random().nextInt(5)) % 5) {
			case 0:
				newConfess.setBackgroundResource(R.color.love);
				((TextView) confession.findViewById(R.id.confess_loc))
						.setText("Riyadh");
				confession.setContentDescription("Love");
				break;
			case 1:
				newConfess.setBackgroundResource(R.color.pain);
				((TextView) confession.findViewById(R.id.confess_loc))
						.setText("Cairo");
				confession.setContentDescription("Pain");
				break;
			case 2:
				newConfess.setBackgroundResource(R.color.guilt);
				((TextView) confession.findViewById(R.id.confess_loc))
						.setText("New York");
				confession.setContentDescription("Guilt");
				break;
			case 3:
				newConfess.setBackgroundResource(R.color.fantasy);
				((TextView) confession.findViewById(R.id.confess_loc))
						.setText("New York");
				confession.setContentDescription("Fantasy");
				break;
			case 4:
				newConfess.setBackgroundResource(R.color.dream);
				((TextView) confession.findViewById(R.id.confess_loc))
						.setText("Riyadh");
				confession.setContentDescription("Dream");
				break;
			}
		}
		switch ((new Random().nextInt(3)) % 3) {
		case 0:
			((TextView) confession.findViewById(R.id.confess_loc))
					.setText("Riyadh");
			break;
		case 1:
			((TextView) confession.findViewById(R.id.confess_loc))
					.setText("Cairo");
			break;
		case 2:
			((TextView) confession.findViewById(R.id.confess_loc))
					.setText("New York");
			break;
		}
		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setDuration(1000);
		confession.setAnimation(fadeIn);
		confession.getChildAt(0).setPadding((int) (scale * 1.5 + 0.5f),
				(int) (scale * 1.5 + 0.5f), (int) (scale * 1.5 + 0.5f),
				(int) (scale * 1.5 + 0.5f));
		confession.getChildAt(0).setBackgroundResource(R.drawable.border);
		mPager.setCurrentItem(1);
		if (!desc.equals("")) {
			((ScrollView) findViewById(R.id.feed))
					.fullScroll(ScrollView.FOCUS_UP);
			((LinearLayout) findViewById(R.id.confession_list)).addView(
					confession, 0);
		} else {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {

					((ScrollView) findViewById(R.id.feed))
							.fullScroll(ScrollView.FOCUS_UP);
					((LinearLayout) findViewById(R.id.confession_list))
							.post(new Runnable() {

								public void run() {
									((LinearLayout) findViewById(R.id.confession_list))
											.addView(confession, 0);
								}
							});
				}
			}, 1000);
		}
		return confession;

	}

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

		mFilter.findViewsWithText(presentView, ((TextView) view).getText(), 1);
		if (presentView.size() == 0) {
			final String filterName = ((TextView) view).getText().toString();
			switch (((LinearLayout) view.getParent()).getId()) {
			case R.id.cat_filter_list:
				Categories.add(filterName);
				break;
			case R.id.locations:
				Cities.add(filterName);
				break;
			}
			if (filterName.equals("Love")) {
				newView.getChildAt(0).setBackgroundResource(R.color.love);
				newViewLoc.getChildAt(0).setBackgroundResource(R.color.love);
				newViewMain.getChildAt(0).setBackgroundResource(R.color.love);
			}
			if (filterName.equals("Pain")) {
				newView.getChildAt(0).setBackgroundResource(R.color.pain);
				newViewLoc.getChildAt(0).setBackgroundResource(R.color.pain);
				newViewMain.getChildAt(0).setBackgroundResource(R.color.pain);
			}
			if (filterName.equals("Guilt")) {
				newView.getChildAt(0).setBackgroundResource(R.color.guilt);
				newViewLoc.getChildAt(0).setBackgroundResource(R.color.guilt);
				newViewMain.getChildAt(0).setBackgroundResource(R.color.guilt);
			}
			if (filterName.equals("Fantasy")) {
				newView.getChildAt(0).setBackgroundResource(R.color.fantasy);
				newViewLoc.getChildAt(0).setBackgroundResource(R.color.fantasy);
				newViewMain.getChildAt(0)
						.setBackgroundResource(R.color.fantasy);
			}
			if (filterName.equals("Dream")) {
				newView.getChildAt(0).setBackgroundResource(R.color.dream);
				newViewLoc.getChildAt(0).setBackgroundResource(R.color.dream);
				newViewMain.getChildAt(0).setBackgroundResource(R.color.dream);
			}
			((TextView) newView.findViewById(android.R.id.text1))
					.setText(filterName);
			((TextView) newViewLoc.findViewById(android.R.id.text1))
					.setText(filterName);
			((TextView) newViewMain.findViewById(android.R.id.text1))
					.setText(filterName);
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
							if (Categories.contains(filterName))
								while (Categories.remove(filterName))
									;
							if (Cities.contains(filterName))
								while (Cities.remove(filterName))
									;
							updateFilters();
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
							if (Categories.contains(filterName))
								while (Categories.remove(filterName))
									;
							if (Cities.contains(filterName))
								while (Cities.remove(filterName))
									;
							updateFilters();
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
							if (Categories.contains(filterName))
								while(Categories.remove(filterName));
							if (Cities.contains(filterName))
								while(Cities.remove(filterName));
							updateFilters();
						}

					});

			// Because mFilter has android:animateLayoutChanges set to true,
			// adding this view is automatically animated.
			// mFilterCat.addView(newViewCat);
			mFilter.addView(newView, 0);
			mFilterLoc.addView(newViewLoc, 0);
			mFilterMain.addView(newViewMain, 0);
			findViewById(R.id.content_loc).setVisibility(View.VISIBLE);
			findViewById(R.id.content_cat).setVisibility(View.VISIBLE);
			findViewById(R.id.content_main).setVisibility(View.VISIBLE);
			updateFilters();
			Toast.makeText(this, filterName + " filter added!",
					Toast.LENGTH_LONG).show();
		} else
			Toast.makeText(this, "Already added!", Toast.LENGTH_LONG).show();
	}

	List<String> Cities = new ArrayList<String>();
	List<String> Categories = new ArrayList<String>();

	public void updateFilters() {
		LinearLayout list = (LinearLayout) findViewById(R.id.confession_list);
		for (int i = 0; i < list.getChildCount(); i++) {
			View currDesc = list.getChildAt(i);
			String currLoc = ((TextView) ((LinearLayout) list.getChildAt(i))
					.findViewById(R.id.confess_loc)).getText().toString();

			if (!Categories.isEmpty() && !Cities.isEmpty()) {
				if (!Categories.contains(currDesc.getContentDescription())
						&& !Cities.contains(currLoc))
					currDesc.setVisibility(View.GONE);
				if (Categories.contains(currDesc.getContentDescription())
						&& !Cities.contains(currLoc))
					currDesc.setVisibility(View.GONE);
				if (!Categories.contains(currDesc.getContentDescription())
						&& Cities.contains(currLoc))
					currDesc.setVisibility(View.GONE);
				if (Categories.contains(currDesc.getContentDescription())
						&& Cities.contains(currLoc))
					if (mTitle.equals("Followers")) {
						if (((TextView) currDesc
								.findViewById(R.id.follow_button)).getText()
								.equals("Unfollow"))
							currDesc.setVisibility(View.VISIBLE);
					} else
						currDesc.setVisibility(View.VISIBLE);
			} else if (Categories.isEmpty() && !Cities.isEmpty()) {
				if (!Cities.contains(currLoc))
					currDesc.setVisibility(View.GONE);
				if (Cities.contains(currLoc)) {
					if (mTitle.equals("Followers")) {
						if (((TextView) currDesc
								.findViewById(R.id.follow_button)).getText()
								.equals("Unfollow"))
							currDesc.setVisibility(View.VISIBLE);
					} else
						currDesc.setVisibility(View.VISIBLE);
				}
			} else if (!Categories.isEmpty() && Cities.isEmpty()) {
				if (!Categories.contains(currDesc.getContentDescription()))
					currDesc.setVisibility(View.GONE);
				if (Categories.contains(currDesc.getContentDescription())) {
					if (mTitle.equals("Followers")) {
						if (((TextView) currDesc
								.findViewById(R.id.follow_button)).getText()
								.equals("Unfollow"))
							currDesc.setVisibility(View.VISIBLE);
					} else
						currDesc.setVisibility(View.VISIBLE);
				}
			} else if (Categories.isEmpty() && Cities.isEmpty()) {
				if (mTitle.equals("Followers")) {
					if (((TextView) currDesc.findViewById(R.id.follow_button))
							.getText().equals("Unfollow"))
						currDesc.setVisibility(View.VISIBLE);
				} else
					currDesc.setVisibility(View.VISIBLE);
			}
		}

	}

	public void updateFiltersDelete() {
		LinearLayout list = (LinearLayout) findViewById(R.id.confession_list);
		for (int i = 0; i < list.getChildCount(); i++) {
			View currDesc = list.getChildAt(i);
			String currLoc = ((TextView) ((LinearLayout) list.getChildAt(i))
					.findViewById(R.id.confess_loc)).getText().toString();

			if (!Categories.isEmpty() && !Cities.isEmpty()) {
				if (!Categories.contains(currDesc.getContentDescription())
						&& !Cities.contains(currLoc))
					currDesc.setVisibility(View.GONE);
			} else if (!Categories.isEmpty()) {
				if (!Categories.contains(currDesc.getContentDescription()))
					currDesc.setVisibility(View.GONE);
			} else if (!Cities.isEmpty()) {
				if (!Cities.contains(currLoc))
					currDesc.setVisibility(View.GONE);
			} else if (Categories.isEmpty() && Cities.isEmpty()) {
				currDesc.setVisibility(View.VISIBLE);
			} else if (Cities.isEmpty()) {
				if (Categories.contains(currDesc.getContentDescription()))
					currDesc.setVisibility(View.VISIBLE);
			} else if (Categories.isEmpty())
				if (Cities.contains(currLoc))
					currDesc.setVisibility(View.VISIBLE);
		}

	}

	public void onNavigationDrawerItemSelected(int position) {
		LinearLayout confessions = (LinearLayout) findViewById(R.id.confession_list);
		switch (position) {
		case 0:
			mTitle = "Cairo Confessions";
			for (int i = 0; i < confessions.getChildCount(); i++)
				if (((TextView) confessions.getChildAt(i).findViewById(
						R.id.follow_button)).getText().equals("Follow"))
					confessions.getChildAt(i).setVisibility(View.VISIBLE);
			updateFilters();
			break;
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			for (int i = 0; i < confessions.getChildCount(); i++)
				if (((TextView) confessions.getChildAt(i).findViewById(
						R.id.follow_button)).getText().equals("Follow"))
					confessions.getChildAt(i).setVisibility(View.GONE);
			updateFilters();
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			getSettings();
			break;

		}
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = "Cairo Confessions";
			break;
		case 2:
			mTitle = getString(R.string.title_section1);
			break;
		case 3:
			mTitle = getString(R.string.title_section2);
			break;
		case 4:
			mTitle = getString(R.string.title_section3);
			break;
		case 5:
			mTitle = getString(R.string.title_section3);
			break;

		}
	}

	/*
	 * @Override public boolean onPrepareOptionsMenu(Menu menu) { MenuItem item=
	 * menu.findItem(R.id.menu_settings); item.setVisible(true);
	 * super.onPrepareOptionsMenu(menu); }
	 */
	public void getSettings() {
		Intent intent = new Intent(this, SettingActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, 1);

	}

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

	public void sendMessage() {
		Intent intent;
		intent = new Intent(this, ComposeActivity.class);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 1) {
			String confession = data.getExtras().get("result").toString();
			String desc = data.getExtras().get("desc").toString();
			final View newConfess = addConfession(confession, desc);
			newConfess.findViewById(R.id.undoPost).setVisibility(View.VISIBLE);
			newConfess.findViewById(R.id.undoPost).setOnClickListener(
					new View.OnClickListener() {
						public void onClick(View view) {
							((LinearLayout) newConfess.getParent())
									.removeView(newConfess);
						}
					});
			new CountDownTimer(12000, 1000) {

				public void onTick(long millisUntilFinished) {
					((TextView) newConfess.findViewById(R.id.undoPost))
							.setText("Undo("
									+ ((millisUntilFinished / 1000) - 1) + ")");
				}

				public void onFinish() {
					newConfess.findViewById(R.id.undoPost).setVisibility(
							View.GONE);
				}
			}.start();
		}

	}

	public void restoreCursor() {
		((AutoCompleteTextView) findViewById(R.id.addLocation))
				.setCursorVisible(true);
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
		if (id == R.id.action_example) {
			sendMessage();
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
			View rootView = inflater.inflate(R.layout.row_confession,
					container, false);

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
