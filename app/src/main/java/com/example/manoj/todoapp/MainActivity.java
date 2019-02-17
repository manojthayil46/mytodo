package com.example.manoj.todoapp;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
ListView listView;
InfoDbHelper mdbhelper;

    @Override
    protected void onStart() {
        super.onStart();
        displaydatabaseinfo();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdbhelper = new InfoDbHelper(this);
        listView = findViewById(R.id.listview);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               SingleItemClick(id);
            }
        });



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                DeleteSingleItem(id);
                return true;
            }
        });
    }

    private void SingleItemClick(long id) {
        Intent intent = new Intent(MainActivity.this,AddActivity.class);
        Uri passinguri = ContentUris.withAppendedId(InfoContract.CONTENT_URI,id);
        intent.putExtra("uri",passinguri.toString());
        Toast.makeText(MainActivity.this, passinguri.toString(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }



    public void displaydatabaseinfo()
    {
        SQLiteDatabase mydb = mdbhelper.getReadableDatabase();
        String[] projection = {InfoContract.DataEntry._ID, InfoContract.DataEntry.COLUMN_TITLE};

        Cursor cursor = mydb.query(InfoContract.DataEntry.TABLE_NAME, projection, null, null,null, null, null);
        DataCursorAdapter dataCursorAdapter = new DataCursorAdapter(this,cursor);
        listView.setAdapter(dataCursorAdapter);
    }


    public void DeleteSingleItem(final long id){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Do you want to Delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase dbdelete= mdbhelper.getWritableDatabase();
                        dbdelete.delete(InfoContract.DataEntry.TABLE_NAME,InfoContract.DataEntry._ID+"=?", new String[]{String.valueOf(id)});
                        displaydatabaseinfo();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();
    }
}
