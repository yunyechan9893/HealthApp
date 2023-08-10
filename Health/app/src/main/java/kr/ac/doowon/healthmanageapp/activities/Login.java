package kr.ac.doowon.healthmanageapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.gson.internal.LinkedTreeMap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.database.AppDatabase;
import kr.ac.doowon.healthmanageapp.database.AteFood;
import kr.ac.doowon.healthmanageapp.database.Diet;
import kr.ac.doowon.healthmanageapp.database.TargetKcal;
import kr.ac.doowon.healthmanageapp.databinding.ActivityLoginBinding;
import kr.ac.doowon.healthmanageapp.models.LoginResponse;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.models.UserRequest;
import kr.ac.doowon.healthmanageapp.res.Prefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Fragment implements View.OnClickListener {
    private static Prefs prefs;
    Call<LoginResponse> call;
    Disposable disposable;
    ActivityLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_login, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = Prefs.getInstance(getActivity());

        binding.btnRegist.setOnClickListener(this::onClick);
        binding.btnLogin.setOnClickListener(this::onClick);
        binding.btnFindIdPwd.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        if(binding.btnRegist.equals(view)){
            AuthenticationFrame authenticationFrame = (AuthenticationFrame) getActivity();
            authenticationFrame.moveFragment("Signup");

        }else if(binding.btnLogin.equals(view)) {
            String id = binding.edId.getText().toString();
            String Password = binding.edPwd.getText().toString();
            String hashPwd = encrypt(Password);

            // 백엔드에 전송할 Login body 구성
            UserRequest loginReq = new UserRequest();
            loginReq.setUserId( id );
            loginReq.setUserPwd( hashPwd );

            call = RetrofitClient.getApiService().login(loginReq);

            call.enqueue(new Callback<LoginResponse>(){
                //콜백 받는 부분
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse resp = response.body();


                    int msg = resp.getMessage();

                    if (msg==200) {
                        String accessToken = resp.getAccessToken();
                        String refreshToken = resp.getRefreshToken();
                        List dietInfo = resp.getDietInfo();

                        List ateFood = resp.getAteFoodList();
                        List targetKcals = resp.getTargetKcal();
                        Log.i("dietInfo",dietInfo.toString() );
                        Log.i("dietInfo",dietInfo.getClass().getName() );


                        prefs.setAccessToken(accessToken);
                        prefs.setRefreshToken(refreshToken);

                        List<Diet> lisDiet = new ArrayList<>();
                        for (Object diet : dietInfo){
                            LinkedTreeMap dietMap = (LinkedTreeMap) diet;
                            int no            = (int) Double.parseDouble( dietMap.get("no").toString() );
                            String typeOfMeal = (String) dietMap.get("type_of_meal");
                            String mealTime   = (String) dietMap.get("meal_time");
                            String comment    = (String) dietMap.get("comment");
                            String dateTime   = (String) dietMap.get("date");
                            String url        = (String) dietMap.get("url");

                            Diet dietTable = new Diet();

                            lisDiet.add(
                                    dietTable.setDietId(no)
                                            .setTypeOfMeal(typeOfMeal)
                                            .setMealTime(typeOfMeal)
                                            .setTypeOfMeal(mealTime)
                                            .setComment(comment)
                                            .setTypeOfMeal(typeOfMeal)
                                            .setDateTime(dateTime)
                                            .setUrl(url)
                            );
                        }

                        List<AteFood> lisAteFood = new ArrayList<>();
                        for (Object food : ateFood){
                            LinkedTreeMap foodMap = (LinkedTreeMap) food;
                            int diet_no       = (int) Double.parseDouble( foodMap.get("diet_no").toString() );
                            String name       = (String) foodMap.get("name");
                            int amount        = (int) Double.parseDouble( foodMap.get("amount").toString() );
                            int kcal          = (int) Double.parseDouble( foodMap.get("kcal").toString() );
                            int carbohydrate  = (int) Double.parseDouble( foodMap.get("carbohydrate").toString() );
                            int protein       = (int) Double.parseDouble( foodMap.get("protein").toString() );
                            int fat           = (int) Double.parseDouble( foodMap.get("fat").toString() );
                            int sugars        = (int) Double.parseDouble( foodMap.get("sugars").toString() );
                            int sodium        = (int) Double.parseDouble( foodMap.get("sodium").toString() );
                            int cholesterol   = (int) Double.parseDouble( foodMap.get("cholesterol").toString() );
                            int saturated_fat = (int) Double.parseDouble( foodMap.get("saturated_fat").toString() );
                            int trans_fat     = (int) Double.parseDouble( foodMap.get("trans_fat").toString() );


                            AteFood ateFoodTable = new AteFood();
                            ateFoodTable.setDietNo(diet_no)
                                    .setFoodName(name)
                                    .setAmount(amount)
                                    .setKcal(kcal)
                                    .setCarbohydrate(carbohydrate)
                                    .setProtein(protein)
                                    .setFat(fat)
                                    .setSodium(sodium);

                            lisAteFood.add(ateFoodTable);
                        }

                        List<TargetKcal> lisTargetKcal = new ArrayList<>();
                        for (Object targetKcal : targetKcals){
                            LinkedTreeMap targetKcalMap = (LinkedTreeMap) targetKcal;
                            String date = targetKcalMap.get("date").toString();
                            int kcal = (int) Double.parseDouble( targetKcalMap.get("target_kcal").toString() );

                            TargetKcal targetKcalTable = new TargetKcal();
                            targetKcalTable.setKcal(kcal)
                                    .setDate(date);


                            lisTargetKcal.add(targetKcalTable);
                        }

                        AppDatabase db = AppDatabase.getDatabase(getActivity());

                        Completable deleteAllCompletable = db.dietDAO().deleteTable();

                        Diet[] diets = lisDiet.toArray(new Diet[0]);
                        Completable insertDietsCompletable = db.dietDAO().insert(diets);

                        AteFood[] ateFoods = lisAteFood.toArray(new AteFood[0]);
                        Completable insertAteFoodsCompletable = db.ateFoodDAO().insert(ateFoods);

                        TargetKcal[] targetKcals1 = lisTargetKcal.toArray(new TargetKcal[0]);
                        Completable insertTargetKcalsCompletable = db.targetKcalDAO().insert(targetKcals1);

                        disposable = deleteAllCompletable
                                .andThen(
                                        Completable.mergeArray(
                                                insertDietsCompletable,
                                                insertTargetKcalsCompletable
                                                )
                                ) // deleteAllCompletable 다음 실행
                                .andThen(insertAteFoodsCompletable) // insertDietsCompletable 다음 실행
                                .subscribeOn(Schedulers.io()) // 입출력 스케줄러 사용
                                .observeOn(Schedulers.io()) // UI에 영향을 안주기에 입출력 스케줄러 사용
                                .subscribe(
                                        () -> {
                                            // 모든 작업이 완료되었을 때 수행할 동작
                                            Log.i("성공", "");
                                            AuthenticationFrame authenticationFrame = (AuthenticationFrame) getActivity();
                                            authenticationFrame.moveMainPage();
                                        },
                                        throwable -> {
                                            // 에러 처리
                                            Log.i("에러", throwable.toString());
                                        }
                                );
                    }
                    else{
                        Toast.makeText(getActivity(), "아이디 혹은 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    System.out.println(t);
                }
            });

        }

        else if(binding.btnFindIdPwd.equals(view)){
        }
    }


    public String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());

            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    // 바이트를 해쉬화한다.
    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();

    }

}
