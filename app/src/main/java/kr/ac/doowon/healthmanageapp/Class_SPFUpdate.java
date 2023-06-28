package kr.ac.doowon.healthmanageapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class Class_SPFUpdate {

    public static void SaveSharedPreferences(Context context, List<JavaBean_Management_Diet_AllAteFood> list){
        int count = 0;

        SharedPreferences dietTable = context.getSharedPreferences("Diet", Activity.MODE_PRIVATE);
        SharedPreferences.Editor dietEditor = dietTable.edit();
        dietEditor.clear().commit();

        while (list.size() > count) {

            int no = list.get(count).getNo();
            String typeOfMeal = list.get(count).getTypeOfMeal();
            String mealTime = list.get(count).getMealTime();
            String comment = list.get(count).getComment();
            String date = list.get(count).getDate();
            int share = list.get(count).getShare();
            String mealName = list.get(count).getFoodName();
            int mealAmount = list.get(count).getFoodAmount();
            int kcal = list.get(count).getKcal();
            int carbohydrate = list.get(count).getCarbohydrate();
            int protein = list.get(count).getProtein();
            int saturatedFat = list.get(count).getSaturatedFat();
            int polyunsaturatedFat = list.get(count).getPolyunsaturatedFat();
            int unsaturatedFat = list.get(count).getUnsaturatedFat();
            int cholesterol = list.get(count).getCholesterol();
            int dietaryFiber = list.get(count).getDietaryFiber();
            int sodium = list.get(count).getSodium();
            count++;

            dietEditor.putInt("no" + count, no);
            dietEditor.putString("typeOfMeal" + count, typeOfMeal);
            dietEditor.putString("mealTime" + count, mealTime);
            dietEditor.putString("comment" + count, comment);
            dietEditor.putString("date" + count, date);
            dietEditor.putInt("share" + count, share);
            dietEditor.putString("mealName" + count, mealName);
            dietEditor.putInt("mealAmount" + count, mealAmount);
            dietEditor.putInt("kcal" + count, kcal);
            dietEditor.putInt("carbohydrate" + count, carbohydrate);
            dietEditor.putInt("protein" + count, protein);
            dietEditor.putInt("saturatedFat" + count, saturatedFat);
            dietEditor.putInt("polyunsaturatedFat" + count, polyunsaturatedFat);
            dietEditor.putInt("unsaturatedFat" + count, unsaturatedFat);
            dietEditor.putInt("cholesterol" + count, cholesterol);
            dietEditor.putInt("dietaryFiber" + count, dietaryFiber);
            dietEditor.putInt("sodium" + count, sodium);
            dietEditor.commit();
        }
        dietEditor.putInt("itemCount", list.size());
        dietEditor.commit();
    }
}
