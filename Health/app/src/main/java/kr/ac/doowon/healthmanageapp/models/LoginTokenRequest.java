package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginTokenRequest {
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public LoginTokenRequest( String accessToken ){
        this.accessToken  = accessToken;
    }
}

