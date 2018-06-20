package org.samtech.earthquaketest.webservices;

import org.samtech.earthquaketest.utils.Keys;
import org.samtech.earthquaketest.webservices.definitions.ApiDefinition;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServices {
    private static ApiDefinition apiDefinition;

    public static ApiDefinition api(){
        if(apiDefinition == null){
            setUpApiDefinition();
        }

        return apiDefinition;
    }

    private static void setUpApiDefinition() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Keys.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiDefinition = retrofit.create(ApiDefinition.class);
    }
}
