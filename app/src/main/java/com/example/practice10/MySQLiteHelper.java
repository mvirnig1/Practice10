package com.example.practice10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class serves as a helper class to open and create database. There are constant variables created for the table
 * columns, constructor defined to create table, SQL database executed OnCreate, and code is used to
 * assist with upgrading database
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENTS = "comments"; //name of table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COMMENT = "comment"; //column name for primary key

    private static final String DATABASE_NAME = "commments.db"; //column mane for comment field
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    /* SQL table create----------------
    * CREATE TABLE comment(
    * COMMENT_ID string,
    * COMMENT string,
    * );
     */
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }
}

