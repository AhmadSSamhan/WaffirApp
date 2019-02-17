package com.android.waffirapp.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.AddressAdapter;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.AddressModel;
import com.android.waffirapp.model.GetAddressResponse;
import com.android.waffirapp.presentation.presenter.Address.AddressContract;
import com.android.waffirapp.presentation.presenter.Address.AddressInteractorImpI;
import com.android.waffirapp.presentation.presenter.Address.AddressPresenterImpI;
import com.android.waffirapp.presentation.ui.BaseActivity;
import com.android.waffirapp.util.SharedPerApp;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Ahmad.Samhan.
 */
public class ListOfAddressActivity extends BaseActivity implements AddressAdapter.CallBackAddress, AddressContract.MainView {

    @BindView(R.id.recycle_view_all_address)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_main_custom)
    Toolbar mToolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbar;

    private AddressContract.presenter mPresenter;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_address);
        ButterKnife.bind(this);
        initToolbar();
        mPresenter = new AddressPresenterImpI(this, new AddressInteractorImpI());
    }
    private void initToolbar() {
        createToolBarWithBackBtn(mToolbar);
        setToolbarTitle("");
        mTvToolbar.setVisibility(View.VISIBLE);
        mTvToolbar.setText(getString(R.string.title_addresses));
    }

    private void initList(List<AddressModel> list) {
        list.add(null);
        AddressAdapter mAdapter = new AddressAdapter(getContext(), list, this);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String sharedPrefUserId = new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_ID).getSharedPref();
        mUserId = sharedPrefUserId != null ? sharedPrefUserId : ConstantApp.GUEST_USER;
        mPresenter.requestGetListOfAddressFromServer(mUserId);
    }

    @OnClick(R.id.btn_add_new_address)
    void btnAddNewAddress() {
        goToAddressActivity(null,false);
    }

    @Override
    public void onSelectedAddress(AddressModel addressModel) {
        goToAddressActivity(addressModel,true);
    }

    private void goToAddressActivity(AddressModel addressModel,boolean isEditAddress) {
        Intent intent = new Intent(getContext(), AddressActivity.class);
        intent.putExtra(ConstantApp.USER_SELECT_ADDRESS, addressModel);
        intent.putExtra(ConstantApp.USER_ID, mUserId);
        intent.putExtra(ConstantApp.IS_EDIT_ADDRESS, isEditAddress);
        startActivity(intent);
    }

    @Override
    public void showProgress() {
        //TODO add progress
    }

    @Override
    public void hideProgress() {
        //TODO hide progress
    }

    @Override
    public void onResponseEmpty(String strNoData) {
        //TODO add something
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        //TODO add something
    }

    @Override
    public void setListOfAddressResultToRecycle(GetAddressResponse getAddressResponse) {
        initList(getAddressResponse.getAddress());
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
