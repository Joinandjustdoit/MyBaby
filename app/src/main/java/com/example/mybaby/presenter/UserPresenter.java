package com.example.mybaby.presenter;

import com.example.mybaby.entity.BaseEntity;
import com.example.mybaby.http.HttpHelper;
import com.example.mybaby.http.params.BaseRequestParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by liu on 2017/12/14.
 */

public class UserPresenter {

    public Flowable<BaseEntity> login(BaseRequestParams params, String method) {

//        HttpHelper.getResponse(params, method).to(new Function<Flowable<String>, Object>() {
//            @Override
//            public Object apply(Flowable<String> stringFlowable) throws Exception {
//                return null;
//            }
//        });

        return HttpHelper.getResponse(params, method).map(new Function<String, BaseEntity>() {
            @Override
            public BaseEntity apply(String s) throws Exception {
                return new Gson().fromJson(s, new TypeToken<BaseEntity>(){}.getType());
            }
        });
    }
}
