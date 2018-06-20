package org.samtech.earthquaketest.utils;

import android.widget.EditText;

public class ETextU {
    public static boolean isValidEditText(EditText editText){
        return !editText.getText().toString().isEmpty();
    }

    public static String getStringFromEtext(EditText editText){
        return editText.getText().toString();
    }

}
