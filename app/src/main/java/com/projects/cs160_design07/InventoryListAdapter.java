package com.projects.cs160_design07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class InventoryListAdapter extends ArrayAdapter<InventoryItem> {

    private List<InventoryItem> items;

    public InventoryListAdapter(Context context, int resource, List<InventoryItem> items) {
        super(context, resource, items);
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.inventory_layout, parent, false);
        }
        TextView itemName = (TextView) convertView.findViewById(R.id.item_name);
        TextView itemNum = (TextView) convertView.findViewById(R.id.item_num);
        TextView itemTime = (TextView) convertView.findViewById(R.id.item_update_time);
        Button delItem = (Button) convertView.findViewById(R.id.item_delete_button);

        itemName.setText(items.get(position).getName());
        itemNum.setText(items.get(position).getNumUnits());
        itemTime.setText(items.get(position).getLastUpdateTime().toString());
        delItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(items.get(position));
            }
        });

        return convertView;
    }

    public void addItem(InventoryItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(InventoryItem item) {
        items.remove(item);
        notifyDataSetChanged();
    }

}
