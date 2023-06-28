package kr.ac.doowon.healthmanageapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.ac.doowon.healthmanageapp.Class_TheadPool;

public class z_Class_Test {

    public z_Class_Test(z_Test.TestHandler handlerTest){
        this.handlerTest = handlerTest;
        bundle = new Bundle();
    }

    private static String ip = "172.30.1.97";
    private static String port = "2934";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String database = "HealthApp";
    private static String username = "YYC";
    private static String passward = "9893";
    private static String url =  "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+database;
    private static Connection connection = null;
    private static boolean permission;

    Handler handlerTest;
    Bundle bundle;
    Message msg;

    public void Connect(){
        try{
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username, passward);
            Log.v("connerting","success");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Log.v("connerting","Error");
        }
    }

    public void Close(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    TextView tv;

    public z_Class_Test(){
    }


    List<JavaBean_Management_Diet_AllAteFood> list;

    public void TestThread()  {
        new Thread(() -> {
            System.out.println("pool//DubleCheckNickname Start");
            msg = handlerTest.obtainMessage();
            bundle.putBoolean("TestThread",true);

            String id = "dpcks98931";
            list = GetDietResultSet(id);

            msg.setData(bundle);
            handlerTest.sendMessage(msg);

        }).start();
    }

    public List GetList(){
        return list;
    }

    public List GetDietResultSet(String id){
        System.out.println("DO//AutoLogin_Delete Start");
        String sqlProc = "{Call Proc_Diet(?)}";
        Connect();

        List<JavaBean_Management_Diet_AllAteFood> list2;

        try {
            CallableStatement callableStatement = connection.prepareCall(sqlProc);
            callableStatement.setString(1,id);
            ResultSet rs = callableStatement.executeQuery();
            list2 = ConvertResultSetTOList(rs);
            callableStatement.close();
            Close();

            return list2;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List ConvertResultSetTOList(ResultSet rs){
        List<JavaBean_Management_Diet_AllAteFood> list = new ArrayList<>();
        try {
            while (rs.next()){
                int no = rs.getInt(1);
                String id = rs.getString(2);
                String typeOfMeal = rs.getString(3);
                String mealTime = rs.getString(4);
                String comment = rs.getString(5);
                String date = rs.getString(6);
                int share  = rs.getInt(7);
                String mealName  = rs.getString(8);
                int mealAmount = rs.getInt(9);
                int kcal = rs.getInt(10);
                int carbohydrate = rs.getInt(11);
                int protein = rs.getInt(12);
                int saturatedFat = rs.getInt(13);
                int polyunsaturatedFat = rs.getInt(14);
                int unsaturatedFat = rs.getInt(15);
                int cholesterol = rs.getInt(16);
                int dietaryFiber = rs.getInt(17);
                int sodium = rs.getInt(18);

                System.out.println(mealName);

                list.add(new JavaBean_Management_Diet_AllAteFood(no, id,typeOfMeal,mealTime,comment,date,share,mealName
                        ,mealAmount,kcal,carbohydrate,protein,saturatedFat,polyunsaturatedFat,unsaturatedFat,cholesterol,dietaryFiber,sodium));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
