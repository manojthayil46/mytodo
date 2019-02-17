package com.example.manoj.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;



public class DataCursorAdapter extends CursorAdapter {
    public DataCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txt1 = (TextView) view.findViewById(R.id.name);

        int titleindex = cursor.getColumnIndex(InfoContract.DataEntry.COLUMN_TITLE);
        String title = cursor.getString(titleindex);
        txt1.setText(title);
    }
}
