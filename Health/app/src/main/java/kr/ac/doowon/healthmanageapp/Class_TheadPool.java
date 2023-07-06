package kr.ac.doowon.healthmanageapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import java.util.List;

public class Class_TheadPool {

    private Class_MemberDO memberDO;
    private Message msg;
    private Handler handler;
    private Bundle bundle;

    public Class_TheadPool(Handler handler){
        this.handler = handler;
        memberDO = new Class_MemberDO();
        bundle = new Bundle();
    }

    private void PutStringMethod(String method){
        bundle.putString("Method", method);
    }

    public void AutoLogin_DeleteThread1(String id, String code){
        new Thread(()->{
            msg = handler.obtainMessage();
            System.out.println("pool//AutoLogin_DeleteThread Start");
            PutStringMethod("Logout");
            memberDO.AutoLogin_Delete(id,code);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }).start();
    }

    public void HomeBBSelectThread1(int category){
        new Thread(()->{
            msg = handler.obtainMessage();
            System.out.println("pool//HomeBBSelectThread Start");
            PutStringMethod("BBSSetting");
            bundle.putString("Title",memberDO.HomeBBSelect(category));
            bundle.putInt("Category",category);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }).start();
    }

    // 2022.11.11
    // 이건 다음에 수정하자
    private List<JavaBean_Management_Diet_AllAteFood> dietList;

    public void GetDietResultTread(String id){
        new Thread(()->{
            System.out.println("GetDietResultTread start");
            msg = handler.obtainMessage();
            PutStringMethod("GetDietResultTread");
            dietList = memberDO.GetDietResultSet(id);

            msg.setData(bundle);
            handler.sendMessage(msg);
        }).start();
    }

    public List GetDietList(){
        return dietList;
    }

    private List<JavaBean_Management_Diet_AllAteFood> foodList;

    public void setFoodList(String foodName){
        new Thread(()->{
            msg = handler.obtainMessage();
            PutStringMethod("setFoodList");
            foodList = memberDO.getFoodList(foodName);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }).start();
    }

    public List getFoodList(){
        return foodList;
    }

    public void registDietThread(String id, String typeOfMeal, String mealTime, String comment, String date, int shere){
        new Thread(()->{
            msg = handler.obtainMessage();
            PutStringMethod("registDietThread");
            int no = memberDO.RegistDiet(id, typeOfMeal, mealTime, comment, date, 0);
            bundle.putInt("no",no);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }).start();
    }

    public void registDietFoodThread(int no,List<JavaBean_Management_Diet_AllAteFood> foodList){
        new Thread(()->{
            msg = handler.obtainMessage();
            PutStringMethod("registDietFood");
            for(int i = 0; i < foodList.size(); i++) {
                memberDO.RegistAteFood(no, foodList.get(i).getFoodName());
            }
            msg.setData(bundle);
            handler.sendMessage(msg);
        }).start();
    }

    public void TargetKcalInsertThread(String id, String date, int targetKcal){
        new Thread(()->{
            msg = handler.obtainMessage();
            PutStringMethod("setTargetKcal");
            bundle.putInt("RESULT",memberDO.TargetKcalInsert(id,date,targetKcal));
            msg.setData(bundle);
            handler.sendMessage(msg);
        }).start();
    }

    public void TargetKcalSelectThread(String id, String date){
        new Thread(()->{
            msg = handler.obtainMessage();
            PutStringMethod("getTargetKcal");
            bundle.putInt("targetKcal",memberDO.TargetKcalSelect(id,date));
            msg.setData(bundle);
            handler.sendMessage(msg);
        }).start();
    }
}