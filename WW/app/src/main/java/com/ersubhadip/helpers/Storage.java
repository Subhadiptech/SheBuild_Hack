package com.ersubhadip.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Storage {
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;
    private Context context;

    public Storage(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        edit = prefs.edit();
    }

    public boolean isNewUser() {
        return prefs.getBoolean("isNewUser", true);
    }

    public void setIsNewUser(boolean b) {
        edit.putBoolean("isNewUser", b);
        edit.apply();
    }

    public String getNumber() {
        return prefs.getString("isAuthenticated", "");
    }

    public void setNumber(String b) {
        edit.putString("isAuthenticated", b);
        edit.apply();
    }

    public String getLat() {
        return prefs.getString("lat", "");
    }

    public void setLat(String b) {
        edit.putString("lat", b);
        edit.apply();
    }

    public String getLong() {
        return prefs.getString("long", "");
    }

    public void setLong(String b) {
        edit.putString("long", b);
        edit.apply();
    }

}