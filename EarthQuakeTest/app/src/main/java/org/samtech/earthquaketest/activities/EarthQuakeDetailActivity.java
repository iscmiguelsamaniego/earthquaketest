package org.samtech.earthquaketest.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.samtech.earthquaketest.R;

public class EarthQuakeDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    double latitude, longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_earthquakedetail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getDouble("latitude");
            longitude = extras.getDouble("longitude");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        if (latitude != 0.0) {
            if(longitude != 0.0) {
                LatLng cdmx = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(cdmx).title("Marker in cdmx"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cdmx, 17.0f));
            }
        }
    }
}
