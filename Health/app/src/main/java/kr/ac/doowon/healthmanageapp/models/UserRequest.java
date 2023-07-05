package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRequest {
    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("user_pwd")
    @Expose
    private String userPwd;

    @SerializedName("user_name")
    @Expose
    private String userName;

    @SerializedName("user_phone")
    @Expose
    private String userPhone;

    public void setUserId( String userPwd ){
        this.userPwd = userPwd;
    }

    public void setUserPwd( String userName ){
        this.userName = userName;
    }

    public void setUserName( String userName ){
        this.userName = userName;
    }

    public void setUserPhone( String userPhone ){
        this.userPhone = userPhone;
    }

}
