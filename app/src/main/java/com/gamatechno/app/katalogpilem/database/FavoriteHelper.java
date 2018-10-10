package com.gamatechno.app.katalogpilem.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.gamatechno.app.katalogpilem.model.FavoriteMovieData;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.TABLE_FAVORITE;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.NAME;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.POSTER;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.RELEASE_DATE;

public class FavoriteHelper {

    private static String DATABASE_TABLE = TABLE_FAVORITE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context) {
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public ArrayList<FavoriteMovieData> query() {

        ArrayList<FavoriteMovieData> arrayList = new ArrayList<FavoriteMovieData>();

        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null, _ID + " DESC"
                , null);

        cursor.moveToFirst();

        FavoriteMovieData favorite;

        if (cursor.getCount() > 0) {
            do {

                favorite = new FavoriteMovieData();
                favorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favorite.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                favorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favorite.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                favorite.setDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));

                arrayList.add(favorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }

        cursor.close();

        return arrayList;
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}