package kr.ac.doowon.healthmanageapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import kr.ac.doowon.healthmanageapp.Interface_TextWatcher;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.models.JsonResponese;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.models.UserRequest;
import kr.ac.doowon.healthmanageapp.res.KeyPatten;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends Activity {

    private ArrayList<Boolean> checkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_03_register);

        checkList = new ArrayList<>(Arrays.asList(false, false, false, false, false, false));

        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnIdCheck = findViewById(R.id.btnIdCheck);
        Button btnNickNameCheck = findViewById(R.id.btnNickNameCheck);
        Button btnPhoneNumberCheck = findViewById(R.id.btnPhoneNumberCheck);

        EditText edId = findViewById(R.id.edId);
        EditText edPwd = findViewById(R.id.edPwd);
        EditText edPwdCheck = findViewById(R.id.edPwdCheck);
        EditText edName = findViewById(R.id.edName);
        EditText edNickName = findViewById(R.id.edNickName);
        EditText edPhoneNumber = findViewById(R.id.edPhoneNumber);

        TextView txPwdDoubleCheck = findViewById(R.id.txPwdDoubleCheck);
        TextView txIdCheck = findViewById(R.id.txIdCheck);
        TextView txNameCheck = findViewById(R.id.txNameCheck);
        TextView txPhoneCheck = findViewById(R.id.txPhoneCheck);
        TextView txPwdCheck = findViewById(R.id.txPwdCheck);

        KeyPatten keyPatten = new KeyPatten();
        edId.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        edName.setFilters(new InputFilter[]{keyPatten.Kor});
        edPwd.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        edPwdCheck.setFilters(new InputFilter[]{keyPatten.AlphaNum});

        Drawable draw = getResources().getDrawable(R.drawable.register_border2);
        draw.setColorFilter(0xFFDF00, PorterDuff.Mode.SRC_ATOP);
        btnRegister.setBackgroundDrawable(draw);

        edId.addTextChangedListener((Interface_TextWatcher) (charSequence, i, i1, i2) -> checkList.set(0, false));

        edPwd.addTextChangedListener((Interface_TextWatcher) (charSequence, i, i1, i2) -> {
            String strPwd = edPwd.getText().toString();

            int pwdLength = strPwd.length();
            if (pwdLength < 8 || pwdLength > 20) {
                txPwdCheck.setText("8~20자 사이를 입력해주세요.");
                txPwdCheck.setVisibility(View.VISIBLE);
                return;
            } else {
                txPwdCheck.setVisibility(View.INVISIBLE);
            }

            checkList.set(1, false);
            for (int index = 0; index < pwdLength; index++) {
                char c = strPwd.charAt(index);
                if (Character.isLetter(c)) {
                    checkList.set(1, true);
                    break;
                }
            }

            if (!checkList.get(1)) {
                txPwdCheck.setText("영문자를 입력해주세요.");
                txPwdCheck.setVisibility(View.VISIBLE);
                return;
            }

            txPwdCheck.setVisibility(View.INVISIBLE);
        });

        edPwdCheck.addTextChangedListener((Interface_TextWatcher) (charSequence, i, i1, i2) -> {
            String strPwd = edPwd.getText().toString();

            if (strPwd.equals(edPwdCheck.getText().toString())) {
                txPwdDoubleCheck.setVisibility(View.INVISIBLE);
                checkList.set(2, true);
            } else {
                txPwdDoubleCheck.setText("비밀번호가 일치하지 않습니다.");
                txPwdDoubleCheck.setVisibility(View.VISIBLE);
                checkList.set(2, false);
            }
        });

        edName.addTextChangedListener((Interface_TextWatcher) (charSequence, i, i1, i2) ->{
            boolean isName = i1!=0;
            txNameCheck.setVisibility(isName ?  View.INVISIBLE:View.VISIBLE);
            checkList.set(3, isName);
        });

        Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        });

        btnIdCheck.setOnClickListener(v -> {
            String id = edId.getText().toString();
            txIdCheck.setVisibility(View.INVISIBLE);

            if (id.length() < 8 || id.length() > 20) {
                toast.setText("8~15자 사이로 입력해주세요.");
                toast.show();
                return;
            }

            boolean alphabatCheck = false;
            for (char c : id.toCharArray()) {
                if (Character.isLetter(c)) {
                    alphabatCheck = true;
                }

                if (!Character.isLetterOrDigit(c)) {
                    toast.setText("특수문자는 사용할 수 없습니다.");
                    toast.show();
                    return;
                }
            }

            if (!alphabatCheck) {
                toast.setText("영문이 포함되어야 합니다.");
                toast.show();
                return;
            }

            Call<JsonResponese> call = RetrofitClient.getApiService().checkDuplicateId(id);
            call.enqueue(new Callback<JsonResponese>() {
                @Override
                public void onResponse(Call<JsonResponese> call, Response<JsonResponese> response) {
                    JsonResponese resp = response.body();
                    boolean isSuccess = resp.isSuccess();

                    if (isSuccess) {
                        toast.setText("사용가능한 아이디입니다");
                    }else {
                        toast.setText("아이디가 중복되었습니다");
                    }
                    checkList.set(0, isSuccess);
                    toast.show();
                }

                @Override
                public void onFailure(Call<JsonResponese> call, Throwable t) {
                    System.out.println(t);
                }
            });
        });

        btnNickNameCheck.setOnClickListener( v -> {
            String nickname = edNickName.getText().toString();

            Call<JsonResponese> call = RetrofitClient.getApiService().checkDuplicateNickname(nickname);
            call.enqueue(new Callback<JsonResponese>() {
                @Override
                public void onResponse(Call<JsonResponese> call, Response<JsonResponese> response) {
                    JsonResponese resp = response.body();
                    boolean isSuccess = resp.isSuccess();

                    if (isSuccess) {
                        toast.setText("사용가능한 닉네임입니다");
                    }else {
                        toast.setText("닉네임이 중복되었습니다");
                    }
                    checkList.set(5, isSuccess);
                    toast.show();
                }

                @Override
                public void onFailure(Call<JsonResponese> call, Throwable t) {
                    System.out.println(t);
                }
            });
        });

        btnPhoneNumberCheck.setOnClickListener(v -> {
            if (edPhoneNumber.length() > 8) {
                checkList.set(4, true);
                txPhoneCheck.setVisibility(View.INVISIBLE);
            } else {
                txPhoneCheck.setVisibility(View.VISIBLE);
            }
        });

        btnRegister.setOnClickListener(v -> {
            for (int i = 0; i < checkList.size(); i++) {
                if (!checkList.get(i)) {
                    showErrorAndFocusInput(i);
                    return;
                }
            }

            String id = edId.getText().toString();
            String pwd = edPwd.getText().toString();
            String name = edName.getText().toString();
            String nickname = edNickName.getText().toString();
            String phone = edPhoneNumber.getText().toString();

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
}