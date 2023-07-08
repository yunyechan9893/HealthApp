package kr.ac.doowon.healthmanageapp.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.models.LoginTokenRequest;
import kr.ac.doowon.healthmanageapp.models.JsonResponese;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.res.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Loding extends AppCompatActivity {
    Class nextActivity;
    private static Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_01_main);


        prefs = Prefs.getInstance(getApplicationContext());
        String accessToken = prefs.getAccessToken();
        nextActivity = Login.class;

        if (accessToken!=null){
            LoginTokenRequest loginTokenRequest = new LoginTokenRequest(accessToken);
            Call call = RetrofitClient.getApiService().tokenLogin(loginTokenRequest);
            call.enqueue(new Callback<JsonResponese>() {
                @Override
                public void onResponse(Call<JsonResponese> call, Response<JsonResponese> response) {
                    if (response.body().getMessage()==200)
                        nextActivity = Main_Frame.class;
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Loding.this, nextActivity);
                startActivity(intent);
            }
        }, 2500);
    }

}