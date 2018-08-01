package com.onlineparkingticket.httpmanager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.WsConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import okhttp3.Response;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("All")
public class ApiHandlerToken {

    public static final String BASE_URL = "http://81.4.110.105:2902/api/";

    private static Retrofit retrofit = null;

    private static Webservices apiService;
    private static Context mContext;


    public ApiHandlerToken(Context context) {
        this.mContext = context;
    }

    public static Webservices getApiService() {

        if (apiService == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client1 = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS);

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("token", "" + AppGlobal.getStringPreference(mContext, WsConstant.SP_TOKEN))
                            .method(original.method(), original.body())
                            .build();

                    AppGlobal.showLog(mContext, "Request : " + request.toString());

                    return chain.proceed(request);
                }

            });

            OkHttpClient client = httpClient.build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            apiService = retrofit.create(Webservices.class);
            return apiService;
        } else {
            return apiService;
        }
    }


}
