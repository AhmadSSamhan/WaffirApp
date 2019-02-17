package com.android.waffirapp.presentation.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.dialog.GeneralDialog;
import com.android.waffirapp.dialog.ProvinceDialog;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.AddressModel;
import com.android.waffirapp.model.ProvincesModel;
import com.android.waffirapp.model.ProvincesResponse;
import com.android.waffirapp.my_interface.CallBackSelectProvince;
import com.android.waffirapp.presentation.presenter.Address.AddressContract;
import com.android.waffirapp.presentation.presenter.Address.AddressInteractorImpI;
import com.android.waffirapp.presentation.presenter.Address.AddressPresenterImpI;
import com.android.waffirapp.presentation.ui.BaseActivity;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class AddressActivity extends BaseActivity implements AddressContract.MainView, CallBackSelectProvince {
    @BindView(R.id.toolbar_main_custom)
    Toolbar mToolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbar;
    @BindView(R.id.img_maps)
    ImageView mImgMaps;
    @BindView(R.id.tv_province_name)
    TextView mEdProvinceName;
    @BindView(R.id.ed_address_name)
    EditText mEdAddressName;
    @BindView(R.id.ed_street_name)
    EditText mEdStreetName;
    @BindView(R.id.ed_street_two_name)
    EditText mEdStreetTwoName;
    @BindView(R.id.ed_building_number)
    EditText mEdBuildingNumber;
    @BindView(R.id.ed_phone_number)
    EditText mEdPhoneNumber;
    @BindView(R.id.ed_note)
    EditText mEdNote;
    @BindView(R.id.btn_add_new_address)
    Button mBtnAddNewAddress;
    @BindView(R.id.btn_delete_address)
    Button mBtnDeleteAddress;
    @BindView(R.id.scroll_address)
    ScrollView mScrollView;
    @BindView(R.id.progress_bar_load)
    ProgressBar mProgressBar;
    private AddressContract.presenter mPresenter;
    private AddressModel mAddressModel;
    private String mUserId;
    private boolean isEditAddress;
    private boolean isFinishActivityAfterAction;
    private ProvincesModel mSelectedProvincesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        initToolbar();
        getExtraData();
        mPresenter = new AddressPresenterImpI(this, new AddressInteractorImpI());
        setDataToView();
    }

    private void initToolbar() {
        createToolBarWithBackBtn(mToolbar);
        setToolbarTitle("");
        mTvToolbar.setVisibility(View.VISIBLE);
        mTvToolbar.setText(getString(R.string.txt_add_new_address));
    }

    private void getExtraData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mAddressModel = bundle.getParcelable(ConstantApp.USER_SELECT_ADDRESS);
            isEditAddress = bundle.getBoolean(ConstantApp.IS_EDIT_ADDRESS);
            mUserId = bundle.getString(ConstantApp.USER_ID);
        }
    }

    private void setDataToView() {
        if (isEditAddress) {
            mBtnAddNewAddress.setText(getString(R.string.txt_edit_address));
            mBtnDeleteAddress.setVisibility(View.VISIBLE);
            if (mAddressModel != null) {
                mEdProvinceName.setText(mAddressModel.getProvinces());
                mEdAddressName.setText(mAddressModel.getName());
                mEdStreetName.setText(mAddressModel.getStreetOne());
                mEdStreetTwoName.setText(mAddressModel.getStreetTow());
                mEdBuildingNumber.setText(mAddressModel.getBillding());
                mEdPhoneNumber.setText(mAddressModel.getMobile());
                mEdNote.setText(mAddressModel.getNote());
            }
        }
    }

    //Open Google Maps
    @OnClick(R.id.img_maps)
    public void openGoogleMaps() {
        startActivity(new Intent(getContext(), MapsActivity.class));
    }

    @OnClick(R.id.tv_province_name)
    public void openProvinceList() {
        mPresenter.requestGetListOfProvincesFromServer();
    }

    @Override
    public void onSelectProvince(ProvincesModel provincesModel) {
        mSelectedProvincesModel = provincesModel;
        mEdProvinceName.setText(provincesModel.getName());
        mEdProvinceName.setTextColor(Color.BLACK);
    }

    @OnClick(R.id.btn_add_new_address)
    public void ButtonAddNewAddress() {
        if (mSelectedProvincesModel == null) {
            Toast.makeText(getContext(), "Please select your province", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEditAddress) {
            isFinishActivityAfterAction = false;
            mPresenter.requestEditAddressFromServer(mAddressModel.getID(), mUserId, getTextFromEditText(mEdAddressName), getTextFromEditText(mEdStreetName),
                    getTextFromEditText(mEdStreetTwoName), getTextFromEditText(mEdBuildingNumber),
                    getTextFromEditText(mEdPhoneNumber), getTextFromEditText(mEdNote), mSelectedProvincesModel.getID(), "31.958187", "35.858368");
            return;
        }
        isFinishActivityAfterAction = true;
        mPresenter.requestAddNewAddressFromServer(mUserId, getTextFromEditText(mEdAddressName), getTextFromEditText(mEdStreetName),
                getTextFromEditText(mEdStreetTwoName), getTextFromEditText(mEdBuildingNumber),
                getTextFromEditText(mEdPhoneNumber), getTextFromEditText(mEdNote), mSelectedProvincesModel.getID(), "31.958187", "35.858368");
    }

    private String getTextFromEditText(EditText editText) {
        return editText.getText().toString();
    }

    @OnClick(R.id.btn_delete_address)
    public void ButtonDeleteAddress() {
        GeneralDialog generalDialog = new GeneralDialog(getContext());
        generalDialog.setCallBack(new GeneralDialog.CallBackGeneralDialog() {
            @Override
            public void onCallBackGeneralDialogButtonOne() {

            }

            @Override
            public void onCallBackGeneralDialogButtonTwo() {
                isFinishActivityAfterAction = true;
                mPresenter.requestDeleteAddressFromServer(mAddressModel.getID());
            }
        });
        generalDialog.show();
        generalDialog.setTitleDialog("Do you want to delete this item ?");
        generalDialog.setButtonOneVisible(View.VISIBLE);
        generalDialog.setButtonTwoVisible(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        mScrollView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (!isFinishActivityAfterAction) {
            mScrollView.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), getString(R.string.txt_update_address), Toast.LENGTH_SHORT).show();
            return;
        }
        finish();
    }

    @Override
    public void onResponseEmpty(String strNoData) {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void setListOfProvincesToRecycle(ProvincesResponse provincesToRecycle) {
        new ProvinceDialog(getContext()).setList(provincesToRecycle.getProvinces()).setCallBack(this).show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }


}
