package com.example.markcarlton.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arjun on 4/25/16.
 */
public class GridAdapter extends BaseAdapter {
    private List<Article> articles;
    private Context context;
    private Article source;

    public GridAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            view = new View(context);
            view = inflater.inflate(R.layout.home_card, null);

            ImageView SourceImg = (ImageView) view.findViewById(R.id.imgsource);
            TextView SourceTitle = (TextView) view.findViewById(R.id.titlesource);

            source = articles.get(position);
            Picasso.with(context).load(source.getUrlToImage()).into(SourceImg);
            SourceTitle.setText(source.getTitle());
        } else {
            view = convertView;
        }

        return view;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
