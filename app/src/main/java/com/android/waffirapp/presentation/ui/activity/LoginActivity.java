package com.android.waffirapp.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.LoginResponse;
import com.android.waffirapp.presentation.presenter.Login.LoginContract;
import com.android.waffirapp.presentation.presenter.Login.LoginInteractorImpl;
import com.android.waffirapp.presentation.presenter.Login.LoginPresenterImpl;
import com.android.waffirapp.presentation.ui.BaseActivity;
import com.android.waffirapp.util.SharedPerApp;
import com.android.waffirapp.util.Utility;
import com.android.waffirapp.util.Validation;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 * Created by Ahmad.Samhan.
 */
public class LoginActivity extends BaseActivity implements LoginContract.MainView {

    @BindView(R.id.tv_subscribe)
    TextView mTvSubScribe;
    @BindView(R.id.tv_forgot_password)
    TextView mTvForgotPassword;
    @BindView(R.id.ed_password)
    EditText mEdPassword;
    @BindView(R.id.ed_email)
    EditText mEdEmail;
    @BindView(R.id.text_input_email)
    TextInputLayout mTxtInputEmail;
    @BindView(R.id.text_input_password)
    TextInputLayout mTextInputPassword;
    @BindView(R.id.img_clear_text_pass)
    ImageView mImgClearTextPassword;
    @BindView(R.id.img_clear_text_email)
    ImageView mImgClearTextEmail;
    @BindView(R.id.progress_bar_load)
    ProgressBar mProgressBar;
    @BindView(R.id.login_form)
    ScrollView mScrollView;
    private LoginContract.presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenterImpl(this, new LoginInteractorImpl());
        mEdPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTvForgotPassword.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable.toString())) {
                    mTvForgotPassword.setVisibility(View.VISIBLE);
                    return;
                }
                mImgClearTextPassword.setVisibility(View.VISIBLE);
            }
        });

        mEdEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mImgClearTextEmail.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setValidateEmail();
            }
        });
    }

    @OnClick(R.id.btn_return_home_page)
    void onClickLogIn() {
        if (checkValidationData()) {
            return;
        }
        if (!TextUtils.isEmpty(mEdPassword.getText().toString()) && !TextUtils.isEmpty(mEdEmail.getText().toString())) {
            mPresenter.requestLoginDataFromServer(mEdPassword.getText().toString(), mEdEmail.getText().toString());
        }
        Utility.hideKeyboard(getContext());
    }

    private boolean setValidateEmail() {
        return Validation.validateEmail(this, mEdEmail, mTxtInputEmail);
    }

    private boolean checkValidationData() {
        if (TextUtils.isEmpty(mEdEmail.getText().toString())) {
            Validation.requestFocus(mEdEmail, this);
            Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            return true;
        }
        mTxtInputEmail.clearFocus();
        mTxtInputEmail.setErrorEnabled(false);
        if (!setValidateEmail()) {
            Validation.requestFocus(mEdEmail, this);
            mEdEmail.setError(getString(R.string.txt_enter_valid_email));
            return true;
        }
        mTextInputPassword.clearFocus();
        mTextInputPassword.setErrorEnabled(false);
        if (TextUtils.isEmpty(mEdPassword.getText().toString())) {
            Validation.requestFocus(mEdPassword, this);
            Toast.makeText(getContext(), "Enter password", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @OnClick(R.id.tv_subscribe)
    void onClickSubscribe() {
        startActivity(new Intent(getContext(), SignUpActivity.class));
    }

    @OnClick(R.id.tv_regs_by_card)
    void onClickRegsByCard() {
        startActivity(new Intent(getContext(), LoginByCardActivity.class));
    }

    @OnClick(R.id.tv_forgot_password)
    void onClickForgotPassword() {
        startActivity(new Intent(getContext(), ForgotPasswordActivity.class));
    }

    @OnClick(R.id.img_clear_text_email)
    void onClickClearEmail() {
        mEdEmail.getText().clear();
        mEdEmail.clearFocus();
        mImgClearTextEmail.setVisibility(View.GONE);
        Utility.hideKeyboard(getContext(), mEdEmail);
    }

    @OnClick(R.id.img_clear_text_pass)
    void onClickClearPassword() {
        mEdPassword.getText().clear();
        mEdPassword.clearFocus();
        mImgClearTextPassword.setVisibility(View.GONE);
        mTvForgotPassword.setVisibility(View.VISIBLE);
        Utility.hideKeyboard(getContext(), mEdPassword);
    }


    @Override
    public void showProgress() {
        mProgressBar.setIndeterminate(true);
        mScrollView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setIndeterminate(false);
        mProgressBar.setVisibility(View.GONE);
        mScrollView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setLoginResultToStore(LoginResponse result) {
        if (result.getEmail() == null || result.getMsgErorre() != null) {
            Toast.makeText(getContext(), result.getMsgErorre(), Toast.LENGTH_SHORT).show();
            return;
        }
        Validation.clearErrorEdit(mEdEmail, mTxtInputEmail);
        Validation.clearErrorEdit(mEdPassword, mTextInputPassword);
        new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_FULL_NAME).setStrValue(result.getFirstName() + " " + result.getLastName()).setSharedPref();
        new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_FIRST_NAME).setStrValue(result.getFirstName()).setSharedPref();
        new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_LAST_NAME).setStrValue(result.getLastName()).setSharedPref();
        new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_EMAIL).setStrValue(result.getEmail()).setSharedPref();
        new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_ID).setStrValue(result.getUserID()).setSharedPref();
        new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_IMAGE_PROFILE).setStrValue(result.getImages()).setSharedPref();
        new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_PHONE_NUMBER).setStrValue(result.getMobile()).setSharedPref();
        finish();
    }

    @Override
    public void onResponseEmpty(String strNoData) {
        //TODO something
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(getContext(), getString(R.string.txt_check_network), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}

