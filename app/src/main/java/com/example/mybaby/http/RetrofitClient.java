package com.example.mybaby.http;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by liu on 2017/12/5.
 */

public class RetrofitClient {

    private int TIMEOUT_READ_WRITE = 20;
    private int TIMEOUT_CONNECT = 10;

    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static String BASE_URL = "http://192.168.3.148:8080/";
    private RetrofitService service;


    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(interceptor)                            // 打印日志
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)      // 超时
            .readTimeout(TIMEOUT_READ_WRITE, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_READ_WRITE, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)                         // 失败重连
            .build();


    public RetrofitService getService() {
        if (service == null) {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build()
                    .create(RetrofitService.class);
        } else {
            return service;
        }
    }
}
