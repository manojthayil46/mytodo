package com.example.manoj.todoapp;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Manoj on 30/01/2019.
 */

public class InfoContract {
    public static final String CONTENT_AUTHORITY = "com.example.manoj.todoapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_INFO = "myinformation";
    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INFO);

private InfoContract(){}
    public static final class DataEntry implements BaseColumns {
        public final static String TABLE_NAME = "myinformation";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "title";

    }
}
