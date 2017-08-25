package com.example.markcarlton.news;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sources extends Fragment {
    private ListView SourceGrid;
    private SourceAdapter cardTabAdapter;
    private List<Source> sourceList;


    public Sources() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sources, container, false);

        sourceList = new ArrayList<>();

        SourceGrid = (ListView) view.findViewById(R.id.gridsources);

        GetSources();


        return view;
    }

    private void GetSources() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        BuilderAPI.apiService.getTechnology().enqueue(new Callback<ResponseList>() {
            @Override
            public void onResponse(Call<ResponseList> call, Response<ResponseList> response) {

                if (response.body() != null){
                    ResponseList list = response.body();
                    sourceList = new ArrayList<>(list.getSources());
                    cardTabAdapter = new SourceAdapter(getContext(),sourceList);
                    SourceGrid.setAdapter(cardTabAdapter);

                } else {
                    Toasty.warning(getContext(),"No articles found",Toast.LENGTH_SHORT,true).show();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ResponseList> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(getContext(),"Connection Error. Please ensure you're connected to the internet",Toast.LENGTH_SHORT,true).show();
            }
        });
    }

}
