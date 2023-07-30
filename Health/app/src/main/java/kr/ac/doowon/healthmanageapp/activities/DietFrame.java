package kr.ac.doowon.healthmanageapp.activities;

import static android.view.Gravity.AXIS_CLIP;

import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import kr.ac.doowon.healthmanageapp.fragments.management.DietAdd;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.FragmentPagerAdapter;
import kr.ac.doowon.healthmanageapp.databinding.ActivityDietBinding;
import kr.ac.doowon.healthmanageapp.fragments.management.Diet;
import kr.ac.doowon.healthmanageapp.fragments.management.MyDatePicker;
import kr.ac.doowon.healthmanageapp.view_model.NavigationVIewModel;

public class DietFrame extends AppCompatActivity implements View.OnClickListener {

    ActivityDietBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diet);

        NavigationVIewModel navigationVIewModel = new NavigationVIewModel();

        FragmentPagerAdapter adapter = navigationVIewModel.getFragmentAdapter();
        if (adapter==null){
            adapter = navigationVIewModel.initAdpter(this)
                    .addFragment(new Diet())
                    .addFragment(new DietAdd())
                    .getFragmentAdapter();
        }

        binding.dietViewpager.setAdapter(adapter);
        binding.dietViewpager.setUserInputEnabled(false);

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

            binding.dietViewpager.setCurrentItem(1);
        }
        else if (binding.ibtnDate.equals(v)){
            DialogFragment dialog = new MyDatePicker();
            dialog.show(this.getSupportFragmentManager(), "tag");
        }
        else  if (binding.ibtnHome.equals(v)){
            moveDietActivity();
        }
    }

    public void moveDietActivity(){
        binding.ibtnTargetKcal.setVisibility(View.VISIBLE);
        binding.ibtnDietRegister.setVisibility(View.VISIBLE);
        binding.ibtnDate.setVisibility(View.VISIBLE);
        binding.dietViewpager.setCurrentItem(0);
    }
}
