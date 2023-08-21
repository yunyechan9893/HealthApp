package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

public class TargetKcalResponese {
    @SerializedName("message")
    private int message;
    @SerializedName("target_kcal")
    private List<LinkedTreeMap<String, ?>> target_kcal;

    public int getMessage() {
        return message;
    }

    public List<LinkedTreeMap<String, ?>> getTargetKcal() {
        return target_kcal;
    }
}
