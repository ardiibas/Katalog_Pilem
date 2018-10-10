package com.gamatechno.app.katalogpilem.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.NAME;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.POSTER;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static com.gamatechno.app.katalogpilem.database.DatabaseContract.FavoriteColumns.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "db_favorite";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_FAVORITE,
            _ID,
            NAME,
            POSTER,
            RELEASE_DATE,
            DESCRIPTION
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE);
        onCreate(db);
    }
}
