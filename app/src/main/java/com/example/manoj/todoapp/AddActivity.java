package com.example.manoj.todoapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
EditText edt1;
String sedt1;
String currenturi;
InfoDbHelper mdbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mdbhelper = new InfoDbHelper(this);

        Intent intent = getIntent();
        currenturi = intent.getStringExtra("uri");

        if (currenturi == null) {
            setTitle("Add a Note");
        }
        else
            {
            setTitle("Edit a Note");
            displaydatabaseinfo();
            }
    }

    private void displaydatabaseinfo() {
        edt1 = findViewById(R.id.edittext1);
        SQLiteDatabase mydb = mdbhelper.getReadableDatabase();
        String[] projection = {InfoContract.DataEntry._ID, InfoContract.DataEntry.COLUMN_TITLE};
        Cursor cursor = mydb.query(InfoContract.DataEntry.TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int TitleIndex = cursor.getColumnIndex(InfoContract.DataEntry.COLUMN_TITLE);
            String TitleName = cursor.getString(TitleIndex);
            edt1.setText(TitleName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insert();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insert() {
        edt1 = findViewById(R.id.edittext1);
        sedt1 = edt1.getText().toString();
        ContentValues values = new ContentValues();
        values.put(InfoContract.DataEntry.COLUMN_TITLE,sedt1);
        InfoDbHelper mdbhelper = new InfoDbHelper(this);
        SQLiteDatabase mydb = mdbhelper.getWritableDatabase();

        if(currenturi==null) {
            long id = mydb.insert(InfoContract.DataEntry.TABLE_NAME,null,values);
            Toast.makeText(AddActivity.this, "insert sucesss", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int rowsaffected = mydb.update(InfoContract.DataEntry.TABLE_NAME,values, InfoContract.DataEntry._ID + "=?",new String[]{String.valueOf(ContentUris.parseId(Uri.parse(currenturi)))});
            if(rowsaffected==0)
            {
                Toast.makeText(this,"update failed",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"update sucess",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
