package kr.ac.doowon.healthmanageapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyFragmentStateAdapter extends FragmentStateAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public MyFragmentStateAdapter(FragmentActivity activity){
        super(activity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public MyFragmentStateAdapter addFragment(Fragment fragment) {
        fragments.add(fragment);

        return this;
    }

    public void removeFragment() {
        fragments.remove(fragments.size() - 1);

        notifyItemRemoved(fragments.size());
    }

}
