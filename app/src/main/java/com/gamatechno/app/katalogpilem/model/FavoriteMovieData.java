package com.gamatechno.app.katalogpilem.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.gamatechno.app.katalogpilem.database.DatabaseContract;

import static android.provider.BaseColumns._ID;

import static com.gamatechno.app.katalogpilem.database.DatabaseContract.getColumnInt;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.getColumnString;

public class FavoriteMovieData implements Parcelable {

    private int id;
    private String name;
    private String poster;
    private String date;
    private String description;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPoster() {
        return poster;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.poster);
        dest.writeString(this.date);
        dest.writeString(this.description);
    }

    public FavoriteMovieData() {

    }

    public FavoriteMovieData(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.name = getColumnString(cursor, DatabaseContract.FavoriteColumns.NAME);
        this.poster = getColumnString(cursor, DatabaseContract.FavoriteColumns.POSTER);
        this.date = getColumnString(cursor, DatabaseContract.FavoriteColumns.RELEASE_DATE);
        this.description = getColumnString(cursor, DatabaseContract.FavoriteColumns.DESCRIPTION);
    }

    protected FavoriteMovieData(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.poster = in.readString();
        this.date = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<FavoriteMovieData> CREATOR = new Parcelable.Creator<FavoriteMovieData>() {
        @Override
        public FavoriteMovieData createFromParcel(Parcel source) {
            return new FavoriteMovieData(source);
        }

        @Override
        public FavoriteMovieData[] newArray(int size) {
            return new FavoriteMovieData[size];
        }
    };
}