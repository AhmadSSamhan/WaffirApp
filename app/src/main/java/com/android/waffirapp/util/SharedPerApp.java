package com.android.waffirapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by A.Samhan on 12/31/2017.
 */

public class SharedPerApp {
    private Context mContext;
    private String mStrKey;
    private String mStrValue;

    public SharedPerApp(Context context) {
        this.mContext = context;
    }

    public SharedPerApp setStrKey(String strKey) {
        this.mStrKey = strKey;
        return this;
    }

    public SharedPerApp setStrValue(String strValue) {
        this.mStrValue = strValue;
        return this;
    }

    public String getSharedPref() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString(mStrKey, mStrValue);
    }

    public void setSharedPref() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(mStrKey, mStrValue);
        editor.apply();
    }
    public void clearSharedPrefByKey(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(mStrKey);
        editor.apply();
    }

    public void clearAllSharedPrefByKey() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
