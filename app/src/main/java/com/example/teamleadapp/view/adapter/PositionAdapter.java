package com.example.teamleadapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.teamleadapp.R;

import java.util.List;

public class PositionAdapter extends ArrayAdapter<String>
{
    private List<String> mObjects;
    public PositionAdapter(Context context, int resourceId,
                           List<String> objects) {
        super(context, resourceId, objects);
        this.mObjects = objects;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= LayoutInflater.from(getContext());
        View row=inflater.inflate(R.layout.spin_item_position, parent, false);
        TextView label = row.findViewById(R.id.positionTextView);
        label.setText(mObjects.get(position));

        return row;
    }
}
