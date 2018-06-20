package org.samtech.earthquaketest.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.samtech.earthquaketest.R;
import org.samtech.earthquaketest.adapters.EarthQuakeListAdapter;
import org.samtech.earthquaketest.models.EarthQuakeProperties;
import org.samtech.earthquaketest.models.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EarthQuakesListActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView earthQuakeRecyclerView;
    private List<EarthQuakeProperties> propertiesList = new ArrayList<>();
    private DatabaseHelper db;

    public static EarthQuakesListActivity newInstance() {
        return new EarthQuakesListActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_earthquake_list);

        swipeRefreshLayout = findViewById(R.id.act_earthquake_swipelayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorPrimaryLigth);
        earthQuakeRecyclerView = findViewById(R.id.act_earthquake_recycler_view);
        db = new DatabaseHelper(this);

        populateRecyclerView();
        setSwipe();
    }

    private void setSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateRecyclerView();
            }
        });
    }

    private void populateRecyclerView() {

        propertiesList.clear();
        propertiesList.addAll(db.getAllProperties());
        swipeRefreshLayout.setRefreshing(false);

        if(propertiesList.isEmpty()){
            hideList(true);
        }else {
            hideList(false);
            earthQuakeRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            earthQuakeRecyclerView.setLayoutManager(layoutManager);
            earthQuakeRecyclerView.setItemAnimator(new DefaultItemAnimator());

            EarthQuakeListAdapter earthQuakeListAdapter = new EarthQuakeListAdapter(this, propertiesList);
            earthQuakeRecyclerView.setAdapter(earthQuakeListAdapter);
        }

    }

    private void hideList(boolean isHidden){
        if(isHidden) {
            swipeRefreshLayout.setVisibility(View.GONE);
            earthQuakeRecyclerView.setVisibility(View.GONE);
        }else{
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            earthQuakeRecyclerView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onDestroy() {
        db.deleteAllProperties();
        super.onDestroy();
    }
}

