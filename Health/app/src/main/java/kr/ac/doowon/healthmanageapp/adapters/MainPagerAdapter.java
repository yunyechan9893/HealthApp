package kr.ac.doowon.healthmanageapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import kr.ac.doowon.healthmanageapp.Fragment_Home_EventImage;
import kr.ac.doowon.healthmanageapp.R;

public class MainPagerAdapter extends FragmentStateAdapter {
    private ArrayList<Integer> images = new ArrayList<>();


    public MainPagerAdapter(FragmentActivity fa, ArrayList<Integer> mImage){
        super(fa);
        this.images = mImage;
    }

    @Override
    public int getItemCount(){
        return images.size();
    }

    public void addImage( int image ){
        images.add(image);
        notifyItemInserted(images.size() - 1);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {


        if(position == 1) return new Fragment_Home_EventImage(images.get(0));
        if(position == 2) return new Fragment_Home_EventImage(images.get(1));
        if(position == 3) return new Fragment_Home_EventImage(images.get(2));
        if(position == 4) return new Fragment_Home_EventImage(images.get(3));
        if(position == 5) return new Fragment_Home_EventImage(images.get(4));
        if(position == 6) return new Fragment_Home_EventImage(images.get(5));
        if(position == 7) return new Fragment_Home_EventImage(images.get(6));
        if(position == 8) return new Fragment_Home_EventImage(images.get(7));
        if(position == 9) return new Fragment_Home_EventImage(images.get(8));
        if(position == 10) return new Fragment_Home_EventImage(images.get(9));

        else return  new Fragment_Home_EventImage(R.drawable.img_banner_2);
    }



    public void addFragment(Integer image) {
        images.add(image);
        notifyItemInserted(images.size() - 1);
    }

    public void removeFragment() {
        images.remove(images.size() - 1);
        notifyItemRemoved(images.size());
    }


}
