package com.gamatechno.app.katalogpilem.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gamatechno.app.katalogpilem.R;
import com.gamatechno.app.katalogpilem.model.MovieCatalogueData;
import com.gamatechno.app.katalogpilem.utils.StringUtils;
import com.gamatechno.app.katalogpilem.view.DetailActivity;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<MovieCatalogueData> listMovies;
    public final static String EXTRA_MOVIE = "movie";

    public MovieAdapter(Context context, List<MovieCatalogueData> listMovies) {
        this.context = context;
        this.listMovies = listMovies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        final MovieCatalogueData data = listMovies.get(position);

        holder.tvTitle.setText(data.getTitle());
        holder.tvDeskripsi.setText(data.getOverview());
        holder.tvDate.setText(StringUtils.dateConverter(data.getReleaseDate()));

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185"+data.getPosterPath())
                .into(holder.ivPoster);

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPoster;
        private TextView tvTitle, tvDeskripsi, tvDate;

        public MovieViewHolder(View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.image_movie);
            tvTitle = itemView.findViewById(R.id.item_judul);
            tvDeskripsi = itemView.findViewById(R.id.item_deskripsi);
            tvDate = itemView.findViewById(R.id.item_date);

            itemView.setOnClickListener(v -> {
                int position = getLayoutPosition();
                MovieCatalogueData movieCatalogueData = listMovies.get(position);

                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("title", movieCatalogueData.getTitle());
                i.putExtra("backdrop", movieCatalogueData.getBackdropPath());
                i.putExtra("overview", movieCatalogueData.getOverview());
                i.putExtra("release_date", StringUtils.dateConverter(movieCatalogueData.getReleaseDate()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            });
        }
    }
}
