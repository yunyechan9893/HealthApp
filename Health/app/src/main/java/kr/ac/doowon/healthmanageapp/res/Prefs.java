package kr.ac.doowon.healthmanageapp.res;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static final String prefNm = "mPref";
    private static final String ACCESS_TOKEN_NAME = "access_token";
    private static final String REFRESH_TOKEN_NAME = "refresh_token";
    private static SharedPreferences prefs;
    private static Prefs instance;
    private Context mContext;

    private Prefs(Context context) {
        mContext = context;
        prefs = context.getSharedPreferences(prefNm, Context.MODE_PRIVATE);
    }

    public static synchronized Prefs getInstance(Context context) {
        if (instance == null) {
            instance = new Prefs(context);
        }
        return instance;
    }

    public String getAccessToken() {
        return prefs.getString(ACCESS_TOKEN_NAME, null);
    }

    public void setAccessToken(String value) {
        prefs.edit().putString(ACCESS_TOKEN_NAME, value).apply();
    }

    public String getRefreshToken() {
        return prefs.getString(REFRESH_TOKEN_NAME, null);
    }

    public void setRefreshToken(String value) {
        prefs.edit().putString(REFRESH_TOKEN_NAME, value).apply();
    }

    public void clearToken() {
        prefs.edit().remove(ACCESS_TOKEN_NAME).remove(REFRESH_TOKEN_NAME).apply();
    }
}
