package kr.ac.doowon.healthmanageapp.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import kr.ac.doowon.healthmanageapp.fragments.dialog.MyEditTextDialog;
import kr.ac.doowon.healthmanageapp.fragments.management.DietAdd;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.MyFragmentStateAdapter;
import kr.ac.doowon.healthmanageapp.databinding.ActivityDietBinding;
import kr.ac.doowon.healthmanageapp.fragments.management.Diet;
import kr.ac.doowon.healthmanageapp.fragments.management.DietDetail;
import kr.ac.doowon.healthmanageapp.fragments.dialog.MyDatePicker;
import kr.ac.doowon.healthmanageapp.view_model.NavigationVIewModel;

public class DietFrame extends AppCompatActivity implements View.OnClickListener {

    ActivityDietBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diet);

        NavigationVIewModel navigationVIewModel = new NavigationVIewModel();

        MyFragmentStateAdapter adapter = navigationVIewModel.getFragmentAdapter();
        if (adapter==null){
            adapter = navigationVIewModel.initAdpter(this)
                    .addFragment(new DietDetail())
                    .addFragment(new Diet())
                    .addFragment(new DietAdd())
                    .getFragmentAdapter();
        }

        binding.dietViewpager.setAdapter(adapter);
        binding.dietViewpager.setUserInputEnabled(false);
        binding.dietViewpager.setCurrentItem(1);

        binding.ibtnDate.setOnClickListener(this::onClick);
        binding.ibtnDietRegister.setOnClickListener(this::onClick);
        binding.ibtnHome.setOnClickListener(this::onClick);
        binding.ibtnTargetKcal.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (binding.ibtnDietRegister.equals(v)){
            binding.ibtnTargetKcal.setVisibility(View.GONE);
            binding.ibtnDietRegister.setVisibility(View.GONE);
            binding.ibtnDate.setVisibility(View.GONE);

            binding.dietViewpager.setCurrentItem(2);
        }
        else if (binding.ibtnDate.equals(v)){
            DialogFragment dialog = new MyDatePicker();
            dialog.show(this.getSupportFragmentManager(), "tag");
        }
        else  if (binding.ibtnHome.equals(v)){
            moveDietFragment();
        }
        else if (binding.ibtnTargetKcal.equals(v)){
            MyEditTextDialog dialog = new MyEditTextDialog();
            dialog.setTitle("목표 칼로리").setContentHint("목표 Kcal를 입력해주세요");
        }
    }

    public void moveDietFragment(){
        binding.ibtnTargetKcal.setVisibility(View.VISIBLE);
        binding.ibtnDietRegister.setVisibility(View.VISIBLE);
        binding.ibtnDate.setVisibility(View.VISIBLE);
        binding.dietViewpager.setCurrentItem(1);
    }

    public void moveDietDetailFragment(){
        binding.ibtnTargetKcal.setVisibility(View.GONE);
        binding.ibtnDietRegister.setVisibility(View.GONE);
        binding.ibtnDate.setVisibility(View.GONE);
        binding.dietViewpager.setCurrentItem(0);
    }
}
