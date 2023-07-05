package kr.ac.doowon.healthmanageapp.res;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

public class KeyPatten {
    // 한글만 작성할 수 있게 만든다.
    public InputFilter Kor = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[ㄱ-ㅣ가-힣]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };
    // 영어와 숫자만 작성할 수 있게 만든다.
    public InputFilter AlphaNum = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]*$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };
}
