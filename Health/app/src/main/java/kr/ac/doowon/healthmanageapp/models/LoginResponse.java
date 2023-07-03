package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private int message;
    @SerializedName("refresh_token")
    private String refresh_token;
    @SerializedName("access_token")
    private String access_token;


    public boolean isSuccess() {
        return success;
    }

    public int getMessage() {
        return message;
    }
    public String getAccessToken() {
        return access_token;
    }
    public String getRefreshToken() {
        return refresh_token;
    }
}