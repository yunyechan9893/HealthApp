package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private int message;
    @SerializedName("refresh_token")
    private String refresh_token;
    @SerializedName("access_token")
    private String access_token;

    @SerializedName("diet_info")
    private List diet_info;

    @SerializedName("food_list")
    private List food_list;


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
    public List getDietInfo() {
        return diet_info;
    }
    public List getAteFoodList() {
        return food_list;
    }
}