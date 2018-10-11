package com.gamatechno.app.moviefav.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gamatechno.app.moviefav.R;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivPoster;
    private TextView tvTitle, tvDescription, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivPoster = findViewById(R.id.detail_poster);
        tvTitle = findViewById(R.id.detail_judul);
        tvDate = findViewById(R.id.detail_date);
        tvDescription = findViewById(R.id.detail_deskripsi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }

        getData();
    }

    private void getData() {

        String backdropPath = getIntent().getStringExtra("backdrop");
        String title = getIntent().getStringExtra("title");
        String releaseDate = getIntent().getStringExtra("release_date");
        String overview = getIntent().getStringExtra("overview");

        Glide.with(this)
                .load(backdropPath)
                .into(ivPoster);

        tvTitle.setText(title);
        tvDate.setText(releaseDate);
        tvDescription.setText(overview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
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
