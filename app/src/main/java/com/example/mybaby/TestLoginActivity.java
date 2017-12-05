package com.example.mybaby;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import com.example.mybaby.entity.LoginRequestParams;
import com.example.mybaby.http.RequestService;
import com.example.mybaby.utils.Base64Util;
import com.example.mybaby.utils.DeviceInfoUtil;
import com.example.mybaby.utils.RSAUtil;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by liu on 2017/12/4.
 */

public class TestLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private RequestService service;
    private Retrofit retrofit;
    @Override
    protected void onResume() {
        super.onResume();
        retrofit = new Retrofit.Builder()
                .baseUrl(" http://192.168.3.148:8080/")
                .build();

        service = retrofit.create(RequestService.class);

        LoginRequestParams params = new LoginRequestParams();
        params.setAgentNum(DeviceInfoUtil.getInstance().getAgentNum());
        params.setChannel("Android");
        params.setCode("login");
        params.setPassword("123456");
        params.setSources("Android");
        params.setSystem(DeviceInfoUtil.getInstance().getSystem());
        params.setTelephone("18401238692");
        params.setTimestamp(System.currentTimeMillis());

        Gson gson = new Gson();
        String jo = gson.toJson(params);
//        JSONObject j = gson.fromJson(jo, JSONObject.class);

        JSONObject js = null;
        try {
             js = new JSONObject(jo);
        } catch (JSONException e) {
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(js);


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("method", "userAccountService.login");
            jsonObject.put("id", System.currentTimeMillis());
            jsonObject.put("jsonrpc", "1.0");
            jsonObject.put("params", jsonArray);

            String s = jsonObject.toString();

//            s = s.substring(0, s.length() - 1);
//            s = s.concat(",\"params\":" + params.toString() + "}");
            // 去掉反斜线
//            String s1 = s.replaceAll("\\\\", "");

            // 加密前
            Log.d("yy加密前---", s);

            // 公钥加密
            final byte[] encryptByte = RSAUtil.encryptByPublicKey(s.getBytes("UTF-8"));
            final String afterencrypt = Base64.encodeToString(encryptByte, Base64.DEFAULT);
            // 私钥解密
            byte[] after = RSAUtil.decryptByPrivateKey(Base64Util.decode(afterencrypt));
            String afterStr = new String(after);

            // 私钥加密
            String test = "{\"jsonrpc\":\"1.0\",\"id\":624889205,\"result\":{\"code\":\"login\",\"status\":\"FAILURE\",\"desc\":\"交易码验证失败\",\"token\":\"\",\"tips\":\"\",\"resultData\":null}}";
            byte[] bytes = RSAUtil.encryptByPrivateKey(test.getBytes("UTF-8"));
            String tests = Base64.encodeToString(bytes, Base64.DEFAULT);
            // 公钥解密
            byte[] bytes1 = RSAUtil.decryptByPublicKey(Base64Util.decode(tests));
            String afterTest = new String(bytes1);

//
            // 去掉换行
            Pattern p = Pattern.compile("\\s*\n");
            Matcher m = p.matcher(afterencrypt);
            final String s2 = m.replaceAll("");

            //加密后
            Log.d("yy加密后---", s2.toString());


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Call<ResponseBody> response = service.post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), afterencrypt));
                        Response<ResponseBody> responseBody = response.execute();
                        if (responseBody.code() == 200) {
                            String backStr = responseBody.body().string();
                            Log.d("yy返回---", backStr);
//                            返回数据解密
                            byte[] afterecrypt = RSAUtil.decryptByPublicKey(Base64Util.decode(backStr));
                            String a = "F4q8mtaKK4k9EissNEWgnTHzjJDQ9kVtRyvJyIi+qzFzOT5s8G4x2QWEDyzVmpNy0WLluFDKYvCZ\n" +
                                    "nPvaBMxaV1i5CXK2XiYa2UWwzSVtAsSUnnYqmDLkFxB1U7FWdcg+WEw5u6lNCDV5IooGu+jsXv79\n" +
                                    "+15XSdVl1ReAiZ7FiEE=KqFJQY1cdjpsxyoevHU/6Ob9ihg3M9M5Vinn4R8tjb2snh2k1c8YyiN1\n" +
                                    "/R1RcPDxV4i0nK+wAr/6Lo0eFYfmTO7qu9f0MXlh9tEk5DJVLojjxgojqJgWLodZonlwBIa8gvbL\n" +
                                    "lZQEak8Msf8nY/V4nxzkuDEcd48dvIBmVVZlhIc=\n";
//                            byte[] afterecrypt = RSAUtil.decryptByPublicKey(Base64Util.decode(a));
                            String sss = new String(afterecrypt);
                            String javas = decryptByPublicKey(backStr);
                            Log.d("yyjavas---", javas.toString());
                            JSONObject j = new JSONObject(new String(afterecrypt, "UTF-8"));
                            Log.d("yy解密---", j.toString());
                        }
                    } catch (Exception e) {
                        Log.d("yyException---", e.toString());
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e1) {
            Log.d("yyException---", e1.toString());
        }
    }

    //公钥
    public static final String DEFAULT_PUBLICKEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9XIQJNnsK6o6wtaGCrawYxEm5uNFKsKqraCME4dVeKoNTc9PdjyQx0REbE0hxP465ZgRZ+J2Tl7PIO628A2dZW0GeEdi55ubbDPwMQjdj1pDhvWJaEpcVM2E+PJTAdFP8eoBfp9RF+igpqHZLmPQatdvquJNWUmUujYeFJjPkOQIDAQAB";

    /**
     * 公钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data) throws Exception {
        byte[] keyBytes = Base64.decode(DEFAULT_PUBLICKEY, Base64.DEFAULT);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1PADDING", "BC");
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        // 模长
        int key_len = 128;
        byte[] bcd = Base64.decode(data, Base64.DEFAULT);
        // 如果密文长度大于模长则要分组解密
        StringBuffer sb = new StringBuffer();
        byte[][] arrays = splitArray(bcd, key_len);
        for (byte[] arr : arrays) {
            sb.append(new String(cipher.doFinal(arr), "utf-8"));
        }
        return sb.toString();
    }

    /**
     * 拆分数组
     */
    private static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                // arr = new byte[y];
                System.arraycopy(data, i * len, arr, 0, y);
            } else {

                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }

