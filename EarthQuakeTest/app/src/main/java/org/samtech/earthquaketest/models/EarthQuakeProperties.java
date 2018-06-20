package org.samtech.earthquaketest.models;

public class EarthQuakeProperties {

    public static final String TABLE_NAME = "properties";

    public static final String COLUMN_ID = "id";
    public static final String MAGNITUDE = "magnitude";
    public static final String PLACE = "place";
    public static final String DATE = "date";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";


    private int id;
    private double magnitude;
    private String place;
    private String date;
    private double latitude;
    private double longitude;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + MAGNITUDE + " TEXT,"
                    + PLACE + " TEXT,"
                    + DATE + " TEXT,"
                    + LATITUDE + " TEXT,"
                    + LONGITUDE + " TEXT"
                    + ")";

    public EarthQuakeProperties() {
    }

    public EarthQuakeProperties(int id, double magnitude, String place, String date, double latitude, double longitude) {
        this.id = id;
        this.magnitude = magnitude;
        this.place = place;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
