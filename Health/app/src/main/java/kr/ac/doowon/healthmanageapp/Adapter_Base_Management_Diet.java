package kr.ac.doowon.healthmanageapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter_Base_Management_Diet extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<JavaBean_Management_Diet_AllAteFood> sample;

    public Adapter_Base_Management_Diet(Context context, List<JavaBean_Management_Diet_AllAteFood> data) {
        mContext = context;
        sample = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View converView, ViewGroup viewGroup) {
        View view = mLayoutInflater.inflate(R.layout.list_diet_main,null);
        TextView tvTypeOfMeal = view.findViewById(R.id.tvTypeOfMeal);
        TextView tvKcal = view.findViewById(R.id.tvKcal);
        TextView tvProtein = view.findViewById(R.id.tvProtein);
        TextView tvComment = view.findViewById(R.id.tvComment);

        tvTypeOfMeal.setText(sample.get(position).getTypeOfMeal());
        tvKcal.setText(sample.get(position).getKcal() + "kcal");
        tvProtein.setText(sample.get(position).getProtein()+"g");
        tvComment.setText(sample.get(position).getComment());
        System.out.println("????");

        return view;
    }
}