//    private Flowable<String> create(Object o, String method) {
//
//        Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(final FlowableEmitter<String> e) throws NetworkErrorException {
//                JSONObject jsonObject = new JSONObject();
//
//                try {
//                    jsonObject.put("method", method);
//                    jsonObject.put("id", System.currentTimeMillis());
//                    jsonObject.put("jsonrpc", "1.0");
//                    jsonObject.put("params", o);
////
//                    String params = jsonObject.toString();
//                    // 加密前
//                    Log.d("pre---", params);
//
//                    // 加密
//                    byte[] encryptByte = encryptByPublicKey(params.getBytes());
//                    String afterencrypt = Base64.encodeToString(encryptByte, Base64.DEFAULT);
//
//                    //加密后
//                    Log.d("after---", afterencrypt);
//
//                    Response<ResponseBody> response = service.post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), afterencrypt)).execute();
//
//                    //返回 != 200抛出错误
//                    if (response.code() != 200) {
//                        throw new NetworkErrorException(response.message());
//                    }
//
//
//                    //原始返回值
//                    String responseStr = response.body().string();
//
////                    try {
////                        JSONObject jsonResult = new JSONObject(responseStr);
////                        if (jsonResult.has("data")) {
////                            //返回数据解密，将加密后data字段数据解密
////                            String encryptData = jsonResult.optString("data");
////                            byte[] afterecrypt = RSAJava.decryptByPublicKey(Base64Util.decode(encryptData));
////                            JSONObject afterecryptData = new JSONObject(new String(afterecrypt));
////                            //取出最终数据 {"data":"对应的数据"}
////                            jsonResult.put("data", afterecryptData.get("data"));
////
////                            //处理完成赋值给返回值，进行下一步处理
////                            responseStr = jsonResult.toString();
////                        }
////                    } catch (JSONException e1) {
////
////                    }
//                    e.onNext(responseStr);
//                } catch (Exception e1) {
//                    throw new NetworkErrorException(e1.getMessage());
//                }
//                e.onComplete();
//            }
//        }, BackpressureStrategy.BUFFER);
//
//        return null;
//    }
}
