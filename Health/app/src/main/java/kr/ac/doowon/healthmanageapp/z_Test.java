package kr.ac.doowon.healthmanageapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class z_Test extends AppCompatActivity {
    class TestHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle =  msg.getData();
            boolean isTestThread = bundle.getBoolean("TestThread");
            }
        }

    TextView tv1;
    z_Class_Test class_test;
    List<JavaBean_Management_Diet_AllAteFood> list;
    z_Test2 fragmentTest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_activity_test);
        tv1 = findViewById(R.id.tv1);
        z_Test2 fragmentTest = new z_Test2();



        tv1.setText("프레그먼트이동");
        tv1.setOnClickListener((view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.flTest,fragmentTest).commit();
        }));

    }
}
