package kr.ac.doowon.healthmanageapp.util;

import android.text.Editable;
import android.text.TextWatcher;

public interface MyTextWatch extends TextWatcher{
    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count){

    }

    @Override
    default void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
}