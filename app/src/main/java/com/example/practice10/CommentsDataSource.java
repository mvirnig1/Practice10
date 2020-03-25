package com.example.practice10;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_COMMENT };

    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
/*
*creates a comment to the table
 */
    public Comment createComment(String comment, String rating) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
        values.put(MySQLiteHelper.COLUMN_RATING, rating);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }
/*
* deletes comments from table
 */
    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }
/*
* retrieves all comments in table
 */
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS, null, null,
                null, null, null, null);


        while (!cursor.moveToFirst()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }
/*
* moves the cursor to the comment on the table
 */
    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setRating(cursor.getString( cursor.getColumnIndex( MySQLiteHelper.COLUMN_RATING ) ));
        comment.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }


}
