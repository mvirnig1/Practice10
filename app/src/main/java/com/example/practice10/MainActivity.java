package com.example.practice10;

import android.os.Bundle;
import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ListActivity {
    private CommentsDataSource datasource;

    TextView textViewRate;
    EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText  = (EditText) findViewById(R.id.editText);
        textViewRate = (TextView) findViewById(R.id.textViewRate);

        datasource = new CommentsDataSource(this);
        datasource.open();

        List<Comment> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        switch (view.getId()) {
            case R.id.add:
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                String[] rating = new String[] {"1", "2", "3", "4", "5"};
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                comment = datasource.createComment(comments[nextInt], rating[nextInt]);
                adapter.add(comment);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}