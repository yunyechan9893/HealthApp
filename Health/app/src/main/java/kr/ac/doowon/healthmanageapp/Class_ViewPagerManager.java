package kr.ac.doowon.healthmanageapp;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import java.util.List;
import me.relex.circleindicator.CircleIndicator3;

public class Class_ViewPagerManager {
    FragmentStateAdapter fragAdapter;

    List<Integer> imgList;
    int ImageCount;

    public Class_ViewPagerManager(ViewPager2 viewPg, CircleIndicator3 indicator, FragmentActivity fa, List<Integer> imageList){

        this.imgList = imageList;
        fragAdapter = new Adapter_FragementState_Home_EventImage(fa,imgList);
        viewPg.setAdapter(fragAdapter);
        indicator.setViewPager(viewPg);
    }

    public void Setting(ViewPager2 viewPg, CircleIndicator3 indicator){
        ImageCount = GetImageCount();

        indicator.createIndicators(ImageCount,0);

        viewPg.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        viewPg.setCurrentItem(ImageCount, true);
        viewPg.setOffscreenPageLimit(ImageCount);
    }

    private int GetImageCount(){
        return imgList.size();
    }
}
