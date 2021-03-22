package com.example.n_one.utils;

import android.widget.EditText;

public class EditTextUtil {

    public static void editTextError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
        editText.requestFocus();
    }
}
