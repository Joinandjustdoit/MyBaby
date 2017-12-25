package com.example.mybaby.http;

import android.accounts.NetworkErrorException;
import android.util.Base64;
import android.util.Log;
import com.example.mybaby.BuildConfig;
import com.example.mybaby.http.params.BaseRequestParams;
import com.example.mybaby.utils.Base64Util;
import com.example.mybaby.utils.RSAUtil;
import org.json.JSONObject;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by liu on 2017/12/5.
 */

public class HttpHelper {

    private static final String TAG = "HTTP";

//    public static Flowable<Object> getResponse(final BaseRequestParams params, final String method, final Class clazz) {
    public static Flowable<String> getResponse(final BaseRequestParams params, final String method) {
        return Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {

                String s = RequestParams.getRequestParams(params, method);
                // 加密前
                log("加密前", s);

                // 公钥加密
                final byte[] encryptByte = RSAUtil.encryptByPublicKey(s.getBytes("UTF-8"));
                final String afterencrypt = Base64.encodeToString(encryptByte, Base64.DEFAULT);

                // 发请求
                Response<ResponseBody> response = RetrofitClient.createApi()
                        .post(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), afterencrypt))
                        .execute();

                String backStr = response.body().string();
                log("返回", backStr);

                if (response.isSuccessful()) {
                    // 返回数据解密
                    byte[] afterecrypt = RSAUtil.decryptByPublicKey(Base64Util.decode(backStr));
                    JSONObject jo = new JSONObject(new String(afterecrypt));
                    log("解密后", jo.toString());
                    if (jo.has("result") && jo.optString("result") != null) {
                        JSONObject jsonObject = new JSONObject(jo.optString("result"));
                        if (jsonObject.has("status") && jsonObject.optString("status") != null) {
                            if (jsonObject.optString("status").equals("SUCCESS")) {
                                if (jsonObject.has("resultData") && jsonObject.optString("resultData") != null) {
                                    e.onNext(jsonObject.optString("resultData"));
//                                    e.onNext(new Gson().fromJson(jsonObject.optString("resultData"), clazz));
                                } else {
                                    throwException("resultData is null");
                                }
                            } else {
                                throwException("status is not SUCCESS");
                            }
                        } else {
                            throwException("status is null");
                        }
                    } else {
                        throwException("result is null");
                    }
                } else {
                    throwException(response.message());
                }
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);
    }


    /**
     * 异常
     * @param message
     * @throws NetworkErrorException
     */
    private static void throwException(String message) throws NetworkErrorException {
        throw new NetworkErrorException(message);
    }

    /**
     * 日志
     * @param tag
     * @param s
     */
    private static void log(String tag, String s) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG.concat(tag), s);
        }
    }

}
