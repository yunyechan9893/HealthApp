package kr.ac.doowon.healthmanageapp.fragments.introduce;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.adapters.MyFragmentStateAdapter;
import kr.ac.doowon.healthmanageapp.databinding.FragmentIntroduceFrameBinding;

public class IntroduceMainFrame extends Fragment {
    FragmentIntroduceFrameBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduce_frame, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MyFragmentStateAdapter adapter = new MyFragmentStateAdapter(getActivity());
        adapter.addFragment(new IntroduceLocation())
                .addFragment(new IntroduceTrainer())
                .addFragment(new UnderConstructor())
                .addFragment(new UnderConstructor());

        binding.viewpager.setAdapter(adapter);

        // 위치, 트레이너, 기구, 이벤트
        List<String> tabNames = Arrays.asList("위치", "트레이너", "기구", "이벤트");
        new TabLayoutMediator(binding.tabLayout, binding.viewpager,
                (tab, position) -> tab.setText(tabNames.get(position))
        ).attach();
    }
}
