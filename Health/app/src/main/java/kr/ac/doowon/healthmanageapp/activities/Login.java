package kr.ac.doowon.healthmanageapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.database.AppDatabase;
import kr.ac.doowon.healthmanageapp.database.DBHelper;
import kr.ac.doowon.healthmanageapp.database.Table;
import kr.ac.doowon.healthmanageapp.database.TableDAO;
import kr.ac.doowon.healthmanageapp.models.UserRequest;
import kr.ac.doowon.healthmanageapp.models.LoginResponse;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.res.Prefs;
import kr.ac.doowon.healthmanageapp.z_Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.Nullable;
import androidx.room.Room;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
            if(view.getId() == R.id.btnLogin) {
                String strId = edId.getText().toString();
                String strPwd = edPwd.getText().toString();

                String hashPwd;
                try {
                    hashPwd = encrypt(strPwd);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                UserRequest loginReq = new UserRequest();
                loginReq.setUserId( strId   );
                loginReq.setUserPwd( hashPwd );

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
                            intent = new Intent(Login.this, Main_Frame.class);

                            System.out.println(accessToken);
                            prefs.setAccessToken(accessToken);
                            prefs.setRefreshToken(refreshToken);
                            createDB();

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

    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }
    // 바이트를 해쉬화한다.
    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private void createDB(){
        //DBHelper helper;
        //SQLiteDatabase db;
        //helper = new DBHelper(this, "health.db", null, 1);
        //db = helper.getWritableDatabase();
        //helper.onCreate(db);

        AppDatabase db = AppDatabase.getDatabase(this);
        TableDAO.DietDAO dietDao = db.dietDAO();
        System.out.println( "데이터베이스 연결 완료" + dietDao );
        //List<Table.Diet> dietList = dietDao.get();
    }
}
