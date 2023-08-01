package kr.ac.doowon.healthmanageapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import java.util.Arrays;
import java.util.List;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.ListDietMainBinding;


public class AteFoodBaseAdapter extends BaseAdapter {
    ListDietMainBinding binding;
    private List<Integer> listViewItemList = Arrays.asList(1,0,1,0);


    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Log.i("error","여긴 출력되니?");
            binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_diet_main, parent, false);
            convertView = binding.getRoot();
        }
        Log.i("error","여긴 출력되니?");

        return convertView;
    }
}
