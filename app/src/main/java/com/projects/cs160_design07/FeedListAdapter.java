package com.projects.cs160_design07;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by cstep on 4/19/2017.
 */

public class FeedListAdapter extends ArrayAdapter<Request> {

    private List<Request> requests;

    public FeedListAdapter(Context context, int resource, List<Request> requests) {
        super(context, resource, requests);
        this.requests = requests;
    }
    @Override @NonNull
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.request_layout, parent, false);
        }
        ImageView requestImage = (ImageView) convertView.findViewById(R.id.request_image);
        TextView requestName = (TextView) convertView.findViewById(R.id.request_name);
        TextView requestMessage = (TextView) convertView.findViewById(R.id.request_description);
        TextView requestCurrentTime = (TextView) convertView.findViewById(R.id.request_current_time);
        Button doneRequest = (Button) convertView.findViewById(R.id.request_reminder_button);

        if (requests.get(position) instanceof EmergencyRequest) {
            requestImage.setImageResource(R.mipmap.ic_emergency);
            requestName.setTextColor(ContextCompat.getColor(getContext(),R.color.emergency));
            requestMessage.setTextColor(ContextCompat.getColor(getContext(),R.color.emergency));
            requestCurrentTime.setTextColor(ContextCompat.getColor(getContext(),R.color.emergency));
        } else {
            requestImage.setImageResource(R.mipmap.ic_reminder);
            requestName.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            requestMessage.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            requestCurrentTime.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
        }
        requestName.setText(requests.get(position).getName());
        requestMessage.setText(requests.get(position).getMessage());
        Date current = requests.get(position).getRequestTime();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String currentDateTimeString = sdf.format(current);
        requestCurrentTime.setText(currentDateTimeString);
        doneRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRequest(requests.get(position));
            }
        });

        return convertView;
    }

    public void addRequest(Request request) {
        requests.add(request);
        notifyDataSetChanged();
    }

    public void removeRequest(Request request) {
        MainActivity.getFinishedRequests().add(request);
        System.out.println(MainActivity.getFinishedRequests().size());
        requests.remove(request);
        notifyDataSetChanged();
    }

    public boolean isRequestHere(Request request) {
        return requests.contains(request);
    }

}
