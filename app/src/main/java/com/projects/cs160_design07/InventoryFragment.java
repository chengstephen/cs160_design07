package com.projects.cs160_design07;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by cstep on 4/18/2017.
 */

public class InventoryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feed_fragment_layout, container, false);
        ArrayList<InventoryItem> mockInventory = getMockInventory();
        ListView listView = (ListView) rootView.findViewById(R.id.feed_list_view);
        listView.setAdapter(new InventoryListAdapter(getActivity(), R.layout.inventory_layout, mockInventory));
        return rootView;
    }

    private ArrayList<InventoryItem> getMockInventory() {
        ArrayList<InventoryItem> items = new ArrayList<>();
        return items;
    }

}
