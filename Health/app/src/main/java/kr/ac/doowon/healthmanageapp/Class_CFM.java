package kr.ac.doowon.healthmanageapp;

import kr.ac.doowon.healthmanageapp.activities.DietFrame;
import kr.ac.doowon.healthmanageapp.fragments.management.DietAdd;
import kr.ac.doowon.healthmanageapp.fragments.management.Management;

public class Class_CFM {
    private Management fragmentManagementMain;
    private DietFrame fragmentManagementDiet;
    private DietAdd fragmentManagementDietAdd;

    // FragmentManagementMain setter,getter
    public void setFragmentManagementMain(Management fragmentManagementMain){
        this.fragmentManagementMain = fragmentManagementMain;
    }

    public Management getFragmentManagementMain() {
        return fragmentManagementMain;
    }

    // FragmentManagementDiet setter,getter
    public void setFragmentManagementDiet(DietFrame fragmentManagementDiet) {
        this.fragmentManagementDiet = fragmentManagementDiet;
    }

    public DietFrame getFragmentManagementDiet() {
        return fragmentManagementDiet;
    }

    // FragmentManagementDietAdd setter,getter
    public void setFragmentManagementDietAdd(DietAdd fragmentManagementDietAdd) {
        this.fragmentManagementDietAdd = fragmentManagementDietAdd;
    }

    public DietAdd getFragmentManagementDietAdd() {
        return fragmentManagementDietAdd;
    }
}
