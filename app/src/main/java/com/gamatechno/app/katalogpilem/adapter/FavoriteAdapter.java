package com.gamatechno.app.katalogpilem.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gamatechno.app.katalogpilem.R;
import com.gamatechno.app.katalogpilem.model.FavoriteMovieData;
import com.gamatechno.app.katalogpilem.view.DetailActivity;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Context context;
    private Cursor listFavorite;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setListFavorite(Cursor listFavorite) {
        this.listFavorite = listFavorite;
    }

    private FavoriteMovieData getData(int position) {
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new FavoriteMovieData(listFavorite);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

        final FavoriteMovieData favorite = getData(position);

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185" + favorite.getPoster())
                .into(holder.ivPoster);

        holder.tvTitle.setText(favorite.getName());
        holder.tvDeskripsi.setText(favorite.getDescription());
        holder.tvDate.setText(favorite.getDate());
        holder.cardView.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("title", favorite.getName());
            i.putExtra("backdrop", favorite.getPoster());
            i.putExtra("overview", favorite.getDescription());
            i.putExtra("release_date", favorite.getDate());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPoster;
        private TextView tvTitle, tvDeskripsi, tvDate;
        private CardView cardView;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.image_movie);
            tvTitle = itemView.findViewById(R.id.item_judul);
            tvDeskripsi = itemView.findViewById(R.id.item_deskripsi);
            tvDate = itemView.findViewById(R.id.item_date);
            cardView = itemView.findViewById(R.id.cv_movies);


        }
    }
}
