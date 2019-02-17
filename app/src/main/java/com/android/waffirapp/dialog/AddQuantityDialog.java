package com.android.waffirapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.my_interface.CallBackAddQuantity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class AddQuantityDialog extends BottomSheetDialog {
    @BindView(R.id.btn_add_quantity_to_cart)
    Button mBtnAdd;
    @BindView(R.id.ly_quantity_root)
    LinearLayout mRoot;
    @BindView(R.id.tv_quantity_count)
    TextView mTvQuantityCount;
    private CallBackAddQuantity mCallBackAddQuantity;
    private int mCount = 0;
    private String mMaxQuantity;
    private String mMinQuantity;

    public AddQuantityDialog(@NonNull Context context) {
        super(context);
    }

    public AddQuantityDialog setMaxQuantity(String maxQuantity) {
        this.mMaxQuantity = maxQuantity;
        return this;
    }

    public AddQuantityDialog setMinQuantity(String minQuantity) {
        this.mMinQuantity = minQuantity;
        return this;
    }

    public AddQuantityDialog setCallBack(CallBackAddQuantity callBack) {
        this.mCallBackAddQuantity = callBack;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_quantity_to_cart);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);
        mCount = Integer.valueOf(mMinQuantity);
        setTextToQuantity();
    }

    @OnClick(R.id.btn_increase_quantity)
    void increaseQuantity() {
        if (mCount < Integer.valueOf(mMaxQuantity)) {
            mCount++;
            setTextToQuantity();
            return;
        }
        setToastMessage(getContext().getString(R.string.txt_max_quantity) + " " + mMaxQuantity);
    }

    @OnClick(R.id.btn_decrease_quantity)
    void decreaseQuantity() {
        if (mCount <= 0 || mCount <= Integer.valueOf(mMinQuantity)) {
            setToastMessage(getContext().getString(R.string.txt_min_quantity) + " " + mMinQuantity);
            return;
        }
        mCount--;
        setTextToQuantity();
    }

    private void setToastMessage(String txt) {
        Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
    }

    private void setTextToQuantity() {
        String txt = getContext().getString(R.string.txt_quantity_is) + " " + mCount;
        mTvQuantityCount.setText(txt);
    }

    @OnClick(R.id.btn_add_quantity_to_cart)
    void onClickAddButton() {
        if (mCallBackAddQuantity != null) {
            mCallBackAddQuantity.onAddQuantityToCart(mCount);
        }
        dismiss();
    }
}
