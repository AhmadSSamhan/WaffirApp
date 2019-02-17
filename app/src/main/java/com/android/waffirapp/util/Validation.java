package com.android.waffirapp.util;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.waffirapp.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Created by Ahmad.Samhan on 2019-02-09.
 */

public class Validation {
    public static boolean validateEmail(Activity activity, EditText editText, TextInputLayout textInputLayout) {
        String strEmail = editText.getText().toString();
        if (!isValidEmail(strEmail)) {
            textInputLayout.setError(activity.getString(R.string.txt_enter_valid_email));
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void requestFocus(View view, Activity activity) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static void clearErrorEdit(EditText editText, TextInputLayout textInputLayout) {
        try {
            editText.getText().clear();
            editText.clearFocus();
            textInputLayout.clearFocus();
            textInputLayout.setErrorEnabled(false);
        } catch (Exception e) {
            e.getCause();
        }
    }
}
