package kr.ac.doowon.healthmanageapp.fragments.management;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.activities.DietFrame;
import kr.ac.doowon.healthmanageapp.databinding.FragmentManagementDietMainBinding;

public class Diet extends Fragment {
    FragmentManagementDietMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_management_diet_main, container,false);




        return binding.getRoot();
    }

}
