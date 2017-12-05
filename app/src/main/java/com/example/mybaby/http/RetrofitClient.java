package com.example.mybaby.http;

import retrofit2.Retrofit;

/**
 * Created by liu on 2017/12/5.
 */

public class RetrofitClient {

    private static String url = "http://192.168.3.148:8080/";
    private RequestService service;

    public RequestService getService() {
        if (service == null) {
            return new Retrofit.Builder()
                    .baseUrl(url)
                    .build()
                    .create(RequestService.class);
        } else {
            return service;
        }
    }
}
