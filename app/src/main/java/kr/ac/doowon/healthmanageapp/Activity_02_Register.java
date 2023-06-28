package kr.ac.doowon.healthmanageapp;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kr.ac.doowon.healthmanageapp.Class_Tool.MakeToast;
import kr.ac.doowon.healthmanageapp.Class_Tool.Input;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.regex.Pattern;

public class Activity_02_Register extends Activity {
     class HandlerManager extends Handler {
        Bundle bundle;

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            bundle = msg.getData();
            String methodName = bundle.getString("Method", "E");
            if(methodName == "DubleCheckId"){
                System.out.println("Activity//isDubleCheckId Start");
                String id = bundle.getString("Id", "E");
                boolean isDubleCheck = bundle.getBoolean("isDoubleCheckIdSuccess");

                System.out.println("Activity//isDubleCheck : " + isDubleCheck);
                DoubleCheckIdSuccess = DubleCheckId(isDubleCheck, id);
            }
            else if(methodName == "DubleCheckNickname"){
                System.out.println("Activity//isDubleCheckNickName Start");
                String nickname = bundle.getString("Nickname", "E");
                boolean isDubleCheckNickname = bundle.getBoolean("isDoubleCheckNicknameSuccess");

                System.out.println("Activity//isDubleCheckNickname : " + isDubleCheckNickname);
                isDoubleCheckNickNameSuccess = DoubleCheckNickname(isDubleCheckNickname, nickname);
            }
            else if(methodName == "Register"){
                System.out.println("Activity//Register Start");
                boolean isRegister = bundle.getBoolean("isRegister");
                MakeToast.Double(isRegister,getApplicationContext(),"회원가입에 성공하셨습니다.","회원가입에 실패하셨습니다.");
                if(isRegister){
                    startActivity(intent);
                }
            }
        }
    }


    Button btnCancel, btnIdCheck, btnNickNameCheck, btnPhoneNumberCheck, btnRegister;
    EditText edId, edPwd, edPwdCheck, edName, edNickName,edPhoneNumber,edACNumber,edHealthCord;
    boolean DoubleCheckIdSuccess, isPwdAlphabatCheck,isPassward,isName ,isPhone ,isDoubleCheckNickNameSuccess, RegistSuccess;
    TextView txIdCheck,txPwdDoubleCheck,txPwdCheck, txNameCheck, txNicknameCheck, txPhoneCheck;
    Class_TheadPool theadPool;
    Intent intent;
    Drawable draw;
    OnClickListener onClick;

    class OnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //취소버튼
            if(view.getId() == R.id.btnCancel){
                startActivity(intent);
            }
            //중복확인버튼
            if(view.getId() == R.id.btnIdCheck){
                String id = edId.getText().toString();
                theadPool.DoubleCheckIdThread(id);
            }
            //닉네임중복검사버튼
            if(view.getId() == R.id.btnNickNameCheck){
                String nickname = edNickName.getText().toString();
                theadPool.DoubleCheckNicknameThread(nickname);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_03_register);

        Awake();
        Update();
    }

    private void Awake(){
        FindByViewIdes();

        Handler handler = new HandlerManager();
        theadPool = new Class_TheadPool(handler);

        onClick = new OnClickListener();
        edId.setFilters(new InputFilter[]{Input.AlphaNum});
        edName.setFilters(new InputFilter[]{Input.Kor});
        edPwd.setFilters(new InputFilter[]{Input.AlphaNum});
        edPwdCheck.setFilters(new InputFilter[]{Input.AlphaNum});

        intent = new Intent(Activity_02_Register.this, Activity_01_Login.class);

        //버튼 색상 변경 코드
        draw = getResources().getDrawable(R.drawable.register_border2);
        draw.setColorFilter(0xFFDF00, PorterDuff.Mode.SRC_ATOP);
        btnRegister.setBackgroundDrawable(draw);
    }

    private void Update(){
        //비밀번호 규격 체크
        edPwd.addTextChangedListener(new Interface_TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String strPwd = edPwd.getText().toString();
                String[] pwdes = strPwd.split("");

                if(strPwd.equals(edPwdCheck.getText().toString())){
                    txPwdDoubleCheck.setText("비밀번호가 일치합니다.");
                    isPassward = true;
                }
                else{
                    txPwdDoubleCheck.setText("비밀번호가 일치하지 않습니다.");
                    isPassward = false;
                }

                if(strPwd.length() < 8 || strPwd.length() > 20) {
                    txPwdCheck.setVisibility(View.VISIBLE);
                    txPwdCheck.setText("8~20자 사이를 입력해주세요.");

                    return;
                }else
                    txPwdCheck.setVisibility(View.INVISIBLE);

                isPwdAlphabatCheck = false;
                for (String ii: pwdes) {
                    if(Pattern.matches("^[a-zA-Z]*$", ii)) {
                        isPwdAlphabatCheck = true;
                        System.out.println(isPwdAlphabatCheck);
                        break;
                    }
                }

                if(!isPwdAlphabatCheck) {
                    txPwdCheck.setText("영문자를 입력해주세요.");
                    txPwdCheck.setVisibility(View.VISIBLE);

                    return;
                }

                txPwdCheck.setVisibility(View.INVISIBLE);
            }
        });
        //비밀번호 일치 체크
        edPwdCheck.addTextChangedListener(new Interface_TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txPwdDoubleCheck.setVisibility(View.VISIBLE);

                if(edPwd.getText().toString().equals(edPwdCheck.getText().toString())){
                    txPwdDoubleCheck.setText("비밀번호가 일치합니다.");
                    isPassward = true;
                }
                else{
                    txPwdDoubleCheck.setText("비밀번호가 일치하지 않습니다.");
                    isPassward = false;
                }
            }
        });
        //버튼리스너 관리
        btnCancel.setOnClickListener(onClick);
        btnIdCheck.setOnClickListener(onClick);
        btnNickNameCheck.setOnClickListener(onClick);
        btnPhoneNumberCheck.setOnClickListener(onClick);
        btnRegister.setOnClickListener(onClick);
    }

    private void FindByViewIdes()
    {
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
    }

    private boolean DubleCheckId(boolean isDoubleCheckIdSuccess,String id){
        String[] ides = id.split("");
        boolean alphabatCheck = false;

        txIdCheck.setVisibility(View.INVISIBLE);

        if(id.length() < 8 || id.length() > 20) {

            MakeToast.Unit(getApplicationContext(),"8~15자 사이로 입력해주세요.");
            return false;
        }

        for (String i: ides) {
            if(Pattern.matches("^[a-zA-Z]*$", i))
                alphabatCheck = true;
            if(!Pattern.matches("^[a-zA-Z0-9]*$",i)){
                MakeToast.Unit(getApplicationContext(),"특수문자는 사용할 수 없습니다.");
                return false;
            }
        }

        if(!alphabatCheck) {
            MakeToast.Unit(getApplicationContext(),"영문이 포함되어야 합니다.");
            return false;
        }

        MakeToast.Double(isDoubleCheckIdSuccess, this,"아이디가 중복되었습니다.","사용가능한 아이디입니다.");
        return !isDoubleCheckIdSuccess;
    }

    private boolean DoubleCheckNickname(boolean isDoubleCheckNicknameSuccess,String nickname)
    {
        txNicknameCheck.setVisibility(View.INVISIBLE);

        if(nickname.length() < 1 || nickname.length() > 20) {
            MakeToast.Unit(getApplicationContext(),"1~20자 사이로 입력해주세요.");
            return false;
        }

        MakeToast.Double(isDoubleCheckNicknameSuccess,getApplicationContext(),"닉네임이 중복되었습니다.","사용가능한 닉네임입니다.");
        return !isDoubleCheckNicknameSuccess;
    }

    private boolean RegisterCheck(){
        if(!edName.getText().toString().equals("")) isName = true;
        else isName = false;

        if(!DoubleCheckIdSuccess || !isPwdAlphabatCheck ||!isPassward || !isName || !isPhone || !isDoubleCheckNickNameSuccess){
            if(!DoubleCheckIdSuccess) txIdCheck.setVisibility(View.VISIBLE);
            if(!isPassward) txPwdDoubleCheck.setVisibility(View.VISIBLE);
            if(!isName) txNameCheck.setVisibility(View.VISIBLE);
            if(!isPhone) txPhoneCheck.setVisibility(View.VISIBLE);
            if(!isDoubleCheckNickNameSuccess) txNicknameCheck.setVisibility(View.VISIBLE);

            if(!DoubleCheckIdSuccess) edId.requestFocus();
            else if(!isPassward) edPwd.requestFocus();
            else if(!isName) edName.requestFocus();
            else if(!isDoubleCheckNickNameSuccess) edNickName.requestFocus();
            else edPhoneNumber.requestFocus();

            return false;
        }
        return true;
    }
}

