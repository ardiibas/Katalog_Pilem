package com.gamatechno.app.katalogpilem.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gamatechno.app.katalogpilem.R;
import com.gamatechno.app.katalogpilem.adapter.FavoriteAdapter;

import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class FavoriteFragment extends Fragment{

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    private FavoriteAdapter favoriteAdapter;
    private Cursor cursor;

    public FavoriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_favorite, container, false
        );

        progressDialog = ProgressDialog.show(getContext(), null, getString(R.string.loading), true, false);

        recyclerView = view.findViewById(R.id.fragment_favorite_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        favoriteAdapter = new FavoriteAdapter(getContext());
        favoriteAdapter.setListFavorite(cursor);

        recyclerView.setAdapter(favoriteAdapter);

        new LoadAsync().execute();

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor favorite) {
            super.onPostExecute(favorite);
            progressDialog.dismiss();

            cursor = favorite;
            favoriteAdapter.setListFavorite(cursor);
            favoriteAdapter.notifyDataSetChanged();
        }
    }
}
