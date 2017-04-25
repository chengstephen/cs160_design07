package com.projects.cs160_design07;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

    @Override @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_row, parent, false);
        }
        String patientItem = getItem(position);
        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);

        textView.setText(patientItem);
        imageView.setImageResource(R.drawable.grandmother);

        // for patient profile
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PatientProfileActivity.class);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
