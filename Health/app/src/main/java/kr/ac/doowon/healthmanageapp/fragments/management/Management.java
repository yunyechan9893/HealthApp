package kr.ac.doowon.healthmanageapp.fragments.management;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import kr.ac.doowon.healthmanageapp.activities.DietFrame;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.FragmentManagementMainBinding;

public class Management extends Fragment implements View.OnClickListener {
    private DietFrame diet;

    private FragmentManagementMainBinding binding;

    @Override
    public void onClick(View view) {
        if (binding.btnDiet.equals(view)){
            Intent intent = new Intent(getActivity(), DietFrame.class);
            startActivity(intent);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_management_main, container,false);

        binding.btnDiet.setOnClickListener(this);

        diet = new DietFrame();


        return binding.getRoot();
    }
}
