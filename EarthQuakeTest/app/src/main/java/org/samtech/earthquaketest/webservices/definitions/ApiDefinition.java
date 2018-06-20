package org.samtech.earthquaketest.webservices.definitions;

import org.samtech.earthquaketest.webservices.responses.QueryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiDefinition {
    @GET("/fdsnws/event/1/query?format=geojson")
    Call<QueryResponse> getEarthQuakes(@Query("starttime") String startTime,
                                @Query("endtime") String endTime,
                                @Query("minmagnitude") String minMagnitude);

}