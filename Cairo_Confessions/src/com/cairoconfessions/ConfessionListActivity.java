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
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.app.ListActivity;

public class ConfessionListActivity extends ListActivity {
    //private ListView confessionListView;
    private ArrayAdapter confessionItemArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_confession_list);

      confessionItemArrayAdapter = new ConfessionAdapter(this, new String[20]);
      //confessionListView = (ListView) findViewById(R.id.confessionList);
      setListAdapter(confessionItemArrayAdapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        TextView t = (TextView) v.findViewById(R.id.confessionTitle);
        t.setText("Confession Expands");
    }
}