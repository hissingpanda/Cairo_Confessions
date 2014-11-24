package com.cairoconfessions;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ConfessionAdapter extends ArrayAdapter{
    private LayoutInflater inflater;

    public ConfessionAdapter(Activity activity, String[] items){
       super(activity, R.layout.row_confession, items);
       inflater = activity.getWindow().getLayoutInflater();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
      return inflater.inflate(R.layout.row_confession, parent, false);
    }

}