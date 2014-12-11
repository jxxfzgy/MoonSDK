package com.to8to.clickstream.toolbox;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by moon.zhong on 2014/12/2.
 */
public class CommonUtil {

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(
                Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return (ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "." + ((ipAddress >> 16) & 0xFF)
                + "." + (ipAddress >> 24 & 0xFF);
    }

    /**
     * 获取应用版本名称
     *
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String versionName = info.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    public static String getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT + "";
    }

    /**
     * 获取应用名称
     *
     * @return
     */
    public static String getAppName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        PackageManager packageManager = context.getPackageManager();
        String appName = packageManager.getApplicationLabel(applicationInfo).toString();
        return appName;
    }

    /**
     * 获取设备号
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        String IMEI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                .getDeviceId();
        return IMEI;
    }

    /**
     * 获取屏幕分辨率
     *
     * @return
     */
    public static String getDisplaySolution(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels + "*" + metrics.heightPixels;
    }

    /**
     * 获取网络类型
     * 1.2G，2.3G，3.4G，4.wifi, 404.Other
     *
     * @return
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return netWorkType(info);
    }

    private static int netWorkType(NetworkInfo ni) {
        if (ni != null && ni.isConnectedOrConnecting()) {
            switch (ni.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    return 4;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (ni.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            //2G
                            return 1;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            //3G
                            return 2;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            //4G
                            return 3;
                    }
            }
        }
        return 0;
    }

    /**
     * 获取运营商类型
     * 1.联通，2.移动，3.电信, 404.Other
     *
     * @return
     */
    public static int getServiceProvider(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
                //中国移动
                return 2;
            } else if (operator.equals("46001")) {
                //中国联通
                return 1;
            } else if (operator.equals("46003")) {
                //中国电信
                return 3;
            } else {
                return 4;
            }
        }
        return 0;
    }

    /**
     * 定位
     *
     * @param context
     * @return
     */
    public static String getLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.NETWORK_PROVIDER;
        Location location = locationManager.getLastKnownLocation(provider);
        return location.getLatitude() + ":" + location.getLongitude();
    }

    /**
     * 获取事件的随机Id
     *
     * @return
     */
    public static String getCurrentId() {
        String currentId;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
        Date date = new Date(System.currentTimeMillis());
        currentId = sdf.format(date) + new Random().nextInt(999);
        return currentId;
    }

    public static String getCurrentTime() {
        String currentTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:sss");
        Date date = new Date(System.currentTimeMillis());
        currentTime = sdf.format(date);
        return currentTime;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
}
