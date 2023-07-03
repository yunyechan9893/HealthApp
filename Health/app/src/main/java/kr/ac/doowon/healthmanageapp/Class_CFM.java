package kr.ac.doowon.healthmanageapp;

import android.app.Activity;

public class Class_CFM {
    private Fragment_Management_main fragmentManagementMain;
    private Fragment_Management_Diet fragmentManagementDiet;
    private Fragment_Management_Diet_Add fragmentManagementDietAdd;

    // FragmentManagementMain setter,getter
    public void setFragmentManagementMain(Fragment_Management_main fragmentManagementMain){
        this.fragmentManagementMain = fragmentManagementMain;
    }

    public Fragment_Management_main getFragmentManagementMain() {
        return fragmentManagementMain;
    }

    // FragmentManagementDiet setter,getter
    public void setFragmentManagementDiet(Fragment_Management_Diet fragmentManagementDiet) {
        this.fragmentManagementDiet = fragmentManagementDiet;
    }

    public Fragment_Management_Diet getFragmentManagementDiet() {
        return fragmentManagementDiet;
    }

    // FragmentManagementDietAdd setter,getter
    public void setFragmentManagementDietAdd(Fragment_Management_Diet_Add fragmentManagementDietAdd) {
        this.fragmentManagementDietAdd = fragmentManagementDietAdd;
    }

    public Fragment_Management_Diet_Add getFragmentManagementDietAdd() {
        return fragmentManagementDietAdd;
    }
}
