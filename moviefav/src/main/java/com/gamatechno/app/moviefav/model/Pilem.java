package com.gamatechno.app.moviefav.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.gamatechno.app.moviefav.database.DbContract.FavoriteColumns.DESCRIPTION;
import static com.gamatechno.app.moviefav.database.DbContract.FavoriteColumns.NAME;
import static com.gamatechno.app.moviefav.database.DbContract.FavoriteColumns.POSTER;
import static com.gamatechno.app.moviefav.database.DbContract.FavoriteColumns.RELEASE_DATE;
import static com.gamatechno.app.moviefav.database.DbContract.getColumnInt;
import static com.gamatechno.app.moviefav.database.DbContract.getColumnString;

public class Pilem implements Parcelable {

    private int id;
    private String name;
    private String poster;
    private String date;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.poster);
    }

    public Pilem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.name = getColumnString(cursor, NAME);
        this.description = getColumnString(cursor, DESCRIPTION);
        this.date = getColumnString(cursor, RELEASE_DATE);
        this.poster = getColumnString(cursor, POSTER);
    }

    private Pilem(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.poster = in.readString();
    }

    public static final Parcelable.Creator<Pilem> CREATOR = new Parcelable.Creator<Pilem>() {
        @Override
        public Pilem createFromParcel(Parcel in) {
            return new Pilem(in);
        }

        @Override
        public Pilem[] newArray(int size) {
            return new Pilem[size];
        }
    };
}
