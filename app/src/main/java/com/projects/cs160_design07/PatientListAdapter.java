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


}
