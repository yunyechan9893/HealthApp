package kr.ac.doowon.healthmanageapp.fragments.management;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.activities.DietFrame;
import kr.ac.doowon.healthmanageapp.adapters.AteFoodBaseAdapter;
import kr.ac.doowon.healthmanageapp.databinding.FragmentManagementDietMainBinding;

public class Diet extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    FragmentManagementDietMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_management_diet_main, container,false);

        AteFoodBaseAdapter baseAdapter = new AteFoodBaseAdapter();
        binding.listAteFood.setAdapter(baseAdapter);
        binding.listAteFood.setOnItemClickListener(this::onItemClick);


        return binding.getRoot();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DietFrame dietFrame = (DietFrame) getActivity();
        dietFrame.moveDietDetailFragment();
    }

    @Override
    public void onClick(View v) {
    }
}
