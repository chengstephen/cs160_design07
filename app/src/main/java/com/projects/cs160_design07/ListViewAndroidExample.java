package com.projects.cs160_design07;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Christine on 4/25/17.
 */

public class ListViewAndroidExample extends ListActivity {

    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_android_example);


       ///// content = (TextView)findViewById(R.id.output);

        //listView = (ListView) findViewById(R.id.list);
        String[] patients = {"Patient 1", "Patient 2", "Patient 3", "Patient 4", "Patient 5", "Patient 6"};
        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third - the Array of data

        CustomAdapter adapter = new CustomAdapter(this, patients);


        // Assign adapter to List
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);

        // ListView Clicked item index
        int itemPosition     = position;

        // ListView Clicked item value
        String  itemValue    = (String) l.getItemAtPosition(position);

        content.setText("Click : \n  Position :"+itemPosition+"  \n  ListItem : " +itemValue);

    }
}