package org.samtech.earthquaketest.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.samtech.earthquaketest.R;
import org.samtech.earthquaketest.activities.EarthQuakeDetailActivity;
import org.samtech.earthquaketest.models.EarthQuakeProperties;

import java.util.List;

public class EarthQuakeListAdapter extends RecyclerView.Adapter<EarthQuakeListAdapter.ViewHolder> {

    private List<EarthQuakeProperties> propertiesList;
    private Context context;

    public EarthQuakeListAdapter(Context context, List<EarthQuakeProperties> propertiesList) {
        this.context = context;
        this.propertiesList = propertiesList;
    }

    @Override
    public EarthQuakeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View layoutView =
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_earthquake_list, null);

        return new ViewHolder(layoutView);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EarthQuakeListAdapter.ViewHolder holder, int position) {

        final EarthQuakeProperties properties = propertiesList.get(position);

        setStatus(properties.getMagnitude(), holder.color);
        holder.magnitude.setText(String.valueOf(properties.getMagnitude()) + " Grados Richter");
        holder.date.setText(properties.getDate());
        holder.place.setText(properties.getPlace());

        holder.containerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, EarthQuakeDetailActivity.class);
                i.putExtra("latitude", properties.getLatitude());
                i.putExtra("longitude", properties.getLongitude());
                context.startActivity(i);

            }
        });

    }

    private void setStatus(double statusValue, TextView view) {
        if (statusValue >= 0.0 && statusValue <= 4.0) {
            view.setBackgroundResource(R.color.colorGreen);
        }

        if (statusValue >= 4.1 && statusValue <= 6.0) {
            view.setBackgroundResource(R.color.colorYellow);
        }

        if (statusValue >= 6.1 && statusValue <= 7.0) {
            view.setBackgroundResource(R.color.colorOrange);
        }

        if (statusValue >= 7.1) {
            view.setBackgroundResource(R.color.colorRed);
        }
    }

    @Override
    public int getItemCount() {
        return propertiesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView place, magnitude, date, color;
        private CardView containerCardView;

        ViewHolder(View itemView) {
            super(itemView);

            containerCardView = itemView.findViewById(R.id.item_card_view);
            color = itemView.findViewById(R.id.item_magnitude_color);
            place = itemView.findViewById(R.id.item_place);
            magnitude = itemView.findViewById(R.id.item_magnitude);
            date = itemView.findViewById(R.id.item_date);
        }
    }
}