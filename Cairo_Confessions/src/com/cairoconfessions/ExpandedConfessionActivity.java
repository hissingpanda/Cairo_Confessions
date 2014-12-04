package com.cairoconfessions;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

public class ExpandedConfessionActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.row_confession);
		getActionBar().setTitle("Cairo Confessions");
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		Intent intent = getIntent();
		String message = intent.getStringExtra("confession");
		TextView confession = ((TextView) findViewById(R.id.text2));
		confession.setText(message);
		if (intent.getStringExtra("category").equals("Love"))
			confession.setBackgroundResource(R.color.love);

		if (intent.getStringExtra("category").equals("Pain"))
			confession.setBackgroundResource(R.color.pain);
		if (intent.getStringExtra("category").equals("Guilt"))
			confession.setBackgroundResource(R.color.guilt);
		if (intent.getStringExtra("category").equals("Fantasy"))
			confession.setBackgroundResource(R.color.fantasy);
		if (intent.getStringExtra("category").equals("Dream"))
			confession.setBackgroundResource(R.color.dream);

		final View decorView = this.getWindow().getDecorView();
		decorView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						Rect rect = new Rect();
						decorView.getWindowVisibleDisplayFrame(rect);
						ScrollView myScrollView = (ScrollView) findViewById(R.id.comment_scroll);
						int displayHeight = rect.bottom - rect.top;
						int height = decorView.getHeight();
						boolean keyboardHiddenTemp = (double) displayHeight
								/ height > 0.8;
						int mylistviewHeight = myScrollView.getMeasuredHeight();

						if (keyboardHiddenTemp != keyboardHidden) {
							keyboardHidden = keyboardHiddenTemp;

							if (!keyboardHidden) {
								getActionBar().hide();
								reduceHeight = height - displayHeight - 109;
								((EditText) findViewById(R.id.reply))
										.setCursorVisible(true);
								LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.MATCH_PARENT,
										mylistviewHeight - reduceHeight);
								myScrollView.setLayoutParams(mParam);
								myScrollView.requestLayout();

							} else {
								((EditText) findViewById(R.id.reply))
										.setCursorVisible(false);
								getActionBar().show();
								LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.MATCH_PARENT,
										mylistviewHeight + reduceHeight);
								myScrollView.setLayoutParams(mParam);
								myScrollView.requestLayout();

							}
						}

					}
				});
	}

	private static boolean keyboardHidden = true;
	private static int reduceHeight = 0;

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
	/*
	 * This is crashing so just recreated method 
	 
	public void callReport(View view){
	    MainActivity mActivity = new MainActivity();
		mActivity.report(view);
	}
	*/
	public void gotoMessage(View view){
		//setContentView(R.layout.activity_conversation);
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

	public void postComment(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		final LinearLayout linLayout = (LinearLayout) findViewById(R.id.linlayoutchild);

		final TextView tx = new TextView(ExpandedConfessionActivity.this);
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
}
