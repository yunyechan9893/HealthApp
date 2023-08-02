package kr.ac.doowon.healthmanageapp.activities;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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

public class Signup extends Fragment {

    private class ValidationStatus {
        private HashMap<String, Boolean> validationMap = new HashMap<>();

        public void setValidationStatus(String key, boolean val){
            validationMap.put(key, val);
        }

        public boolean isValidation(){
            for (boolean status:
                    validationMap.values()) {
                if(!status){
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
        validationStatus.setValidationStatus("isIdDoubleCheck", false);

        validationStatus.setValidationStatus("isNicknameDoubleCheck", false);
        validationStatus.setValidationStatus("isCertificationNumberSendCheck", false);
        validationStatus.setValidationStatus("isCertificationNumberValidCheck", false);

        KeyPatten keyPatten = new KeyPatten();
        binding.edId.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        binding.edName.setFilters(new InputFilter[]{keyPatten.Kor});
        binding.edPwd.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        binding.edPwdCheck.setFilters(new InputFilter[]{keyPatten.AlphaNum});


        binding.edPwd.addTextChangedListener((MyTextWatch) (editable) ->
            validationStatus.setValidationStatus("isPwdValidCheck",performPasswordValidCheck()));


        binding.edPwdCheck.addTextChangedListener((MyTextWatch) (editable) ->
            validationStatus.setValidationStatus("isPwdValidCheck",performPasswordEqulCheck()));

        binding.edName.addTextChangedListener((MyTextWatch) (editable) ->
            validationStatus.setValidationStatus("isNameInputCheck",performNameInputCheck()));

        binding.btnCancel.setOnClickListener(v -> {
            // mainActivity로 이동
        });

        binding.btnIdCheck.setOnClickListener(v -> {
            String id = binding.edId.getText().toString();
            TextView txIdCheck = binding.txIdCheck;

            if (isIdValid(id)){
                validationStatus.setValidationStatus("isIdDoubleCheck",checkDuplicateId(id, txIdCheck));
            } else {
                validationStatus.setValidationStatus("isIdDoubleCheck",false);
            }
        });

        binding.btnNickNameCheck.setOnClickListener(v -> {
            String nickname = binding.edNickName.getText().toString();

            if (nickname.isEmpty()) {
                showErrorMessage(binding.txNicknameCheck, "닉네임을 입력하세요");
                return;
            }

            checkDuplicateNickname(nickname);
        });

        binding.btnPhoneNumberCheck.setOnClickListener(v -> {
            int phoneNumberLength = binding.edPhoneNumber.getText().toString().length();
            TextView phoneNumberCheck = binding.txPhoneCheck;

            if (phoneNumberLength < 7 || phoneNumberLength > 8 ) {
                showErrorMessage(phoneNumberCheck, "정확한 핸드폰 번호를 입력해주세요");
                return; // false
            }

            hideErrorMessage(phoneNumberCheck);
            return; // true
        });

        binding.btnRegister.setOnClickListener(v -> {
            for (int i = 0; i < checkList.size(); i++) {
                if (!checkList.get(i)) {
                    showErrorAndFocusInput(i);
                    return;
                }
            }

            String id = binding.edId.getText().toString();
            String pwd = binding.edPwd.getText().toString();
            String name = binding.edName.getText().toString();
            String nickname = binding.edNickName.getText().toString();
            String phone = binding.edPhoneNumber.getText().toString();

            String hashPwd;
            try {
                hashPwd = encrypt(pwd);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            UserRequest userRequest = new UserRequest();
            userRequest.setUserId(id);
            userRequest.setUserPwd(hashPwd);
            userRequest.setUserName(name);
            userRequest.setUserNickname(nickname);
            userRequest.setUserPhone(phone);

            Call<JsonResponese> call = RetrofitClient.getApiService().signup(userRequest);
            call.enqueue(new Callback<JsonResponese>() {
                @Override
                public void onResponse(Call<JsonResponese> call, Response<JsonResponese> response) {
                    toast.setText("회원가입에 성공했습니다");
                    toast.show();
                    onBackPressed();
                    return false;
                }
                @Override
                public void onFailure(Call<JsonResponese> call, Throwable t) {
                    toast.setText("회원가입에 실패했습니다");
                    toast.show();
                }
            });
        });
    }

    private void showErrorAndFocusInput(int index) {
        TextView textView;
        EditText editText;
        switch (index) {
            case 0:
                textView = findViewById(R.id.txIdCheck);
                editText = findViewById(R.id.edId);
                break;
            case 1:
                textView = findViewById(R.id.txPwdCheck);
                editText = findViewById(R.id.edPwd);
                break;
            case 2:
                textView = findViewById(R.id.txPwdDoubleCheck);
                editText = findViewById(R.id.edPwd);
                break;
            case 3:
                textView = findViewById(R.id.txNameCheck);
                editText = findViewById(R.id.edName);
                break;
            case 4:
                textView = findViewById(R.id.txPhoneCheck);
                editText = findViewById(R.id.edPhoneNumber);
                break;
            case 5:
                textView = findViewById(R.id.txNicknameCheck);
                editText = findViewById(R.id.edNickName);
                break;
            default:
                return;
        }
        textView.setVisibility(View.VISIBLE);
        editText.requestFocus();

    }

    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
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
        call.enqueue(new Callback<JsonResponese>() {
            @Override
            public void onResponse(Call<JsonResponese> call, Response<JsonResponese> response) {
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
            public void onFailure(Call<JsonResponese> call, Throwable t) {
                System.out.println(t);
                isDuplicate.set(false); // 설정
            }
        });

        if (!isDuplicate.get()){
            return false;
        }

        hideErrorMessage(txIdCheck);
        return isDuplicate.get();
    }


    private boolean checkDuplicateNickname(String nickname) {
        AtomicBoolean isDuplicate = new AtomicBoolean(false);

        Call<JsonResponese> call = RetrofitClient.getApiService().checkDuplicateNickname(nickname);
        call.enqueue(new Callback<JsonResponese>() {
            @Override
            public void onResponse(Call<JsonResponese> call, Response<JsonResponese> response) {
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
            public void onFailure(Call<JsonResponese> call, Throwable t) {
                System.out.println(t);
                showErrorMessage(binding.txNicknameCheck, "오류가 발생했습니다");
                isDuplicate.set(false);
            }
        });
        return isDuplicate.get();
    }


    private void showErrorMessage(TextView textView, String message) {
        textView.setText(message);
        textView.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage(TextView textView) {
        textView.setVisibility(View.INVISIBLE);
    }


}