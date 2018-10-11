package com.gamatechno.app.moviefav.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gamatechno.app.moviefav.R;
import com.gamatechno.app.moviefav.view.DetailActivity;

import static com.gamatechno.app.moviefav.database.DbContract.FavoriteColumns.DESCRIPTION;
import static com.gamatechno.app.moviefav.database.DbContract.FavoriteColumns.NAME;
import static com.gamatechno.app.moviefav.database.DbContract.FavoriteColumns.POSTER;
import static com.gamatechno.app.moviefav.database.DbContract.FavoriteColumns.RELEASE_DATE;
import static com.gamatechno.app.moviefav.database.DbContract.getColumnString;

public class MovieAdapter extends CursorAdapter{

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, viewGroup, false);

        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null){
            TextView textViewTitle, textViewOverview, textViewRelease;
            ImageView imgPoster;

            final String loadPoster = "http://image.tmdb.org/t/p/w185" + getColumnString(cursor, POSTER);

            textViewTitle = view.findViewById(R.id.item_judul);
            textViewOverview = view.findViewById(R.id.item_deskripsi);
            textViewRelease = view.findViewById(R.id.item_date);
            imgPoster = view.findViewById(R.id.image_movie);
            CardView cv_listMovie = view.findViewById(R.id.cv_movies);
            cv_listMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title", getColumnString(cursor,NAME));
                    i.putExtra("backdrop", loadPoster);
                    i.putExtra("overview", getColumnString(cursor,DESCRIPTION));
                    i.putExtra("release_date", getColumnString(cursor,RELEASE_DATE));
                    context.startActivity(i);
                }
            });

            textViewTitle.setText(getColumnString(cursor,NAME));
            textViewOverview.setText(getColumnString(cursor,DESCRIPTION));
            textViewRelease.setText(getColumnString(cursor,RELEASE_DATE));
            Glide.with(context).load(loadPoster)
                    .into(imgPoster);
        }
    }
}
