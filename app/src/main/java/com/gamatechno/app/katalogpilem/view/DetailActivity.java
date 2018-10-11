package com.gamatechno.app.katalogpilem.view;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gamatechno.app.katalogpilem.R;
import com.gamatechno.app.katalogpilem.database.DatabaseContract;

import java.util.Objects;

import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView ivPoster;
    private TextView tvDeskripsi, tvDate;

    private FloatingActionButton fab;

    private long id;
    private String backdropPath, title, releaseDate, overview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivPoster = findViewById(R.id.detail_image);
        tvDeskripsi = findViewById(R.id.detail_deskripsi);
        tvDate = findViewById(R.id.detail_date);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::favorite);

        fetchData();
        setFavorite();
    }

    private void fetchData() {

        backdropPath = getIntent().getStringExtra("backdrop");
        title = getIntent().getStringExtra("title");
        releaseDate = getIntent().getStringExtra("release_date");
        overview = getIntent().getStringExtra("overview");

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w185" + backdropPath)
                .into(ivPoster);

        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

        tvDate.setText(releaseDate);
        tvDeskripsi.setText(overview);
        fab.setImageResource(R.drawable.ic_fav_unchecked);
    }

    public boolean setFavorite() {
        Uri uri = Uri.parse(CONTENT_URI + "");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String getTitle;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getLong(0);
                getTitle = cursor.getString(1);
                if (getTitle.equals(getIntent().getStringExtra("title"))) {
                    fab.setImageResource(R.drawable.ic_fav_checked);
                    favorite = true;
                }
            } while (cursor.moveToNext());

        }

        return favorite;

    }

    public void favorite(View view) {
        if (setFavorite()) {
            Uri uri = Uri.parse(CONTENT_URI + "/" + id);
            getContentResolver().delete(uri, null, null);
            fab.setImageResource(R.drawable.ic_fav_unchecked);
            Snackbar.make(view, "Keluar pak Ekoooo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.FavoriteColumns.NAME, title);
            values.put(DatabaseContract.FavoriteColumns.POSTER, backdropPath);
            values.put(DatabaseContract.FavoriteColumns.RELEASE_DATE, releaseDate);
            values.put(DatabaseContract.FavoriteColumns.DESCRIPTION, overview);

            getContentResolver().insert(CONTENT_URI, values);
            setResult(101);

            fab.setImageResource(R.drawable.ic_fav_checked);
            Snackbar.make(view, "Masuk pak Ekoooo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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
}
