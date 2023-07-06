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
import kr.ac.doowon.healthmanageapp.Interface_TextWatcher;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.models.JsonResponese;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.models.UserRequest;
import kr.ac.doowon.healthmanageapp.res.KeyPatten;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/* 수정할 것
 * - 하드코딩 문자열 옮기기
 * - 아이디 입력시 영어 키보드, 이름 입력시 한글 키보드 자동 변경
 * */

public class Signup extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 0 : 아이디 중복체크, 1: 비밀번호 체크, 2: 비밀번호 일치 체크, 3: 이름체크, 4: 폰 체크, 5:닉네임 체크
        ArrayList<Boolean> checkList = new ArrayList<>(Arrays.asList(false, false, false, false, false, false));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_03_register);

        /* findViewById */
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
        TextView txNicknameCheck = findViewById(R.id.txNicknameCheck);
        TextView txPhoneCheck = findViewById(R.id.txPhoneCheck);
        TextView txPwdCheck = findViewById(R.id.txPwdCheck);

        /* setFilters */
        KeyPatten keyPatten = new KeyPatten();
        edId.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        edName.setFilters(new InputFilter[]{keyPatten.Kor});
        edPwd.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        edPwdCheck.setFilters(new InputFilter[]{keyPatten.AlphaNum});

        /* 버튼 색상 변경 코드 */
        Drawable draw = getResources().getDrawable(R.drawable.register_border2);
        draw.setColorFilter(0xFFDF00, PorterDuff.Mode.SRC_ATOP);
        btnRegister.setBackgroundDrawable(draw);

        /* editText 키 입력시 자동 검사 */

        // 아이디 키 입력시 중복검사 해제
        edId.addTextChangedListener((Interface_TextWatcher) (charSequence, i, i1, i2) -> checkList.set(0, false));
        //비밀번호 규격 체크
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
        // 비밀번호 일치 체크
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
        // 이름 체크
        edName.addTextChangedListener((Interface_TextWatcher) (charSequence, i, i1, i2) ->{
            boolean isName = i1!=0;
            System.out.println( isName );
            txNameCheck.setVisibility(isName ?  View.INVISIBLE:View.VISIBLE);
            checkList.set(3, isName);
        });


        /** Click Listener **/

        Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        // 취소 버튼
        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(Signup.this, Login.class);
            startActivity(intent);
        });
        // 아이디 중복 검사
        btnIdCheck.setOnClickListener(v ->{
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

            Call call = RetrofitClient.getApiService().checkDuplicateId(id);
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
        //닉네임중복검사버튼
        btnNickNameCheck.setOnClickListener( v -> {
            String nickname = edNickName.getText().toString();

            Call call = RetrofitClient.getApiService().checkDuplicateNickname(nickname);
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
        //핸드폰인증검사버튼
        btnPhoneNumberCheck.setOnClickListener(v -> {
            if (edPhoneNumber.length() > 8) {
                checkList.set(4, true);
                txPhoneCheck.setVisibility(View.INVISIBLE);
            } else {
                txPhoneCheck.setVisibility(View.VISIBLE);
            }
        });
        //회원가입검사버튼
        btnRegister.setOnClickListener(v -> {
            for (int i = 0; i < checkList.size(); i++) {
                if (!checkList.get(i)) {
                    switch (i) {
                        case 0:
                            txIdCheck.setVisibility(View.VISIBLE);
                            edId.requestFocus();
                            return;
                        case 1:
                            txPwdCheck.setVisibility(View.VISIBLE);
                            edPwd.requestFocus();
                            return;
                        case 2:
                            txPwdDoubleCheck.setVisibility(View.VISIBLE);
                            edPwd.requestFocus();
                            return;
                        case 3:
                            txNameCheck.setVisibility(View.VISIBLE);
                            edName.requestFocus();
                            return;
                        case 4:
                            txPhoneCheck.setVisibility(View.VISIBLE);
                            edPhoneNumber.requestFocus();
                            return;
                        case 5:
                            txNicknameCheck.setVisibility(View.VISIBLE);
                            edNickName.requestFocus();
                            return;
                        default:
                            return;
                    }
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

            Call call = RetrofitClient.getApiService().signup(userRequest);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
}

