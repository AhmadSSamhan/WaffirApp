package com.android.waffirapp.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.PurchasesAdapter;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.ListProductModel;
import com.android.waffirapp.presentation.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmad.Samhan.
 */
public class CompletePurchaseActivity extends BaseActivity {
    @BindView(R.id.recycler_view_purchase_complete)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_delivery_cost)
    TextView mTvDeliveryCost;
    @BindView(R.id.tv_inv_no)
    TextView mTvInvNo;
    @BindView(R.id.toolbar_main_custom)
    Toolbar mToolbar;
    private List<ListProductModel> mList = new ArrayList<>();
    private boolean isShowDeliveryPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_purchase);
        ButterKnife.bind(this);
        createToolBarWithBackBtn(mToolbar);
        setToolbarTitle("Complete purchase");
        getExtraIntent();
        dummyData();
        if (isShowDeliveryPart) {
            mTvDeliveryCost.setVisibility(View.VISIBLE);
        }
        String styledTextInv = "Invoice no : <font color='red'>435345</font>";
        mTvInvNo.setText(Html.fromHtml(styledTextInv), TextView.BufferType.SPANNABLE);
        String styledTextDelivery = "Delivery cost : <font color='#F7C25C'>Free</font>";
        mTvDeliveryCost.setText(Html.fromHtml(styledTextDelivery), TextView.BufferType.SPANNABLE);
    }

    private void getExtraIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isShowDeliveryPart = bundle.getBoolean(ConstantApp.SHOW_DELIVERY_COMPLETE_PURCHASE);
        }
    }

    private void dummyData() {
        for (int ix = 0; ix < 2; ix++) {
            ListProductModel listProductModel = new ListProductModel();
            listProductModel.setProductName("Hamiltion");
            listProductModel.setProductDesc("Item " + ix);
            listProductModel.setProductCount("Count is :70");
            listProductModel.setProductPrice(+ix + " $");
            listProductModel.setProductNotes("this is your note");
            mList.add(listProductModel);
        }
        setDataToRecycleView();
    }

    private void setDataToRecycleView() {
        if (mList.size() == 0) {
            return;
        }
        PurchasesAdapter adapter = new PurchasesAdapter(getContext(), mList);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.btn_complete)
    protected void onClickCompleteOrder() {
        Intent intent = new Intent(getContext(), FinalStepActivity.class);
        intent.putExtra(ConstantApp.SHOW_DELIVERY_COMPLETE_PURCHASE, isShowDeliveryPart);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
