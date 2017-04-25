package com.projects.cs160_design07;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cstep on 4/19/2017.
 */

public class PatientListAdapter extends ArrayAdapter<String> {

    public PatientListAdapter(Context context, String[] patients) {
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

        return customView;
    }
}
