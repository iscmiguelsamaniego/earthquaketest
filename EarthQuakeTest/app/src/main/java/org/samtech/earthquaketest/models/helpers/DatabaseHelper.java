package org.samtech.earthquaketest.models.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.samtech.earthquaketest.models.EarthQuakeProperties;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "earthquakes_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EarthQuakeProperties.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EarthQuakeProperties.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertProperty(String magnitude, String place, String date, String latitude, String longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EarthQuakeProperties.MAGNITUDE, magnitude);
        values.put(EarthQuakeProperties.PLACE, place);
        values.put(EarthQuakeProperties.DATE, date);
        values.put(EarthQuakeProperties.LATITUDE, latitude);
        values.put(EarthQuakeProperties.LONGITUDE, longitude);

        long id = db.insert(EarthQuakeProperties.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public EarthQuakeProperties getProperties(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(EarthQuakeProperties.TABLE_NAME,
                new String[]{EarthQuakeProperties.COLUMN_ID, EarthQuakeProperties.MAGNITUDE, EarthQuakeProperties.PLACE, EarthQuakeProperties.DATE, EarthQuakeProperties.LATITUDE, EarthQuakeProperties.LONGITUDE},
                EarthQuakeProperties.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        EarthQuakeProperties properties = new EarthQuakeProperties(
                cursor.getInt(cursor.getColumnIndex(EarthQuakeProperties.COLUMN_ID)),
                cursor.getDouble(cursor.getColumnIndex(EarthQuakeProperties.MAGNITUDE)),
                cursor.getString(cursor.getColumnIndex(EarthQuakeProperties.PLACE)),
                cursor.getString(cursor.getColumnIndex(EarthQuakeProperties.DATE)),
                cursor.getDouble(cursor.getColumnIndex(EarthQuakeProperties.LATITUDE)),
                cursor.getDouble(cursor.getColumnIndex(EarthQuakeProperties.LONGITUDE))
        );


        cursor.close();

        return properties;
    }

    public List<EarthQuakeProperties> getAllProperties() {
        List<EarthQuakeProperties> propertiesList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + EarthQuakeProperties.TABLE_NAME + " ORDER BY " +
                EarthQuakeProperties.MAGNITUDE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                EarthQuakeProperties properties = new EarthQuakeProperties(
                        cursor.getInt(cursor.getColumnIndex(EarthQuakeProperties.COLUMN_ID)),
                        cursor.getDouble(cursor.getColumnIndex(EarthQuakeProperties.MAGNITUDE)),
                        cursor.getString(cursor.getColumnIndex(EarthQuakeProperties.PLACE)),
                        cursor.getString(cursor.getColumnIndex(EarthQuakeProperties.DATE)),
                        cursor.getDouble(cursor.getColumnIndex(EarthQuakeProperties.LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(EarthQuakeProperties.LONGITUDE))
                );

                propertiesList.add(properties);

            } while (cursor.moveToNext());
        }

        db.close();
        return propertiesList;
    }

    public List<EarthQuakeProperties> deleteAllProperties() {
        List<EarthQuakeProperties> notes = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EarthQuakeProperties.TABLE_NAME, null, null);
        db.close();
        return notes;
    }


}
