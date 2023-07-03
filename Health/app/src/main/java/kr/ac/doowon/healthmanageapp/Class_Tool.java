package kr.ac.doowon.healthmanageapp;

import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Class_Tool {

    // 토스트 메세지를 만들어준다.
    static class MakeToast{
        //단일  토스트 메세지를 생성한다.
        public static void Unit(Context context ,String positiveTxt){
            Toast.makeText(context, positiveTxt, Toast.LENGTH_SHORT).show();
        }
        // True, False 값에 따라 변하는 토스트 메세지를 생성한다.
        //--Ture,False여부, 화면, 성공메세지, 실패메세지
        public static void Double(boolean Success, Context context, String positiveTxt, String NagativeTxt){
            if (Success) {
                Toast.makeText(context , positiveTxt, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, NagativeTxt, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 키보드에 입력키를 제어한다.
    static class Input{
        // 한글만 작성할 수 있게 만든다.
        public static InputFilter Kor = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[ㄱ-ㅣ가-힣]*$");
                if (!ps.matcher(source).matches()) {
                    return "";
                }
                return null;
            }
        };
        // 영어와 숫자만 작성할 수 있게 만든다.
        public static InputFilter AlphaNum = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[a-zA-Z0-9]*$");
                if (!ps.matcher(source).matches()) {
                    return "";
                }
                return null;
            }
        };
    }
    // 비밀번호 암호화
    static class SHA256 {
        //비밀번호를 암호화 시켜준다.
        public static String encrypt(String text) throws NoSuchAlgorithmException {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());

            return bytesToHex(md.digest());
        }
        // 바이트를 해쉬화한다.
        private static String bytesToHex(byte[] bytes) {
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        }

    }

    // 쿠키ID
    // 로그인 된 아이디를 반환한다.
    public static String getUSERID(Context context){
        return context.getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE).getString("ID","E");
    }

    //현재시간 리턴
    public static String getNowDate(){
        // 현재시간 가져온다.
        long mNow = System.currentTimeMillis();
        // 포맷을 정한 후 텍스트뷰에 값 출력
        Date date = new Date(mNow);
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

        return mFormat.format(date);
    }
}
