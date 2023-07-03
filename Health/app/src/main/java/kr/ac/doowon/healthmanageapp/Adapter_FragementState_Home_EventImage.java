package kr.ac.doowon.healthmanageapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class Adapter_FragementState_Home_EventImage extends FragmentStateAdapter {
    public int mCount;
    private List<Integer> mImage;


    public Adapter_FragementState_Home_EventImage(FragmentActivity fa, List<Integer> mImage){
        super(fa);
        mCount = mImage.size();
        this.mImage = mImage;
    }

    public void setCount(int count){
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        int index = getRealPosition(position);

        if(index == 1) return new Fragment_Home_EventImage(mImage.get(0));
        if(index == 2) return new Fragment_Home_EventImage(mImage.get(1));
        if(index == 3) return new Fragment_Home_EventImage(mImage.get(2));
        if(index == 4) return new Fragment_Home_EventImage(mImage.get(3));
        if(index == 5) return new Fragment_Home_EventImage(mImage.get(4));
        if(index == 6) return new Fragment_Home_EventImage(mImage.get(5));
        if(index == 7) return new Fragment_Home_EventImage(mImage.get(6));
        if(index == 8) return new Fragment_Home_EventImage(mImage.get(7));
        if(index == 9) return new Fragment_Home_EventImage(mImage.get(8));
        if(index == 10) return new Fragment_Home_EventImage(mImage.get(9));

        else return  new Fragment_Home_EventImage(R.drawable.img_banner_2);
    }

    public int getRealPosition(int position){
        return position%mCount;
    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
