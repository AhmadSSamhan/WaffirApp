package com.android.waffirapp.presentation.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.presentation.ui.BaseActivity;
import com.android.waffirapp.util.SharedPerApp;
import com.android.waffirapp.util.Utility;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmad.Samhan.
 */
public class SignUpActivity extends BaseActivity {
    @BindView(R.id.ed_password)
    EditText mEditTextPassword;
    @BindView(R.id.ed_first_name)
    EditText mEditTextFirstName;
    @BindView(R.id.ed_last_name)
    EditText mEditTextLastName;
    @BindView(R.id.ed_email_address)
    EditText mEditTextEmail;
    @BindView(R.id.ed_phone_number)
    EditText mEditTextPhoneNumber;
    @BindView(R.id.img_user_profile)
    ImageView mImgUserProfile;
    @BindView(R.id.toolbar_main_custom)
    Toolbar mToolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbar;
    @BindView(R.id.img_show_password)
    ImageView mImgShowPassword;
    private boolean icClickShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        createToolBarWithBackBtn(mToolbar);
        setToolbarTitle("");
        mTvToolbar.setVisibility(View.VISIBLE);
        mTvToolbar.setText("New Account");
        getUserInfo();
        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mImgShowPassword.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_hide_pass));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!icClickShowPassword) {
                    return;
                }
                mImgShowPassword.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_show_pass));
            }
        });
    }

    private void getUserInfo() {
        String strUserFirstName = new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_FIRST_NAME).getSharedPref();
        String strUserLastName = new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_LAST_NAME).getSharedPref();
        String strUserEmail = new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_EMAIL).getSharedPref();
        String strPhoneNumber = new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_PHONE_NUMBER).getSharedPref();
        String strUserProfile = new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_IMAGE_PROFILE).getSharedPref();
        mEditTextFirstName.setText(strUserFirstName);
        mEditTextLastName.setText(strUserLastName);
        mEditTextEmail.setText(strUserEmail);
        mEditTextPhoneNumber.setText(strPhoneNumber);
        Utility.setImageCircle(getContext(), strUserProfile, mImgUserProfile);
    }

    @OnClick(R.id.img_show_password)
    void onCLickShowPassword() {
        if (!icClickShowPassword) {
            icClickShowPassword = true;
            mImgShowPassword.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_show_pass));
            mEditTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mEditTextPassword.setSelection(mEditTextPassword.length());
            return;
        }
        icClickShowPassword = false;
        mImgShowPassword.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_hide_pass));
        mEditTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mEditTextPassword.setSelection(mEditTextPassword.length());
    }
}
