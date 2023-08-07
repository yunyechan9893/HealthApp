package kr.ac.doowon.healthmanageapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.MyFragmentStateAdapter;
import kr.ac.doowon.healthmanageapp.databinding.ActivityAuthenticationFrameBinding;

public class AuthenticationFrame extends AppCompatActivity {
    ActivityAuthenticationFrameBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication_frame);

        MyFragmentStateAdapter fragmentStateAdapter = new MyFragmentStateAdapter(this);
        fragmentStateAdapter.addFragment(new Login()).addFragment(new Signup());

        binding.viewPager.setAdapter(fragmentStateAdapter);
        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.setCurrentItem(0);
    }

    public void moveFragment(String fragmentName){
        if (fragmentName.equals("Login")) binding.viewPager.setCurrentItem(0);
        else binding.viewPager.setCurrentItem(1);
    }

    public void moveMainPage(){
        Intent intent = new Intent(this, MainFrame.class);
        startActivity(intent);
    }
}
