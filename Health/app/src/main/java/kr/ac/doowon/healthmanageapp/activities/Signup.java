package kr.ac.doowon.healthmanageapp.activities;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.databinding.ActivitySignupBinding;
import kr.ac.doowon.healthmanageapp.models.JsonResponese;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.models.UserRequest;
import kr.ac.doowon.healthmanageapp.res.KeyPatten;

import kr.ac.doowon.healthmanageapp.util.MyTextWatch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends Fragment implements View.OnClickListener {
    private static class ValidationStatus  {
        private final HashMap<View, Boolean> validationMap = new HashMap<>();

        public void setValidationStatus(View focusView, boolean val){
            validationMap.put(focusView, val);
        }

        public boolean isValidation(){
            for (Map.Entry<View, Boolean> entry:
                    validationMap.entrySet()) {
                if(!entry.getValue()){
                    entry.getKey().requestFocus();
                    return false;
                }
            }

            return true;
        }
    }

    private ActivitySignupBinding binding;
    ValidationStatus validationStatus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_signup,container,false);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        validationStatus = new ValidationStatus();
        validationStatus.setValidationStatus(binding.btnIdCheck, false);
        validationStatus.setValidationStatus(binding.edPwd, false);
        validationStatus.setValidationStatus(binding.edName, false);
        validationStatus.setValidationStatus(binding.edPwdCheck, false);
        validationStatus.setValidationStatus(binding.btnNickNameCheck, false);
        validationStatus.setValidationStatus(binding.btnPhoneNumberCheck, false);
        validationStatus.setValidationStatus(binding.edPhoneNumber, false);

        KeyPatten keyPatten = new KeyPatten();
        binding.edId.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        binding.edName.setFilters(new InputFilter[]{keyPatten.Kor});
        binding.edPwd.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        binding.edPwdCheck.setFilters(new InputFilter[]{keyPatten.AlphaNum});

        // ID키를 입력할 때마다 중복확인 추가로 하도록 만듦
        binding.edId.addTextChangedListener((MyTextWatch) (editable) ->
            validationStatus.setValidationStatus(binding.btnIdCheck, false));

        binding.edPwd.addTextChangedListener((MyTextWatch) (editable) ->{
            validationStatus.setValidationStatus(binding.edPwd, performPasswordValidCheck());
            validationStatus.setValidationStatus(binding.edPwdCheck, performPasswordEqulCheck());
        });

        binding.edPwdCheck.addTextChangedListener((MyTextWatch) (editable) ->{
            validationStatus.setValidationStatus(binding.edPwd, performPasswordValidCheck());
            validationStatus.setValidationStatus(binding.edPwdCheck, performPasswordEqulCheck());
        });

        binding.edName.addTextChangedListener((MyTextWatch) (editable) ->
                validationStatus.setValidationStatus(binding.edName, performNameInputCheck()));

        // Nickname 키를 입력할 때마다 중복확인 추가로 하도록 만듦
        binding.edNickName.addTextChangedListener((MyTextWatch) (editable) -> {
            String nickname = binding.edNickName.getText().toString();
            performNicknameValidCheck(nickname);
            validationStatus.setValidationStatus(binding.btnNickNameCheck, false);
        });


        binding.btnCancel.setOnClickListener(this);
        binding.btnIdCheck.setOnClickListener(this);
        binding.btnNickNameCheck.setOnClickListener(this);
        binding.btnPhoneNumberCheck.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (binding.btnCancel.equals(v)){
            AuthenticationFrame authenticationFrame = (AuthenticationFrame) getActivity();
            assert authenticationFrame != null;
            authenticationFrame.moveFragment("Login");
        } else if (binding.btnIdCheck.equals(v)){
            String id = binding.edId.getText().toString();
            TextView txIdCheck = binding.txIdCheck;

            if (isIdValid(id)){
                validationStatus.setValidationStatus(binding.btnIdCheck, checkDuplicateId(id, txIdCheck));
            } else {
                validationStatus.setValidationStatus(binding.btnIdCheck, false);
            }
        } else if (binding.btnNickNameCheck.equals(v)){
            String nickname = binding.edNickName.getText().toString();
            TextView nickNameCheck = binding.txNicknameCheck;

            if (nickname.isEmpty()) {
                showErrorMessage(nickNameCheck, "닉네임을 입력하세요");
                validationStatus.setValidationStatus(binding.btnNickNameCheck, false);

            }else{
                hideErrorMessage(nickNameCheck);
                validationStatus.setValidationStatus(binding.btnNickNameCheck, checkDuplicateNickname(nickname));
            }
        } else if (binding.btnPhoneNumberCheck.equals(v)){
            int phoneNumberLength = binding.edPhoneNumber.getText().toString().length();
            TextView phoneNumberCheck = binding.txPhoneCheck;

            if (phoneNumberLength < 10 || phoneNumberLength > 11 ) {
                showErrorMessage(phoneNumberCheck, "정확한 핸드폰 번호를 입력해주세요");
                validationStatus.setValidationStatus(binding.btnPhoneNumberCheck, false);
                return;
            }

            validationStatus.setValidationStatus(binding.btnPhoneNumberCheck, true);
            hideErrorMessage(phoneNumberCheck);
        } else if (binding.btnRegister.equals(v)) {
            if (!validationStatus.isValidation()) {
                makeToast("다시 한번 확인해주세요").show();
                return;
            }

            String id = binding.edId.getText().toString();
            String pwd = binding.edPwd.getText().toString();
            String name = binding.edName.getText().toString();
            String nickname = binding.edNickName.getText().toString();
            String phone = binding.edPhoneNumber.getText().toString();

            registerUser(id, pwd, name, nickname, phone);
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

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private boolean performPasswordValidCheck(){
        String password = binding.edPwd.getText().toString();
        int passwordLength = password.length();

        if (passwordLength < 8 || passwordLength > 20) {
            showErrorMessage(binding.txPwdCheck,"8~20자 사이를 입력해주세요.");
            return false;
        }

        if (!containsLowerCaseLetter(password)) {
            showErrorMessage(binding.txPwdCheck,"영문자를 입력해주세요.");
            return false;
        }

        hideErrorMessage(binding.txPwdCheck);
        return true;
    }

    private boolean containsLowerCaseLetter(String text) {
        for (int index = 0; index < text.length(); index++) {
            if (Character.isLowerCase(text.charAt(index))) {
                return true;
            }
        }
        return false;
    }

    private boolean performPasswordEqulCheck(){
        String password = binding.edPwd.getText().toString();
        String passwordEquelCheck = binding.edPwdCheck.getText().toString();

        if (!passwordEquelCheck.equals(password)) {
            showErrorMessage(binding.txPwdDoubleCheck, "비밀번호가 일치하지 않습니다.");
            return false;
        }

        hideErrorMessage(binding.txPwdDoubleCheck);
        return true;
    }

    private boolean performNameInputCheck(){
        if (binding.edName.getText().toString().length()== 0){
            showErrorMessage(binding.txNameCheck, "이름을 입력하세요.");
            return false;
        }

        hideErrorMessage(binding.txNameCheck);
        return true;
    }

    private boolean isIdValid(String id) {
        if (id.length() < 8 || id.length() > 20) {
            showErrorMessage(binding.txIdCheck, "8~15자 사이로 입력해주세요");
            return false;
        }

        for (char c : id.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                showErrorMessage(binding.txIdCheck, "특수문자는 사용할 수 없습니다.");
                return false;
            }
        }

        if (!containsLetter(id)) {
            showErrorMessage(binding.txIdCheck, "영문이 포함되어야 합니다.");
            return false;
        }

        return true;
    }

    private boolean containsLetter(String text) {
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDuplicateId(String id, TextView txIdCheck) {
        final AtomicBoolean isDuplicate = new AtomicBoolean(false);

        Call<JsonResponese> call = RetrofitClient.getApiService().checkDuplicateId(id);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponese> call, @NonNull Response<JsonResponese> response) {
                JsonResponese resp = response.body();
                boolean isSuccess = resp != null && resp.isSuccess();

                if (isSuccess) {
                    showErrorMessage(txIdCheck, "사용 가능한 아이디입니다");
                    isDuplicate.set(false);
                } else {
                    showErrorMessage(txIdCheck, "아이디가 중복되었습니다");
                    isDuplicate.set(true); // 설정
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonResponese> call, @NonNull Throwable t) {
                isDuplicate.set(false); // 설정
            }
        });

        if (!isDuplicate.get()){
            return false;
        }

        hideErrorMessage(txIdCheck);
        return isDuplicate.get();
    }

    private void performNicknameValidCheck(String nickname){
        if (nickname.length() > 1 && nickname.length() < 10) {
            binding.edNickName.setText("1~10 자리로 입력해주세요");
        }
    }
    private boolean checkDuplicateNickname(String nickname) {
        if (nickname.length() < 1 || nickname.length() >10){
            binding.edNickName.setText("1~10 자리로 입력해주세요");
            return false;
        }

        AtomicBoolean isDuplicate = new AtomicBoolean(false);

        Call<JsonResponese> call = RetrofitClient.getApiService().checkDuplicateNickname(nickname);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponese> call, @NonNull Response<JsonResponese> response) {
                JsonResponese resp = response.body();
                boolean isSuccess = resp != null && resp.isSuccess();

                if (isSuccess) {
                    showErrorMessage(binding.txNicknameCheck, "사용 가능한 닉네임입니다");
                } else {
                    showErrorMessage(binding.txNicknameCheck, "닉네임이 중복되었습니다");
                }
                isDuplicate.set(isSuccess);
            }

            @Override
            public void onFailure(@NonNull Call<JsonResponese> call, @NonNull Throwable t) {
                showErrorMessage(binding.txNicknameCheck, "오류가 발생했습니다");
                isDuplicate.set(false);
            }
        });
        return isDuplicate.get();
    }

    private void registerUser(String id, String pwd, String name, String nickname, String phone) {
        String hashPwd = encrypt(pwd);

        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(id);
        userRequest.setUserPwd(hashPwd);
        userRequest.setUserName(name);
        userRequest.setUserNickname(nickname);
        userRequest.setUserPhone(phone);

        Call<JsonResponese> call = RetrofitClient.getApiService().signup(userRequest);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<JsonResponese> call, @NonNull Response<JsonResponese> response) {
                makeToast("회원가입에 성공하셨습니다").show();
                AuthenticationFrame authenticationFrame = (AuthenticationFrame) getActivity();

                assert authenticationFrame != null;
                authenticationFrame.moveFragment("Login");
            }

            @Override
            public void onFailure(@NonNull Call<JsonResponese> call, @NonNull Throwable t) {
                makeToast("회원가입에 실패했습니다").show();
            }
        });
    }


    private void showErrorMessage(TextView textView, String message) {
        textView.setText(message);
        textView.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage(TextView textView) {
        textView.setVisibility(View.INVISIBLE);

    }

    private Toast makeToast(String contents){
        return Toast.makeText(getContext(), contents, Toast.LENGTH_SHORT);
    }


}