package kr.ac.doowon.healthmanageapp.adapters;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public FragmentPagerAdapter(FragmentActivity activity){
        super(activity);
    }

    public void setFragmentsAll(List<Fragment> fragments){
        this.fragments = fragments;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount",String.valueOf(fragments.size()));
        return fragments.size();
    }

    public FragmentPagerAdapter addFragment(Fragment fragment) {
        fragments.add(fragment);
        notifyItemInserted(fragments.size() - 1);

        Log.i("addFragment",fragment.toString());

        return this;
    }

    public List<Fragment> getFragments(){
        return fragments;
    }

    public void removeFragment() {
        fragments.remove(fragments.size() - 1);

        notifyItemRemoved(fragments.size());
    }
}
