package kr.ac.doowon.healthmanageapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        binding.ibtnDate.setOnClickListener(this);
        binding.ibtnDietRegister.setOnClickListener(this);
        binding.ibtnHome.setOnClickListener(this);
        binding.ibtnTargetKcal.setOnClickListener(this);
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

            String dateString = "23.08.21 10:10:10";
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

            try {
                Date date = dateFormat.parse(dateString);
                long timeInMillis = date.getTime();

                MyDatePicker dialog = MyDatePicker.getInstance(timeInMillis);
                dialog.setPositiveClick(() -> {
                    // 날짜를 선택시 나옴
                });
                dialog.show(this.getSupportFragmentManager(), "tag");

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        else  if (binding.ibtnHome.equals(v)){
            moveDietFragment();
        }
        else if (binding.ibtnTargetKcal.equals(v)){

            // DialogFragment.show() will take care of adding the fragment
            // in a transaction.  We also want to remove any currently showing
            // dialog, so make our own transaction and take care of that here.
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            String title = "목표 칼로리";
            String contentHint = "목표 Kcal를 입력해주세요";
            MyEditTextDialog dialog = MyEditTextDialog.getInstance(title, contentHint);
            dialog.setPositiveClick(() -> {
                        // 목표칼로리 Insert, 같은 날짜에 값이 있다면 Update

            });
            dialog.setNegativeClick(() -> Log.i("1", "이게되네2?"));

            dialog.show(ft, "dialog");
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
