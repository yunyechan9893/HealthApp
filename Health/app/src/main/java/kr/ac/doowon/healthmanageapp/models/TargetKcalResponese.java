package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TargetKcalResponese {
    @SerializedName("message")
    private int message;
    @SerializedName("target_kcal")
    private List target_kcal;

    public int getMessage() {
        return message;
    }

    public List getTargetKcal() {
        return target_kcal;
    }
}
