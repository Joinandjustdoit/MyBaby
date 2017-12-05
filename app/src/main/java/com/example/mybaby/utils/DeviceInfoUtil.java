package com.example.mybaby.utils;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * <p>Description: </p>
 * <p>Company: 天财宝</p>
 * <p>Create Time:2017/3/6 12:53</p>
 *
 * @author MiaoWenHai
 */
public class DeviceInfoUtil {

    private static final DeviceInfoUtil ourInstance = new DeviceInfoUtil();

    public static DeviceInfoUtil getInstance() {
        return ourInstance;
    }


    private DeviceInfoUtil() {
    }

    /** 设备编码 */
    private String agentNum;
    /** 手机品牌/型号 */
    private String device;
    /** 操作系统 */
    private String system;
    /** app版本名称 */
    private String appVersion;
    /** 渠道名称 */
    private String channel;
    /** Http Headers */
    private Map<String, String> headerMap;


    public void init(Application app) {

        //获取设备序列号
        agentNum = AppUtil.getIMEI(app);
        //值为空时也获取随机码
        if (TextUtils.isEmpty(agentNum) || Pattern.compile("^[0]+$").matcher(agentNum).matches()) {
            agentNum = AppUtil.getUniquePsuedoID();
        }

        this.device = AppUtil.getDevice(app);
        this.system = AppUtil.getSystem(app);
        this.appVersion = AppUtil.getVersionName(app);
        this.channel = AppUtil.getMetaData(app, "UMENG_CHANNEL");


        //Http Headers 信息
        headerMap = new HashMap<>();
        //系统
        headerMap.put("os", "android");
        //系统版本
        headerMap.put("osVersion", String.valueOf(Build.VERSION.SDK_INT));
        //手机序列号
        headerMap.put("sn", agentNum);
        //设备
        headerMap.put("device", device);
        //渠道
        headerMap.put("channel", channel);
        //应用版本
        headerMap.put("appVersion", appVersion);

    }


    public String getAppVersion() {
        return appVersion;
    }

    public String getAgentNum() {
        return agentNum;
    }

    public String getDevice() {
        return device;
    }

    public String getSystem() {
        return system;
    }

    public String getChannel() {
        return channel;
    }


    public Map<String, String> getHeaderMap() {
        return headerMap;
    }
}
