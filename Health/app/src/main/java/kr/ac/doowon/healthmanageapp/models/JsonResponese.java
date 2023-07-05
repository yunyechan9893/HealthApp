package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.SerializedName;

public class JsonResponese {
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private int message;

    public boolean isSuccess() {
        return success;
    }

    public int getMessage() {
        return message;
    }
}
