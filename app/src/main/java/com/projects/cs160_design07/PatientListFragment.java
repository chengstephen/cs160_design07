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

    private View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.patientlist_fragment_layout, parent, false);
            String[] patients = {"Patient 1", "Patient 2", "Patient 3", "Patient 4", "Patient 5", "Patient 6"};
            PatientListAdapter adapter = new PatientListAdapter(getContext(), patients);
            ListView listView = (ListView) rootView.findViewById(R.id.patient_list_view);
            listView.setAdapter(adapter);
        }
        return rootView;
    }
}
