package com.example.markcarlton.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mark Carlton on 25/08/2017.
 */

public class SourceAdapter extends BaseAdapter {
    private List<Source>  sourceList;
    private Context context;
    private Source source;

    public SourceAdapter(Context context, List<Source> sourceList) {
        this.context = context;
        this.sourceList = sourceList;

    }


    @Override
    public int getCount() {
        return sourceList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            view = new View(context);
            view = inflater.inflate(R.layout.cell_title_layout, null);

            TextView SourceDesc = (TextView) view.findViewById(R.id.sourcedescription);
            TextView SourceTitle = (TextView) view.findViewById(R.id.sourcename);

            source = sourceList.get(position);
            SourceTitle.setText(source.getName());
            SourceDesc.setText(source.getDescription());
        } else {
            view = convertView;
        }
        return view;
    }
}
