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
import kr.ac.doowon.healthmanageapp.Class_TheadPool;
import kr.ac.doowon.healthmanageapp.Interface_TextWatcher;
import kr.ac.doowon.healthmanageapp.R;
import kr.ac.doowon.healthmanageapp.models.JsonResponese;
import kr.ac.doowon.healthmanageapp.models.RetrofitClient;
import kr.ac.doowon.healthmanageapp.res.KeyPatten;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import androidx.annotation.Nullable;


public class Activity_02_Register extends Activity {
    Button btnCancel, btnIdCheck, btnNickNameCheck, btnPhoneNumberCheck, btnRegister;
    EditText edId, edPwd, edPwdCheck, edName, edNickName,edPhoneNumber,edACNumber,edHealthCord;
    boolean isDoubleCheckIdSuccess, isPwdAlphabatCheck,isPassward,isName ,isPhone ,isDoubleCheckNickNameSuccess, RegistSuccess;
    TextView txIdCheck,txPwdDoubleCheck,txPwdCheck, txNameCheck, txNicknameCheck, txPhoneCheck;
    Class_TheadPool theadPool;

    Drawable draw;
    OnClickListener onClick;
    Toast toast;
    KeyPatten keyPatten;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_03_register);
        toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        keyPatten = new KeyPatten();
        // findViewById
        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);
        btnIdCheck = findViewById(R.id.btnIdCheck);
        btnNickNameCheck = findViewById(R.id.btnNickNameCheck);
        btnPhoneNumberCheck = findViewById(R.id.btnPhoneNumberCheck);

        edId = findViewById(R.id.edId);
        edPwd = findViewById(R.id.edPwd);
        edPwdCheck = findViewById(R.id.edPwdCheck);
        edName = findViewById(R.id.edName);
        edNickName = findViewById(R.id.edNickName);
        edPhoneNumber = findViewById(R.id.edPhoneNumber);

        txPwdDoubleCheck = findViewById(R.id.txPwdDoubleCheck);
        txIdCheck = findViewById(R.id.txIdCheck);
        txNameCheck = findViewById(R.id.txNameCheck);
        txNicknameCheck = findViewById(R.id.txNicknameCheck);
        txPhoneCheck = findViewById(R.id.txPhoneCheck);
        txPwdCheck = findViewById(R.id.txPwdCheck);

        onClick = new OnClickListener();
        edId.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        edName.setFilters(new InputFilter[]{keyPatten.Kor});
        edPwd.setFilters(new InputFilter[]{keyPatten.AlphaNum});
        edPwdCheck.setFilters(new InputFilter[]{keyPatten.AlphaNum});

        //버튼 색상 변경 코드
        draw = getResources().getDrawable(R.drawable.register_border2);
        draw.setColorFilter(0xFFDF00, PorterDuff.Mode.SRC_ATOP);
        btnRegister.setBackgroundDrawable(draw);


        edId.addTextChangedListener(new Interface_TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                isDoubleCheckIdSuccess = false;
            }
        });

        //비밀번호 규격 체크
        edPwd.addTextChangedListener(new Interface_TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String strPwd = edPwd.getText().toString();

                if (strPwd.equals(edPwdCheck.getText().toString())) {
                    txPwdDoubleCheck.setText("비밀번호가 일치합니다.");
                    isPassward = true;
                } else {
                    txPwdDoubleCheck.setText("비밀번호가 일치하지 않습니다.");
                    isPassward = false;
                }

                int pwdLength = strPwd.length();
                if (pwdLength < 8 || pwdLength > 20) {
                    txPwdCheck.setText("8~20자 사이를 입력해주세요.");
                    txPwdCheck.setVisibility(View.VISIBLE);
                    return;
                } else {
                    txPwdCheck.setVisibility(View.INVISIBLE);
                }

                boolean isPwdAlphabatCheck = false;
                for (int index = 0; index < pwdLength; index++) {
                    char c = strPwd.charAt(index);
                    if (Character.isLetter(c)) {
                        isPwdAlphabatCheck = true;
                        System.out.println(isPwdAlphabatCheck);
                        break;
                    }
                }

                if (!isPwdAlphabatCheck) {
                    txPwdCheck.setText("영문자를 입력해주세요.");
                    txPwdCheck.setVisibility(View.VISIBLE);
                    return;
                }

                txPwdCheck.setVisibility(View.INVISIBLE);
            }
        });



        //버튼리스너 관리
        btnCancel.setOnClickListener(onClick);
        btnIdCheck.setOnClickListener(onClick);
        btnNickNameCheck.setOnClickListener(onClick);
        btnPhoneNumberCheck.setOnClickListener(onClick);
        btnRegister.setOnClickListener(onClick);
    }

    class OnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //취소버튼
            if(view.getId() == R.id.btnCancel){
                Intent intent = new Intent(Activity_02_Register.this, Login.class);
                startActivity(intent);
            }

            //중복확인버튼
            Loop1:
            if(view.getId() == R.id.btnIdCheck){
                String id = edId.getText().toString();
                txIdCheck.setVisibility(View.INVISIBLE);

                if(id.length() < 8 || id.length() > 20) {
                    toast.setText("8~15자 사이로 입력해주세요.");
                    toast.show();

                    break Loop1;
                }

                boolean alphabatCheck = false;
                for (char c : id.toCharArray()) {
                    if ( Character.isLetter(c) ) {
                        alphabatCheck = true;
                    }
                    if (!Character.isLetterOrDigit(c)) {
                        toast.setText("특수문자는 사용할 수 없습니다.");
                        toast.show();

                        break Loop1;
                    }
                }

                if(!alphabatCheck) {
                    toast.setText("영문이 포함되어야 합니다.");
                    toast.show();

                    break Loop1;
                }

                Call call = RetrofitClient.getApiService().checkDuplicateId(id);
                call.enqueue(new Callback<JsonResponese>() {
                    @Override
                    public void onResponse(Call<JsonResponese> call, Response<JsonResponese> resp) {
                        if (resp.isSuccessful()){
                            toast.setText("사용가능한 아이디입니다");
                            toast.show();

                            isDoubleCheckIdSuccess=true;
                        }
                        else{
                            toast.setText("아이디가 중복되었습니다");
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponese> call, Throwable t) {
                        System.out.println(t);
                    }
                });
            }


            //닉네임중복검사버튼
            if(view.getId() == R.id.btnNickNameCheck){
                String nickname = edNickName.getText().toString();

                Call call = RetrofitClient.getApiService().checkDuplicateNickname(nickname);
                call.enqueue(new Callback<JsonResponese>() {
                    @Override
                    public void onResponse(Call<JsonResponese> call, Response<JsonResponese> resp) {
                        if (resp.isSuccessful()){
                            toast.setText("사용가능한 닉네임입니다");
                            toast.show();

                            isDoubleCheckIdSuccess=true;
                        }
                        else{
                            toast.setText("닉네임이 중복되었습니다");
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonResponese> call, Throwable t) {
                        System.out.println(t);
                    }
                });

            }
            //핸드폰인증검사버튼
            if(view.getId() == R.id.btnPhoneNumberCheck){
                if(edPhoneNumber.length() > 8) {
                    isPhone = true;
                    txPhoneCheck.setVisibility(View.INVISIBLE);
                }
                else
                {
                    txPhoneCheck.setVisibility(View.VISIBLE);
                }
            }
            //회원가입검사버튼
            if(view.getId() == R.id.btnRegister){
                if(RegisterCheck()){
                    String id = edId.getText().toString();
                    String pwd = edPwd.getText().toString();
                    String name = edName.getText().toString();
                    String nickname = edNickName.getText().toString();
                    String phone = edPhoneNumber.getText().toString();

                    theadPool.RegisterThread(id, pwd, name,nickname,phone);
                }
            }
        }
    }

    private boolean RegisterCheck(){
        if(!edName.getText().toString().equals("")) isName = true;
        else isName = false;

        if(!isDoubleCheckIdSuccess || !isPwdAlphabatCheck ||!isPassward || !isName || !isPhone || !isDoubleCheckNickNameSuccess){
            if(!isDoubleCheckIdSuccess) txIdCheck.setVisibility(View.VISIBLE);
            if(!isPassward) txPwdDoubleCheck.setVisibility(View.VISIBLE);
            if(!isName) txNameCheck.setVisibility(View.VISIBLE);
            if(!isPhone) txPhoneCheck.setVisibility(View.VISIBLE);
            if(!isDoubleCheckNickNameSuccess) txNicknameCheck.setVisibility(View.VISIBLE);

            if(!isDoubleCheckIdSuccess) edId.requestFocus();
            else if(!isPassward) edPwd.requestFocus();
            else if(!isName) edName.requestFocus();
            else if(!isDoubleCheckNickNameSuccess) edNickName.requestFocus();
            else edPhoneNumber.requestFocus();

            return false;
        }
        return true;
    }
}

