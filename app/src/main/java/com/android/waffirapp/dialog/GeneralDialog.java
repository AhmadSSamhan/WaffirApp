package com.android.waffirapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.waffirapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class GeneralDialog extends AlertDialog {
    @BindView(R.id.tv_title_dialog)
    TextView mTvTitle;
    @BindView(R.id.btn_action_one)
    Button mBtnActionOne;
    @BindView(R.id.btn_action_two)
    Button mBtnActionTwo;
    private CallBackGeneralDialog mCallBackGeneralDialog;

    public GeneralDialog(@NonNull Context context) {
        super(context);
    }

    public GeneralDialog setCallBack(CallBackGeneralDialog callBackGeneralDialog) {
        mCallBackGeneralDialog = callBackGeneralDialog;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_general);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);
        setCancelable(false);
    }


    public void setTitleDialog(Spanned desc) {
        if (!TextUtils.isEmpty(desc)) {
            mTvTitle.setText(desc);
        }
    }

    public void setTitleDialog(CharSequence title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }
    }

    public void setTitleDialog(@StringRes int titleId) {
        if (!TextUtils.isEmpty(getContext().getString(titleId))) {
            mTvTitle.setText(titleId);
        }
    }

    public void setButtonOneVisible(int visible) {
        mBtnActionOne.setVisibility(visible);
    }

    public void setButtonTwoVisible(int visible) {
        mBtnActionTwo.setVisibility(visible);
    }

    public void setTxtForButtonOne(Spanned desc) {
        if (!TextUtils.isEmpty(desc)) {
            mBtnActionOne.setText(desc);
        }
    }

    public void setTxtForButtonTwo(Spanned desc) {
        if (!TextUtils.isEmpty(desc)) {
            mBtnActionTwo.setText(desc);
        }
    }

    public void setTxtForButtonTwo(String desc) {
        if (!TextUtils.isEmpty(desc)) {
            mBtnActionTwo.setText(desc);
        }
    }

    @OnClick(R.id.btn_action_one)
    void onClickButtonActionOne() {
        if (mCallBackGeneralDialog != null) {
            mCallBackGeneralDialog.onCallBackGeneralDialogButtonOne();
            dismiss();
        }
    }

    @OnClick(R.id.btn_action_two)
    void onClickButtonActionTwo() {
        if (mCallBackGeneralDialog != null) {
            mCallBackGeneralDialog.onCallBackGeneralDialogButtonTwo();
            dismiss();
        }
    }

    public interface CallBackGeneralDialog {
        void onCallBackGeneralDialogButtonOne();

        void onCallBackGeneralDialogButtonTwo();
    }
}
