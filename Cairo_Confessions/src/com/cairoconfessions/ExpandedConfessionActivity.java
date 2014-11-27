package com.cairoconfessions;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ExpandedConfessionActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.row_confession);
		getActionBar().setTitle("Cairo Confessions");
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		EditText editText = (EditText) findViewById(R.id.reply);
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

		editText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean handled = false;
				Intent intent = getIntent();
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					sendMessage();
					// EditText comment = (EditText) findViewById(R.id.reply);
					// String message = comment.getText().toString();
					// intent.putExtra("result", message);
					setResult(1, intent);
					handled = true;
				}
				return handled;
			}

			private void sendMessage() {
				LinearLayout linLayout = (LinearLayout) findViewById(R.id.linlayoutchild);

				TextView tx = new TextView(ExpandedConfessionActivity.this);
				Intent intent = getIntent();
				EditText editText = (EditText) findViewById(R.id.reply);
				String message = editText.getText().toString();
				intent.putExtra("Result", message);
				setResult(1, intent);
				final float scale = getResources().getDisplayMetrics().density;
				tx.setText(message);
				tx.setBackgroundResource(R.drawable.back);
				tx.setTextColor(getResources().getColor(R.color.confession));
				tx.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				tx.setTypeface(Typeface.SERIF, Typeface.NORMAL);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				lp.setMargins((int) (scale * 5 + 0.5f),
				/* (int) (scale * 5 + 0.5f) */0, (int) (scale * 5 + 0.5f),
						(int) (scale * 5 + 0.5f));
				tx.setLayoutParams(lp); // close to 100dp

				tx.setPadding((int) (scale * 5 + 0.5f),
						(int) (scale * 5 + 0.5f), (int) (scale * 5 + 0.5f),
						(int) (scale * 5 + 0.5f));
				linLayout.addView(tx);
				editText.setText("");
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						((ScrollView) findViewById(R.id.comment_scroll))
								.fullScroll(ScrollView.FOCUS_DOWN);
					}
				}, 500);

			}
		});
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
								reduceHeight = height - displayHeight - 55;

								LinearLayout.LayoutParams mParam = new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.MATCH_PARENT,
										mylistviewHeight - reduceHeight);
								myScrollView.setLayoutParams(mParam);
								myScrollView.requestLayout();

							} else {
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
}
