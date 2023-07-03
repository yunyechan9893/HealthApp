package kr.ac.doowon.healthmanageapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import kr.ac.doowon.healthmanageapp.models.LoginTokenRequest;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.res.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_00_Loding extends AppCompatActivity {
    Class nextActivity;
    private static Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_01_main);


        prefs = Prefs.getInstance(getApplicationContext());
        String accessToken = prefs.getAccessToken();
        nextActivity = Activity_01_Login.class;

        if (accessToken!=null){
            LoginTokenRequest loginTokenRequest = new LoginTokenRequest(accessToken);
            Call call = RetrofitClient.getApiService().tokenLogin(loginTokenRequest);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    nextActivity = Activity_04_Main_Frame.class;
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Activity_00_Loding.this, nextActivity);
                startActivity(intent);
            }
        }, 2500);
    }

}