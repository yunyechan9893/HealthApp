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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.database.AppDatabase;
import kr.ac.doowon.healthmanageapp.database.AteFood;
import kr.ac.doowon.healthmanageapp.database.Diet;
import kr.ac.doowon.healthmanageapp.database.TargetKcal;
import kr.ac.doowon.healthmanageapp.databinding.ActivityLoginBinding;
import kr.ac.doowon.healthmanageapp.models.DietResponese;
import kr.ac.doowon.healthmanageapp.models.LoginRequest;
import kr.ac.doowon.healthmanageapp.models.LoginResponse;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.models.TargetKcalResponese;
import kr.ac.doowon.healthmanageapp.res.Prefs;
public class Login extends Fragment implements View.OnClickListener {
    private static Prefs prefs;
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

        binding.btnRegist.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.btnFindIdPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (binding.btnRegist.equals(view)) {
            moveToSignupFragment();
        } else if (binding.btnLogin.equals(view)) {
            performLogin();
        }
    }

    private void moveToSignupFragment() {
        AuthenticationFrame authenticationFrame = (AuthenticationFrame) getActivity();
        authenticationFrame.moveFragment("Signup");
    }

    private void performLogin() {
        String id = binding.edId.getText().toString();
        String password = binding.edPwd.getText().toString();
        String hashedPassword = encrypt(password);

        LoginRequest loginRequest = new LoginRequest(id, hashedPassword);

        Single<LoginResponse> loginSingle = RetrofitClient.getApiService().login(loginRequest);
        Single<DietResponese> dietSingle = RetrofitClient.getApiService().getDiet(id);
        Single<TargetKcalResponese> targetKcalSingle = RetrofitClient.getApiService().getTargetKcal(id);

        Single<Map<String, List>> combinedSingle = Single.zip(
                loginSingle,
                dietSingle,
                targetKcalSingle,
                (loginResponse, dietResponse, targetKcalResponse) -> createResponseMap(id, loginResponse, dietResponse, targetKcalResponse)
        );

        combinedSingle
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMapCompletable(stringListMap -> processResponseAndDatabase(stringListMap))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onLoginComplete,
                        throwable -> onLoginError(throwable)
                );
    }

    private Map<String, List> createResponseMap(String id, LoginResponse loginResponse, DietResponese dietResponse, TargetKcalResponese targetKcalResponse) {
        Map<String, List> map = new HashMap<>();

        try {
            if (loginResponse.getMessage() == 200) {
                String accessToken = loginResponse.getAccessToken();
                String refreshToken = loginResponse.getRefreshToken();

                prefs.setAccessToken(accessToken);
                prefs.setRefreshToken(refreshToken);

                map.put("diets", dietResponse.getMessage() == 200 ?
                        adaptLinkedTreeMapToDietsList(dietResponse.getDietInfo()) : new ArrayList<>());

                map.put("ate_foods", dietResponse.getMessage() == 200 ?
                        adaptLinkedTreeMapToAteFoodsList(dietResponse.getAteFoodList()) : new ArrayList<>());

                map.put("target_kcals", targetKcalResponse.getMessage() == 200 ?
                        handleTargetKcal(targetKcalResponse.getTargetKcal()) : new ArrayList<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("1", e.toString());
        }

        return map;
    }

    private Completable processResponseAndDatabase(Map<String, List> stringListMap) {
        List<Diet> dietList = stringListMap.get("diets");
        List<AteFood> ateFoodList = stringListMap.get("ate_foods");
        List<TargetKcal> targetKcalList = stringListMap.get("target_kcals");

        AppDatabase db = AppDatabase.getDatabase(getActivity());
        Completable deleteAllCompletable = db.dietDAO().deleteTable();

        if (!dietList.isEmpty()) {
            Completable insertDietsCompletable = db.dietDAO().insert(dietList.toArray(new Diet[0]));
            Completable insertAteFoodsCompletable = db.ateFoodDAO().insert(ateFoodList.toArray(new AteFood[0]));
            deleteAllCompletable = deleteAllCompletable.andThen(insertDietsCompletable).andThen(insertAteFoodsCompletable);
        }

        if (!targetKcalList.isEmpty()) {
            Completable insertTargetKcalsCompletable = db.targetKcalDAO().insert(targetKcalList.toArray(new TargetKcal[0]));
            deleteAllCompletable = deleteAllCompletable.andThen(insertTargetKcalsCompletable);
        }

        if (dietList.isEmpty() && targetKcalList.isEmpty()) {
            return Completable.fromAction(() -> moveToMainPage());
        }

        return deleteAllCompletable;
    }

    private void onLoginComplete() {
        moveToMainPage();
    }

    private void onLoginError(Throwable throwable) {
        Log.i("1", throwable.toString());
        Toast.makeText(getContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
    }

    private void moveToMainPage() {
        AuthenticationFrame authenticationFrame = (AuthenticationFrame) getActivity();
        authenticationFrame.moveMainPage();
    }

    private List<AteFood> adaptLinkedTreeMapToAteFoodsList(List<LinkedTreeMap> ateFood){
        List<AteFood> lisAteFood = new ArrayList<>();

        for (LinkedTreeMap food : ateFood) {
            int ate_food_no   = (int) Double.parseDouble(food.get("ate_food_no").toString());
            int diet_no       = (int) Double.parseDouble(food.get("diet_no").toString());
            String name       = (String) food.get("name");
            int amount        = (int) Double.parseDouble(food.get("amount").toString());
            int kcal          = (int) Double.parseDouble(food.get("kcal").toString());
            int carbohydrate  = (int) Double.parseDouble(food.get("carbohydrate").toString());
            int protein       = (int) Double.parseDouble(food.get("protein").toString());
            int fat           = (int) Double.parseDouble(food.get("fat").toString());
            int sugars        = (int) Double.parseDouble(food.get("sugars").toString());
            int sodium        = (int) Double.parseDouble(food.get("sodium").toString());
            int cholesterol   = (int) Double.parseDouble(food.get("cholesterol").toString());
            int saturated_fat = (int) Double.parseDouble(food.get("saturated_fat").toString());
            int trans_fat     = (int) Double.parseDouble(food.get("trans_fat").toString());

            AteFood ateFoodTable = new AteFood();
            ateFoodTable.setDietNo(diet_no)
                    .setNo(ate_food_no)
                    .setFoodName(name)
                    .setAmount(amount)
                    .setKcal(kcal)
                    .setCarbohydrate(carbohydrate)
                    .setProtein(protein)
                    .setFat(fat)
                    .setSodium(sodium)
                    .setSugars(sugars)
                    .setCholesterol(cholesterol)
                    .setSaturatedFat(saturated_fat)
                    .setTrans_fat(trans_fat);

            lisAteFood.add(ateFoodTable);
        }
        return lisAteFood;
    }


    private List<Diet> adaptLinkedTreeMapToDietsList(List<LinkedTreeMap> dietInfo){
        List<Diet> lisDiet = new ArrayList<>();
        for (LinkedTreeMap diet : dietInfo){
            int no            = (int) Double.parseDouble( diet.get("no").toString() );
            String typeOfMeal = (String) diet.get("type_of_meal");
            String mealTime   = (String) diet.get("meal_time");
            String comment    = (String) diet.get("comment");
            String dateTime   = (String) diet.get("date");
            String url        = (String) diet.get("url");
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
        return lisDiet;
    }

    private List<TargetKcal> handleTargetKcal(List<LinkedTreeMap> targetKcals){
        List<TargetKcal> lisTargetKcal = new ArrayList<>();
        for (LinkedTreeMap targetKcal : targetKcals){
            String date = targetKcal.get("date").toString();
            int kcal = (int) Double.parseDouble( targetKcal.get("target_kcal").toString() );

            TargetKcal targetKcalTable = new TargetKcal();
            targetKcalTable.setKcal(kcal)
                    .setDate(date);


            lisTargetKcal.add(targetKcalTable);
        }

        return lisTargetKcal;
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
}
