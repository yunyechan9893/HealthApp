package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import kr.ac.doowon.healthmanageapp.database.AteFood;
import kr.ac.doowon.healthmanageapp.database.Diet;

public class DietResponese {
    @SerializedName("message")
    private int message;

    @SerializedName("diet_info")
    private List<LinkedTreeMap<String, ?>> diet_info;

    @SerializedName("food_list")
    private List<LinkedTreeMap<String, ?>> food_list;

    public DietResponese() {
    }


    public int getMessage() {
        return message;
    }

    //링크드 뭐시기로 반환되니깐 타입 잡고 여기서 해체시도
    public List<LinkedTreeMap<String, ?>> getDietInfo() { return diet_info;}
    public List<LinkedTreeMap<String, ?>> getAteFoodList() {
        return food_list;
    }
}