package com.gamatechno.app.katalogpilem.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.gamatechno.app.katalogpilem.R;
import com.gamatechno.app.katalogpilem.adapter.MovieAdapter;
import com.gamatechno.app.katalogpilem.model.MovieCatalogueData;
import com.gamatechno.app.katalogpilem.model.OtherData;
import com.gamatechno.app.katalogpilem.network.MovieClient;
import com.gamatechno.app.katalogpilem.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private MovieAdapter movieAdapter;
    private List<MovieCatalogueData> catalogueDataList = new ArrayList<>();

    private MovieClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String keyword = getIntent().getStringExtra("keyword");

        Objects.requireNonNull(getSupportActionBar()).setTitle(keyword);

        client = ApiUtils.getAPIService();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.search_recycler);
        movieAdapter = new MovieAdapter(getApplicationContext(), catalogueDataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(movieAdapter);

        searchData(keyword);
    }

    private void searchData(String keyword) {

        String api_key = "6cbbb575d03419c61482de70c8706aae";
        String language = "en-US";
        String include_adult = "false";
        String page = "1";

        client.searchMovie(api_key, language, keyword, page, include_adult).enqueue(new Callback<OtherData>() {
            @Override
            public void onResponse(Call<OtherData> call, Response<OtherData> response) {
                if (response.isSuccessful()) {
                    catalogueDataList = response.body().getMovies();

                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), catalogueDataList));
                    movieAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OtherData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
