package com.projects.cs160_design07;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.cs160_design07.R;

/**
 * Created by Christine on 4/24/17.
 */

 class CustomAdapter extends ArrayAdapter<String> {

     CustomAdapter(Context context, String[] patients) {
         super(context, R.layout.custom_row, patients);
     }

     @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         LayoutInflater inflater = LayoutInflater.from(getContext());
         View customView = inflater.inflate(R.layout.custom_row, parent, false);

         String patientItem = getItem(position);
         TextView textView = (TextView) customView.findViewById(R.id.textView);
         ImageView imageView = (ImageView) customView.findViewById(R.id.imageView);

         textView.setText(patientItem);
         imageView.setImageResource(R.drawable.grandmother);

         // for patient profile
         textView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(),PatientProfileActivity.class);
                 getContext().startActivity(intent);
             }
         });

        ///// Log.v("MYAPP","getview is called");
         return customView;
     }
}
