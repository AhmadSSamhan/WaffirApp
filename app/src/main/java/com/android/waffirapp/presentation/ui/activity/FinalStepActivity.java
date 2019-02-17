package com.android.waffirapp.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.presentation.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class FinalStepActivity extends BaseActivity {
    @BindView(R.id.tv_inv_number)
    TextView mTvInvNo;
    @BindView(R.id.tv_delivery_time)
    TextView mTvDeliveryTime;
    @BindView(R.id.tv_delivery_note)
    TextView mTvDeliveryNote;
    @BindView(R.id.tv_delivery_call)
    TextView mTvDeliveryCall;
    @BindView(R.id.tv_messg_thanks)
    TextView mTvMessgThanks;
    @BindView(R.id.btn_return_home_page)
    Button mBtnHomePage;
    private boolean isShowDeliveryPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_step);
        ButterKnife.bind(this);
        getExtraIntent();
        String styledTextInv = "Invoice no: <font color='red'>435345</font>";
        mTvInvNo.setText(Html.fromHtml(styledTextInv), TextView.BufferType.SPANNABLE);
        String styledTextInvNote = "<font color='red'>Notes</font><br>Delivery estimation</br>";
        mTvDeliveryNote.setText(Html.fromHtml(styledTextInvNote), TextView.BufferType.SPANNABLE);
        if (isShowDeliveryPart) {
            mTvDeliveryNote.setVisibility(View.GONE);
            mTvMessgThanks.setVisibility(View.GONE);
        }
    }

    private void getExtraIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isShowDeliveryPart = bundle.getBoolean(ConstantApp.SHOW_DELIVERY_COMPLETE_PURCHASE);
        }
    }

    @OnClick(R.id.btn_return_home_page)
    protected void onClickReturnHomePage() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
