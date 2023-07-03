package kr.ac.doowon.healthmanageapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.ac.doowon.healthmanageapp.Activity_02_Register;
import kr.ac.doowon.healthmanageapp.Activity_04_Main_Frame;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.models.LoginRequest;
import kr.ac.doowon.healthmanageapp.models.LoginResponse;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.res.Prefs;
import kr.ac.doowon.healthmanageapp.z_Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.Nullable;

public class Login extends Activity {
    private static Prefs prefs;
    private Button btnRegister, btnLogin, btnFindIdPwd;
    EditText edId, edPwd;
    Intent intent;
    Drawable drawRegist;
    Call<LoginResponse> call;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_02_login);

        prefs = Prefs.getInstance(getApplicationContext());

        btnRegister = findViewById(R.id.btnRegist);
        btnLogin = findViewById(R.id.btnLogin);
        btnFindIdPwd = findViewById(R.id.btnFindIdPwd);
        edId = findViewById(R.id.edId);
        edPwd = findViewById(R.id.edPwd);

        //버튼 색상 변경 코드
        drawRegist = getResources().getDrawable(R.drawable.register_border2);
        drawRegist.setColorFilter(0xff99cc00, PorterDuff.Mode.SRC_ATOP);
        btnRegister.setBackgroundDrawable(drawRegist);

        btnRegister.setOnClickListener(new ClickEvent());
        btnLogin.setOnClickListener(new ClickEvent());
        btnFindIdPwd.setOnClickListener(new ClickEvent());
    }

    class ClickEvent implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.btnRegist){
                Intent intent = new Intent(Login.this, Activity_02_Register.class);
                startActivity(intent);
            }
            if(view.getId() == R.id.btnLogin) {
                String strId = edId.getText().toString();
                String strPwd = edPwd.getText().toString();
                LoginRequest loginReq = new LoginRequest(strId, strPwd);

                call = RetrofitClient.getApiService().login(loginReq);
                call.enqueue(new Callback<LoginResponse>(){
                    //콜백 받는 부분
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse resp = response.body();
                        int msg = resp.getMessage();
                        String accessToken = resp.getAccessToken();
                        String refreshToken = resp.getRefreshToken();

                        if (msg==200) {
                            intent = new Intent(Login.this, Activity_04_Main_Frame.class);

                            System.out.println(accessToken);
                            prefs.setAccessToken(accessToken);
                            prefs.setRefreshToken(refreshToken);

                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        System.out.println(t);
                    }
                });

            }

            if(view.getId() == R.id.btnFindIdPwd){
                intent = new Intent(Login.this, z_Test.class);
                startActivity(intent);
            }
        }
    }
}
