package kr.ac.doowon.healthmanageapp.view_model;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kr.ac.doowon.healthmanageapp.adapters.MyFragmentStateAdapter;

public class NavigationVIewModel extends ViewModel {
    MutableLiveData<MyFragmentStateAdapter> liveDataAdapter = new MutableLiveData<>();

    public NavigationVIewModel initAdpter(FragmentActivity activity){
        liveDataAdapter.setValue(new MyFragmentStateAdapter(activity));

        return this;
    }

    public NavigationVIewModel addFragment(Fragment fragment){
        liveDataAdapter.getValue().addFragment(fragment);

        return this;
    }


    public MyFragmentStateAdapter getFragmentAdapter(){
        return liveDataAdapter.getValue();
    }

}
