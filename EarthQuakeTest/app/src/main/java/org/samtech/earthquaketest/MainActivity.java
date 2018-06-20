package org.samtech.earthquaketest;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.samtech.earthquaketest.activities.EarthQuakeDetailActivity;
import org.samtech.earthquaketest.activities.EarthQuakesListActivity;
import org.samtech.earthquaketest.models.helpers.DatabaseHelper;
import org.samtech.earthquaketest.utils.Connection;
import org.samtech.earthquaketest.utils.DateFormatUtil;
import org.samtech.earthquaketest.utils.ETextU;
import org.samtech.earthquaketest.utils.Messages;
import org.samtech.earthquaketest.webservices.WebServices;
import org.samtech.earthquaketest.webservices.responses.Feature;
import org.samtech.earthquaketest.webservices.responses.QueryResponse;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.samtech.earthquaketest.utils.DateFormatUtil.formatDate;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private TextView mainSBResult;
    private EditText startTime, endTime;
    private DatabaseHelper db;
    private String magnitudeAux = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_earthquake_form);

        SeekBar mainSbar = findViewById(R.id.form_sbar);
        Button mainBtn = findViewById(R.id.form_btn);
        Button queryAll = findViewById(R.id.form_query_all_btn);
        Button detail = findViewById(R.id.form_show_detail);

        mainSBResult = findViewById(R.id.form_sbar_result);
        startTime = findViewById(R.id.form_startTime);
        endTime = findViewById(R.id.form_endTime);

        mainBtn.setOnClickListener(this);
        queryAll.setOnClickListener(this);
        detail.setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        mainSbar.setOnSeekBarChangeListener(this);

        db = new DatabaseHelper(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.form_btn:
                if (isValidForm()) {
                    consumeWebService
                            (ETextU.getStringFromEtext(startTime),
                                    ETextU.getStringFromEtext(endTime),
                                    magnitudeAux);
                }
                break;

            case R.id.form_startTime:
                setDatePicker(startTime);
                break;

            case R.id.form_endTime:
                setDatePicker(endTime);
                break;

            case R.id.form_query_all_btn:
                startActivity(new Intent(MainActivity.this, EarthQuakesListActivity.class));
                break;

            case R.id.form_show_detail:
                startActivity(new Intent(MainActivity.this, EarthQuakeDetailActivity.class));
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        double value = (i / 10.0);
        mainSBResult.setText(String.valueOf(value) + " Grados Richter");
        magnitudeAux = String.valueOf(value);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void setDatePicker(final EditText paramEdiText) {
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener datePickerDialog =
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        DateTime date =
                                new DateTime().
                                        withYear(year).
                                        withMonthOfYear(monthOfYear + 1).withDayOfMonth(dayOfMonth);

                        paramEdiText.setText(formatDate(date, false));
                    }
                };

        new DatePickerDialog(Objects.requireNonNull(this), datePickerDialog, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private boolean isValidForm() {
        if (Connection.isConnected(Objects.requireNonNull(this))) {
            if (!magnitudeAux.equals("")) {
                if (!ETextU.getStringFromEtext
                        (startTime).equals("aaaa-mm-dd")) {

                    if (!ETextU.getStringFromEtext
                            (endTime).equals("aaaa-mm-dd")) {
                        if (DateFormatUtil.isCurrentDate(ETextU.getStringFromEtext(startTime))) {
                            if (DateFormatUtil.isAfterDate(ETextU.getStringFromEtext(endTime))) {
                                return true;
                            } else {
                                Messages.showMsg(this, getString(R.string.no_current_date), false);
                            }
                        } else {
                            Messages.showMsg(this, getString(R.string.no_current_date), false);
                        }
                    } else {
                        Messages.showMsg(this, getString(R.string.must_enter_end_time), false);
                    }
                } else {
                    Messages.showMsg(this, getString(R.string.must_enter_start_time), false);
                }
            } else {
                Messages.showMsg(this, getString(R.string.must_have_magnitude_valid), false);
            }
        } else {
            Messages.showMsg(this, getString(R.string.must_have_network_access), false);
        }
        return false;
    }

    private void consumeWebService(String startTime, String endTime, String magnitude) {
        Call<QueryResponse> responseCallBack =
                WebServices.api()
                        .getEarthQuakes(
                                startTime,
                                endTime,
                                magnitude);

        responseCallBack.enqueue(new Callback<QueryResponse>() {
            @Override
            public void onResponse(Call<QueryResponse> call, Response<QueryResponse> response) {
                if (response.isSuccessful()) {
                    QueryResponse queryResponse = response.body();
                    System.out.println("" + queryResponse.getMetadata().getApi());

                    List<Feature> featureList = queryResponse.getFeatures();

                    String magnitude, place, date;

                    for (int x = 0; x < featureList.size(); x++) {

                        magnitude = String.valueOf(featureList.get(x).getProperties().getMag());
                        place = String.valueOf(featureList.get(x).getProperties().getPlace());
                        date = String.valueOf(featureList.get(x).getProperties().getTime());

                        List<Double> coordinatesList = featureList.get(x).getGeometry().getCoordinates();

                        for (int y = 0; y < coordinatesList.size(); y++) {

                            db.insertProperty
                                    (magnitude,
                                            place,
                                            date,
                                            String.valueOf(coordinatesList.get(0)),
                                            String.valueOf(coordinatesList.get(1))
                                    );

                        }
                    }

                    startActivity(new Intent(MainActivity.this, EarthQuakesListActivity.class));
                }
            }

            @Override
            public void onFailure(Call<QueryResponse> call, Throwable t) {
                System.out.println("" + t.getMessage());
                Messages.showMsg(MainActivity.this, "No hay resultados", false);
            }
        });
    }
}
