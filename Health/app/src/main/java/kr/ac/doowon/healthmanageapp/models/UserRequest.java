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

    @SerializedName("user_nickname")
    @Expose
    private String userNickname;

    @SerializedName("user_phone")
    @Expose
    private String userPhone;

    @SerializedName("user_code")
    @Expose
    private String userCode;

    public void setUserId( String userId ){
        this.userId = userId;
    }
    public void setUserPwd( String userPwd ){
        this.userPwd = userPwd;
    }
    public void setUserName( String userName ){
        this.userName = userName;
    }
    public void setUserNickname( String userNickname ){
        this.userNickname = userNickname;
    }
    public void setUserPhone( String userPhone ){
        this.userPhone = userPhone;
    }

    public void setUserCode( String userCode ){
        this.userCode = userCode;
    }

}
