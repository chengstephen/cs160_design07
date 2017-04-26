package com.projects.cs160_design07;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amazonaws.util.IOUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by cstep on 4/18/2017.
 */

public class FeedFragment extends Fragment {

    private ListView listView;
    private SwipeRefreshLayout layout;
    private FeedListAdapter listAdapter;
    private ArrayList<Request> mockRequests;
    private View rootView;

    private class HttpTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream is = urlConnection.getInputStream();
                String result = IOUtils.toString(is);
                return result;
            } catch(Exception e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.feed_fragment_layout, parent, false);
            mockRequests = getMockRequests();
            layout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
            layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    String url = "https://a2owklu6i1.execute-api.us-east-1.amazonaws.com/prod/EmergenciesUpdate?TableName=Emergencies";
                    String result = "";
                    try {
                        result = new HttpTask().execute(url).get();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println(result);

                    try {
                        JSONObject resultJSON = new JSONObject(result);
                        JSONArray array = (JSONArray) resultJSON.get("Items");
                        for (int i = 0; i < array.length(); i++) {
                            Request request = new Request();
                            request.setName("James Lee");
                            request.setMessage(((JSONObject)array.get(i)).get("Emergency").toString());
                            addRequest(request);
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                    if(layout.isRefreshing()) {
                        layout.setRefreshing(false);
                    }
                }
            });
            listView = (ListView) rootView.findViewById(R.id.feed_list_view);
            listAdapter = new FeedListAdapter(getActivity(), R.layout.request_layout, mockRequests);
            listView.setAdapter(listAdapter);
        }
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

        String url = "https://a2owklu6i1.execute-api.us-east-1.amazonaws.com/prod/EmergenciesUpdate?TableName=Emergencies";
        String result = "";
        try {
            result = new HttpTask().execute(url).get();
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println(result);

        try {
            JSONObject resultJSON = new JSONObject(result);
            JSONArray array = (JSONArray) resultJSON.get("Items");
            for (int i = 0; i < array.length(); i++) {
                request = new Request();
                request.setName("James Lee");
                request.setMessage(((JSONObject)array.get(i)).get("Emergency").toString());

                boolean valid = true;
                for (Request req : mockRequests) {
                    if (req.getName().equals("James Lee") &
                            req.getMessage().equals(request.getMessage())) {
                        valid = false;
                    }
                }
                if (valid) {
                    mockRequests.add(request);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return mockRequests;
    }

    public void addRequest(Request request) {
        if(!listAdapter.isRequestHere(request)) {
            boolean valid = true;
            for (Request req : mockRequests) {
                if (req.getName().equals("James Lee") &
                        req.getMessage().equals(request.getMessage())) {
                    valid = false;
                }
            }
            if (valid) {
                listAdapter.add(request);
            }
        }
    }

}
