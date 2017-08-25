package com.example.markcarlton.news;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Technology extends Fragment {
    private ListView listtech;
    private GridAdapter adapter;
    private List<Article> articles;


    public Technology() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_technology, container, false);

        articles = new ArrayList<>();
        listtech = (ListView) view.findViewById(R.id.list);

        listtech.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Gson gson = new Gson();
                String articleslist = gson.toJson(articles.get(position));
                Intent intent = new Intent(getContext(), SingleArticle.class);
                intent.putExtra("articleslist", articleslist);
                startActivity(intent);
            }
        });

        GetArticles();
        return view;
    }

    private void GetArticles() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        BuilderAPI.apiService.getSpecifics().enqueue(new Callback<ResponseList>() {
            @Override
            public void onResponse(Call<ResponseList> call, Response<ResponseList> response) {

                if (response.body() != null){

                    Toasty.success(getContext(),"We found some articles for you....", Toast.LENGTH_LONG,true).show();
                    ResponseList list = response.body();
                    articles = new ArrayList<>(list.getArticles());
                    adapter = new GridAdapter(getContext(),articles);
                    listtech.setAdapter(adapter);

                } else {
                    Toasty.warning(getContext(),"No articles found",Toast.LENGTH_LONG,true).show();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(getContext(),"Connection Error.Please ensure you're connected to the internet",Toast.LENGTH_LONG,true).show();
            }
        });
    }

}
