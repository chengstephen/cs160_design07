package com.projects.cs160_design07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cstep on 4/19/2017.
 */

public class PatientListAdapter extends ArrayAdapter<Request> {

    List<Request> requests;

    public PatientListAdapter(Context context, int resource, List<Request> requests) {
        super(context, resource, requests);
        this.requests = requests;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.request_layout, parent, false);
        }
        TextView requestName = (TextView) convertView.findViewById(R.id.request_name);
        TextView requestMessage = (TextView) convertView.findViewById(R.id.request_description);
        TextView requestCurrentTime = (TextView) convertView.findViewById(R.id.request_current_time);

        requestName.setText(requests.get(position).getName());
        requestMessage.setText(requests.get(position).getMessage());
        requestCurrentTime.setText(requests.get(position).getRequestTime().toString());

        return convertView;
    }

}
