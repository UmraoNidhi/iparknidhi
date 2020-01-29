package com.wemsuser.app.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BannerPreferenceUtil {
    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static String gettireHomeBanner(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.TIRES, "");
    }

    public static void settireHomeBanner(Context context, String useremail) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.TIRES, useremail).apply();
    }
    public static String getLOCKHomeBanner(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.LOCKOUT, "");
    }

    public static void setLOCKHomeBanner(Context context, String useremail) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.LOCKOUT, useremail).apply();
    }
    public static String getTowingHomeBanner(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.TOWING, "");
    }

    public static void setTowingHomeBanner(Context context, String useremail) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.TOWING, useremail).apply();
    }
    public static String getBatteryHomeBanner(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.BATTERY, "");
    }

    public static void setBatteryHomeBanner(Context context, String useremail) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.BATTERY, useremail).apply();
    }
    public static String getCarBodyHomeBanner(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.CARBODAY, "");
    }

    public static void setCarBodyHomeBanner(Context context, String useremail) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.CARBODAY, useremail).apply();
    }
    public static String getMechanicsHomeBanner(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.MECHANICS, "");
    }

    public static void setMechanicsHomeBanner(Context context, String useremail) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.MECHANICS, useremail).apply();
    }


}
