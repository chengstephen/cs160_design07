package com.projects.cs160_design07;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by cstep on 4/18/2017.
 */

public class PatientListFragment extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patientlist_fragment_layout, parent, false);
        ArrayList<Request> mockRequests = getMockRequests();
        ListView listView = (ListView) rootView.findViewById(R.id.patient_list_view);
        listView.setAdapter(new FeedListAdapter(getActivity(), R.layout.request_layout, mockRequests));
        return rootView;
    }
    private ArrayList<Request> getMockRequests() {
        ArrayList<Request> mockRequests = new ArrayList<>();

        Request request = new Request();

        request.setName("John Doe");
        request.setMessage("I'm hungry!");
        mockRequests.add(request);

        request = new Request();
        request.setName("Mary Jones");
        request.setMessage("Need to take my medicine.");
        mockRequests.add(request);

        request = new EmergencyRequest();
        request.setName("Mary Jones");
        request.setMessage("Help!!");
        mockRequests.add(request);

        return mockRequests;
    }
}
