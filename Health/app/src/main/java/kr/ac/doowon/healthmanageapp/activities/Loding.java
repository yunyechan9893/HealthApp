package kr.ac.doowon.healthmanageapp.activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                        nextActivity = MainFrame.class;
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
        }

        List<String> urls = Arrays.asList(
                "https://firebasestorage.googleapis.com/v0/b/carscratch.appspot.com/o/image%2Fimg_banner_1.png?alt=media&token=19569bed-573a-40df-87cf-9edc79b22bc2",
                "https://firebasestorage.googleapis.com/v0/b/carscratch.appspot.com/o/image%2Fimg_banner_1.png?alt=media&token=19569bed-573a-40df-87cf-9edc79b22bc2",
                "https://firebasestorage.googleapis.com/v0/b/carscratch.appspot.com/o/image%2Fimg_banner_1.png?alt=media&token=19569bed-573a-40df-87cf-9edc79b22bc2",
                "https://firebasestorage.googleapis.com/v0/b/carscratch.appspot.com/o/image%2Fimg_banner_1.png?alt=media&token=19569bed-573a-40df-87cf-9edc79b22bc2"
        );
        for (int i=0; i<urls.size();i++){
            int finalI = i;
            Glide.with(this)
                    .asBitmap()
                    .load(urls.get(i))
                    .override(300,300)
                    .error(R.drawable.img_logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            String fileName = "banner_"+ finalI;
                            saveBitmapToFile(resource, fileName);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        }


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Loding.this, nextActivity);
            startActivity(intent);
        }, 2500);
    }

    private void saveBitmapToFile(Bitmap bitmap, String filename){
        File file = new File(this.getFilesDir(), filename);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}