package kr.ac.doowon.healthmanageapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTabHost;

public class Fragment_Introduce_Main extends Fragment {
    public enum Introduce {위치,트레이너, 기구, 시설, 이벤트}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_introduce_frame,container,false);
        FragmentTabHost tabHost = rootView.findViewById(android.R.id.tabhost);

        tabHost.setup(getActivity().getApplicationContext(),getChildFragmentManager(),android.R.id.tabcontent);

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec(Introduce.위치.toString()); // 구분자
        tabSpec1.setIndicator("위치"); // 탭 이름
        tabHost.addTab(tabSpec1, Fragment_Introduce_Location.class, null);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec(Introduce.트레이너.toString());
        tabSpec2.setIndicator("트레이너");
        tabHost.addTab(tabSpec2, Fragment_Introduce_Trainer.class, null);

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec(Introduce.기구.toString());
        tabSpec3.setIndicator("기구");
        tabHost.addTab(tabSpec3, z_UnderConstructorActivity.class, null);

        TabHost.TabSpec tabSpec4 = tabHost.newTabSpec(Introduce.시설.toString());
        tabSpec4.setIndicator("시설");
        tabHost.addTab(tabSpec4, z_UnderConstructorActivity.class, null);

        TabHost.TabSpec tabSpec5 = tabHost.newTabSpec(Introduce.이벤트.toString());
        tabSpec4.setIndicator("이벤트");
        tabHost.addTab(tabSpec4, z_UnderConstructorActivity.class, null);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {


            }
        });

        return rootView;
    }
}
