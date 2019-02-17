package com.android.waffirapp.presentation.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.waffirapp.R;
import com.android.waffirapp.presentation.ui.BaseActivity;
import com.android.waffirapp.util.Utility;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */

public class ForgotPasswordActivity extends BaseActivity {
    @BindView(R.id.toolbar_main_custom)
    Toolbar mToolbar;
    @BindView(R.id.ed_forgot_email)
    EditText mEdForgotPass;
    @BindView(R.id.img_clear_text_email)
    ImageView mImgClearText;
    @BindView(R.id.root_forgot_pass)
    ConstraintLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        createToolBarWithBackBtn(mToolbar);
        setToolbarTitle("forgot your password");
        mEdForgotPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mImgClearText.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick(R.id.img_clear_text_email)
    void onClickClearEmail() {
        mEdForgotPass.getText().clear();
        mEdForgotPass.clearFocus();
        mImgClearText.setVisibility(View.GONE);
        Utility.hideKeyboard(getContext(), mRootView);
    }
}
