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

public class FeedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feed_fragment_layout, parent, false);
        ArrayList<Request> mockRequests = getMockRequests();
        ListView listView = (ListView) rootView.findViewById(R.id.feed_list_view);
        listView.setAdapter(new FeedListAdapter(getActivity(), R.layout.request_layout, mockRequests));
        return rootView;
    }

    private ArrayList<Request> getMockRequests() {
        ArrayList<Request> mockRequests = new ArrayList<>();

        Request request = new Request();

        request.setName("John Doe");
        request.setMessage("I'm hungry!");
        request.setTime("15 minutes");
        mockRequests.add(request);

        request = new Request();
        request.setName("Mary Jones");
        request.setMessage("Need to take my medicine.");
        request.setTime("1 hour");
        mockRequests.add(request);

        request = new EmergencyRequest();
        request.setName("Mary Jones");
        request.setMessage("Help!!");
        mockRequests.add(request);

        return mockRequests;
    }

}
