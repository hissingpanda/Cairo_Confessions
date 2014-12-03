package com.cairoconfessions;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingActivity extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity_main);

		getActionBar().setTitle("Settings");

		// Set up the drawer.
		Spinner spinner = (Spinner) findViewById(R.id.spinner_lang);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.lang_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		// setTitle("Cairo Confessions");

	}
	
	public void hidetabs(View view){
		MainActivity.mSwipeyTabs.setVisibility(View.GONE);
	}

	}

