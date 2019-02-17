package com.android.waffirapp.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.my_interface.CallBackCompleteDialog;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class CompletePurchaseDialog extends AlertDialog {
    private Context mContext;
    @BindView(R.id.img_dialog_back)
    ImageView mImgViewBackground;
    @BindView(R.id.tv_option_one)
    TextView mTvOptionOne;
    @BindView(R.id.tv_option_two)
    TextView mTvOptionTwo;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private int mImageResource;
    private String mTypeOne;
    private String mTypeTwo;
    private CallBackCompleteDialog mCallBackCompleteDialog;

    public CompletePurchaseDialog(Context context) {
        super(context);
        mContext = context;
    }

    public CompletePurchaseDialog setImageResource(@DrawableRes int resId) {
        mImageResource = resId;
        return this;
    }

    public CompletePurchaseDialog setCallBack(CallBackCompleteDialog callBackCompleteDialog) {
        mCallBackCompleteDialog = callBackCompleteDialog;
        return this;
    }

    public CompletePurchaseDialog setTypeOptionOne(String type) {
        mTypeOne = type;
        return this;
    }

    public CompletePurchaseDialog setTypeOptionTwo(String type) {
        mTypeTwo = type;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_complete_purchase);
        ButterKnife.bind(this);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setBackgroundImage();
    }

    private void setBackgroundImage() {
        if (mImageResource != 0) {
            mImgViewBackground.setImageResource(mImageResource);
        }
    }

    public void setTextOptionOne(int textOptionOne) {
        if (mTvOptionOne != null) {
            mTvOptionOne.setText(textOptionOne);
        }
    }

    public void setTitle(int title) {
        try {
            mTvTitle.setText(title);
        } catch (Exception e) {
            Log.d("CustomDialog", e.getMessage());
        }
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setTitle(Spannable title) {
        mTvTitle.setText(title);
    }

    public void setTextOptionTwo(int textOptionOne) {
        mTvOptionTwo.setText(textOptionOne);
    }

    private void setToastMessage() {
        Toast.makeText(mContext, "Test", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_option_one)
    void onClickItemOne() {
        setActionAfterSelect(mTypeOne);
        hide();
    }

    @OnClick(R.id.tv_option_two)
    void onClickItemTwo() {
        setActionAfterSelect(mTypeTwo);
        hide();
    }

    @OnClick(R.id.img_close)
    void onClickCloseItem() {
        hide();
    }

    private void setActionAfterSelect(String type) {
        if (mCallBackCompleteDialog != null) {
            mCallBackCompleteDialog.onCompletePurchaseDialog(type);
        }
    }


}
