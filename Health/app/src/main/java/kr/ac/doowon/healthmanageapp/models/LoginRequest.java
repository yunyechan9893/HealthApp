package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    public LoginRequest(String id, String pwd){
        this.userId = id;
        this.userPwd = pwd;
    }

    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("user_pwd")
    @Expose
    private String userPwd;


}
