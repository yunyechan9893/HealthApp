package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("userPwd")
    @Expose
    private String userPwd;

    public LoginRequest(
            String userId,
            String userPwd
    ){
        this.userId  = userId;
        this.userPwd = userPwd;
    }
}
