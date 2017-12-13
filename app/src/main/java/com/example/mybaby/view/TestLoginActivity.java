package com.example.mybaby.view;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mybaby.R;
import com.example.mybaby.entity.BaseEntity;
import com.example.mybaby.http.HttpHelper;
import com.example.mybaby.http.params.LoginRequestParams;
import com.example.mybaby.utils.DeviceInfoUtil;
import com.google.gson.Gson;

import java.util.List;


/**
 * Created by liu on 2017/12/4.
 */

public class TestLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "Uri";
    private Button btn1;
    private Button btn2;
    private Button twoActivity;
    private Button threeActivity;
    private PackageManager packageManager;
    private Intent intent;
    private List<ResolveInfo> activities;
    private boolean isValid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_login);

        Uri uri = Uri.parse("https://note.youdao.com/web/#/file/WEBafaa7511fcb0d9da061ceebe4a13255f/note/WEB4189dc324c2d487bc17e783e3a2de68c/");
        Log.d(TAG, "scheme：" + uri.getScheme());
        Log.d(TAG, "host：" + uri.getHost());
        Log.d(TAG, "port：" + uri.getPort());
        Log.d(TAG, "path：" + uri.getPath());
        Log.d(TAG, "query：" + uri.getQuery());

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        twoActivity = (Button) findViewById(R.id.two);
        threeActivity = (Button) findViewById(R.id.three);

        packageManager = getPackageManager();


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        twoActivity.setOnClickListener(this);
        threeActivity.setOnClickListener(this);

        LoginRequestParams params = new LoginRequestParams();
        params.setAgentNum(DeviceInfoUtil.getInstance().getAgentNum());
        params.setChannel("Android");
        params.setCode("login");
        params.setPassword("123456");
        params.setSources("Android");
        params.setSystem(DeviceInfoUtil.getInstance().getSystem());
        params.setTelephone("18401238692");
        params.setTimestamp(System.currentTimeMillis());
        HttpHelper.getResponse(params, "userAccountService.login");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));
                break;
            case R.id.btn2:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://note.youdao.com/web/#/file/WEBafaa7511fcb0d9da061ceebe4a13255f/note/WEB4189dc324c2d487bc17e783e3a2de68c/"));
                break;
            case R.id.two:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("lp://mmm"));
                break;
            case R.id.three:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("gg://com"));
                break;
        }
        activities = packageManager.queryIntentActivities(intent, 0);
        isValid = !activities.isEmpty();
        if (isValid) {
            startActivity(intent);
        }
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
