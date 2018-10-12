package com.gamatechno.app.katalogpilem.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gamatechno.app.katalogpilem.R;
import com.gamatechno.app.katalogpilem.adapter.MovieAdapter;
import com.gamatechno.app.katalogpilem.model.MovieCatalogueData;
import com.gamatechno.app.katalogpilem.model.OtherData;
import com.gamatechno.app.katalogpilem.network.MovieClient;
import com.gamatechno.app.katalogpilem.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    private List<MovieCatalogueData> movieCatalogueData = new ArrayList<>();
    private MovieAdapter movieAdapter;

    private MovieClient client;

    public NowPlayingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_now_playing, container, false
        );
        
        client = ApiUtils.getAPIService();
        
        recyclerView = view.findViewById(R.id.fragment_now_playing_recycler);
        
        movieAdapter = new MovieAdapter(getContext(), movieCatalogueData);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(movieAdapter);

        refreshData();

        return view;
    }

    private void refreshData() {
        progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.loading), true, false);

        String api_key = "6cbbb575d03419c61482de70c8706aae";
        String language = "en-US";

        client.getNowPlaying(api_key, language).enqueue(new Callback<OtherData>() {
            @Override
            public void onResponse(Call<OtherData> call, Response<OtherData> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();

                    movieCatalogueData = response.body().getMovies();

                    recyclerView.setAdapter(new MovieAdapter(getContext(), movieCatalogueData));
                    movieAdapter.notifyDataSetChanged();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtherData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
