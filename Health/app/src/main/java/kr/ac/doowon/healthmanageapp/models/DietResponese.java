package kr.ac.doowon.healthmanageapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kr.ac.doowon.healthmanageapp.database.AteFood;
import kr.ac.doowon.healthmanageapp.database.Diet;

public class DietResponese {
    @SerializedName("message")
    private int message;

    @SerializedName("diet_info")
    private List diet_info;

    @SerializedName("food_list")
    private List<AteFood> food_list;

    public DietResponese() {
    }


    public int getMessage() {
        return message;
    }
    public List getDietInfo() { return diet_info;}
    public List<AteFood> getAteFoodList() {
        return food_list;
    }
}