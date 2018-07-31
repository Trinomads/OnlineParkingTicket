package com.onlineparkingticket.httpmanager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by admin on 8/31/2016.
 */
public class ApiHandler {

    public static final String BASE_URL = "http://ybtestserver.in/kasi/V1/"; //Test Server

    private static Retrofit retrofit = null;

    private static Webservices apiService;

    private ApiHandler() {
    }

    public static Webservices getApiService() {

        if (apiService == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(Webservices.class);
            return apiService;
        } else {
            return apiService;
        }
    }


}
