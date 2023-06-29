package kr.ac.doowon.healthmanageapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import kr.ac.doowon.healthmanageapp.Class_Tool.MakeToast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class Activity_01_Login extends Activity {
     class HandlerManager extends Handler {
        Bundle bundle;

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            bundle = msg.getData();
            String methodName = bundle.getString("Method","E");

            if(methodName == "Login"){
                boolean isLogin = bundle.getBoolean("isLogin");
                String id = bundle.getString("Id","E");
                String code = bundle.getString("Code","E");

                if (id != "E" || code != "E") FirstEnterAutoLogin(id,code);
                Login(isLogin, id);
            }
            else if(methodName == "AutoLogin"){
                    String id = bundle.getString("Id","E");
                    AutoLoginCheck(bundle.getBoolean("isAutoLogin"), id);

            }
        }
    }

    Button btnRegister, btnLogin, btnFindIdPwd;
    Switch scAutoLogin;
    EditText edId, edPwd;
    Intent intent;

    Drawable drawRegist;
    Class_TheadPool theadPool;
    HandlerManager handlerManager;
    Handler handler;
    SharedPreferences userInfo;
    SharedPreferences.Editor userInfoEditor;

    class ClickEvent implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btnRegist){
                intent = new Intent(Activity_01_Login.this, Activity_02_Register.class);
                startActivity(intent);
            }
            if(view.getId() == R.id.btnLogin) {
                String strId = edId.getText().toString();
                String strPwd = edPwd.getText().toString();
                if (!scAutoLogin.isChecked()) theadPool.LoginThead_AutoNoCheck(strId,strPwd);
                else theadPool.LoginThead_AutoCheck(strId,strPwd);
            }
            if(view.getId() == R.id.btnFindIdPwd){
                intent = new Intent(Activity_01_Login.this, z_Test.class);
                startActivity(intent);
            }

        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_02_login);

        Awake();
        Update();
    }

    private  void Awake()
    {
        UserInfoSave("");
        FindByViewIdes();
        handler = new HandlerManager();
        handlerManager = new HandlerManager();
        theadPool = new Class_TheadPool(handler);
        sendAutoLoginInfo();
        //버튼 색상 변경 코드
        drawRegist = getResources().getDrawable(R.drawable.register_border2);
        drawRegist.setColorFilter(0xff99cc00, PorterDuff.Mode.SRC_ATOP);
        btnRegister.setBackgroundDrawable(drawRegist);
    }

    private void Update(){
        btnRegister.setOnClickListener(new ClickEvent());
        btnLogin.setOnClickListener(new ClickEvent());
        btnFindIdPwd.setOnClickListener(new ClickEvent());
    }

    private void FindByViewIdes()
    {
        btnRegister = findViewById(R.id.btnRegist);
        btnLogin = findViewById(R.id.btnLogin);
        btnFindIdPwd = findViewById(R.id.btnFindIdPwd);
        edId = findViewById(R.id.edId);
        edPwd = findViewById(R.id.edPwd);
        scAutoLogin = findViewById(R.id.scAutoLogin);
    }

    private void sendAutoLoginInfo(){
        SharedPreferences sp = getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
        theadPool.AutoLoginThead(sp.getString("ID","E"), sp.getString("CODE", "E"));
    }

    private void AutoLoginCheck(boolean isAutoLogin, String id){

        System.out.println("Activity//AutoLoginCheck start");
        System.out.println("Activity//isAutoLogin : " + isAutoLogin);

        if (isAutoLogin){
            UserInfoSave(id);
            intent = new Intent(this, Activity_04_Main_Frame.class);
            startActivity(intent);
            return;
        }

        MakeToast.Unit(this, "다시 로그인 해주세요.");
    }

    private void Login(Boolean isLogin, String id){
        btnLogin.setBackgroundColor(Color.YELLOW);
        if(isLogin) {
            UserInfoSave(id);
            intent = new Intent(Activity_01_Login.this, Activity_04_Main_Frame.class);
            startActivity(intent);
            return;
        }

        MakeToast.Unit(getApplicationContext(),"아이디 혹은 비밀번호가 잘못되었습니다.");
    }

    private void FirstEnterAutoLogin(String id, String code){
            SharedPreferences auto = getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
            SharedPreferences.Editor autoLoginEdit = auto.edit();
            autoLoginEdit.putString("ID",id);
            autoLoginEdit.putString("CODE",code);
            autoLoginEdit.commit();

            System.out.println("sharedP ID : "+auto.getString("ID","ERROR"));
            System.out.println("sharedP CODE : "+auto.getString("CODE","ERROR"));
    }

    private void UserInfoSave(String id){
        userInfo = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        userInfoEditor = userInfo.edit();
        userInfoEditor.putString("Id", id);
        userInfoEditor.commit();

        System.out.println("userInfo ID : "+userInfo.getString("Id","ERROR"));
    }
}
