package com.example.mybaby.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.mybaby.R;
import com.example.mybaby.http.HttpHelper;
import com.example.mybaby.http.params.LoginRequestParams;
import com.example.mybaby.utils.DeviceInfoUtil;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;


/**
 * Created by liu on 2017/12/4.
 */

public class TestLoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);

        LoginRequestParams params = new LoginRequestParams();
        params.setAgentNum(DeviceInfoUtil.getInstance().getAgentNum());
        params.setChannel("Android");
        params.setCode("login");
        params.setPassword("123456");
        params.setSources("Android");
        params.setSystem(DeviceInfoUtil.getInstance().getSystem());
        params.setTelephone("18401238692");
        params.setTimestamp(System.currentTimeMillis());
        HttpHelper.getResponse(params, "userAccountService.login")
                .to(new Function<Flowable<String>, List<LoginRequestParams>>() {
            @Override
            public List<LoginRequestParams> apply(Flowable<String> stringFlowable) throws Exception {
                return null;
            }
        });



    }

//    private RetrofitService service;
//    private Retrofit retrofit;
//    @Override
//    protected void onResume() {
//        super.onResume();
//        retrofit = new Retrofit.Builder()
//                .baseUrl(" http://192.168.3.148:8080/")
//                .build();
//
//        service = retrofit.create(RetrofitService.class);
//
//        LoginRequestParams params = new LoginRequestParams();
//        params.setAgentNum(DeviceInfoUtil.getInstance().getAgentNum());
//        params.setChannel("Android");
//        params.setCode("login");
//        params.setPassword("123456");
//        params.setSources("Android");
//        params.setSystem(DeviceInfoUtil.getInstance().getSystem());
//        params.setTelephone("18401238692");
//        params.setTimestamp(System.currentTimeMillis());
//        try {
//            // 去掉反斜线
////            String s1 = s.replaceAll("\\\\", "");
//            String s = RequestParams.getRequestParams(params, "userAccountService.login");
//            // 加密前
//            Log.d("http加密前", s);
//
//            // 公钥加密
//            final byte[] encryptByte = RSAUtil.encryptByPublicKey(s.getBytes("UTF-8"));
//            final String afterencrypt = Base64.encodeToString(encryptByte, Base64.DEFAULT);
//
//            // 去掉换行
////            Pattern p = Pattern.compile("\\s*\n");
////            Matcher m = p.matcher(afterencrypt);
////            final String s2 = m.replaceAll("");
//            //加密后
////            Log.d("yy加密后---", s2.toString());
//
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Call<ResponseBody> response = service.post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), afterencrypt));
//                        Response<ResponseBody> responseBody = response.execute();
//                        if (responseBody.code() == 200) {
//                            String backStr = responseBody.body().string();
//                            Log.d("http返回", backStr);
////                            返回数据解密
//                            byte[] afterecrypt = RSAUtil.decryptByPublicKey(Base64Util.decode(backStr));
//                            JSONObject j = new JSONObject(new String(afterecrypt));
//                            Log.d("http解密后", j.toString());
//                        }
//                    } catch (Exception e) {
//                        Log.d("httpException---", e.toString());
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        } catch (Exception e1) {
//            Log.d("httpException---", e1.toString());
//        }
//    }
}
