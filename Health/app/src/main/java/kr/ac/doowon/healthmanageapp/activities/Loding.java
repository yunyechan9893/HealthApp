package kr.ac.doowon.healthmanageapp.activities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.models.LoginTokenRequest;
import kr.ac.doowon.healthmanageapp.models.JsonResponese;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.res.Prefs;
import retrofit2.Call;

import retrofit2.Response;

public class Loding extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loding);

        // 파이어베이스에서 url을 받아온다는 가정
        List<String> urls = Arrays.asList(
                "https://firebasestorage.googleapis.com/v0/b/carscratch.appspot.com/o/image%2Fimg_banner_1.png?alt=media&token=19569bed-573a-40df-87cf-9edc79b22bc2",
                "https://firebasestorage.googleapis.com/v0/b/carscratch.appspot.com/o/image%2Fimg_banner_1.png?alt=media&token=19569bed-573a-40df-87cf-9edc79b22bc2",
                "https://firebasestorage.googleapis.com/v0/b/carscratch.appspot.com/o/image%2Fimg_banner_1.png?alt=media&token=19569bed-573a-40df-87cf-9edc79b22bc2",
                "https://firebasestorage.googleapis.com/v0/b/carscratch.appspot.com/o/image%2Fimg_banner_1.png?alt=media&token=19569bed-573a-40df-87cf-9edc79b22bc2"
        );

        loadBannerImageFromFierbase(urls).observeOn(Schedulers.io())
                .subscribe(() -> {
                    Log.i("성공?", "성공?");
                    Prefs prefs = Prefs.getInstance(getApplicationContext());
                    String accessToken = prefs.getAccessToken();

                    requestLoginWithToken(accessToken).observeOn(AndroidSchedulers.mainThread())
                            .subscribe((aBoolean, throwable) -> {
                                Log.i("aBoolean", aBoolean.toString());
                                if(aBoolean){
                                    movePageTo(new MainFrame());
                                } else {
                                    movePageTo(new AuthenticationFrame());
                                }
                            });

                });
    }

    private Completable loadBannerImageFromFierbase(List<String> urls) {
        return Completable.fromRunnable(() -> {
            for (int i = 0; i < urls.size(); i++) {
                int finalI = i;
                Glide.with(this)
                        .asBitmap()
                        .load(urls.get(i))
                        .error(R.drawable.img_logo)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                String fileName = "banner_" + finalI;
                                saveBitmapToFile(resource, fileName);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
            }
        }).subscribeOn(Schedulers.io());
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

    private Single<Boolean> requestLoginWithToken(String accessToken) {
        return Single.fromCallable(() -> {
            if (accessToken == null) {
                return false;
            }

            LoginTokenRequest loginTokenRequest = new LoginTokenRequest(accessToken);
            Call<JsonResponese> call = RetrofitClient.getApiService().tokenLogin(loginTokenRequest);
            Response<JsonResponese> response = call.execute();

            if (response.isSuccessful() && response.body().getMessage() == 200) {
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "토큰 가져오기에 실패했습니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }).subscribeOn(Schedulers.io());
    }

    private void movePageTo(Activity nextActivity) {
        Intent intent = new Intent(this, nextActivity.getClass());
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}