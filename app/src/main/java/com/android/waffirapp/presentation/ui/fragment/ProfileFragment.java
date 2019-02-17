package com.android.waffirapp.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.ProfileAdapter;
import com.android.waffirapp.dialog.GeneralDialog;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.help.WrapperProfile;
import com.android.waffirapp.model.ProfileModel;
import com.android.waffirapp.presentation.ui.BaseFragment;
import com.android.waffirapp.presentation.ui.activity.ListOfAddressActivity;
import com.android.waffirapp.presentation.ui.activity.LoginActivity;
import com.android.waffirapp.presentation.ui.activity.SignUpActivity;
import com.android.waffirapp.util.SharedPerApp;
import com.android.waffirapp.util.Utility;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmad.Samhan.
 */
public class ProfileFragment extends BaseFragment implements ProfileAdapter.CallBack {

    @BindView(R.id.recycle_view_all_profile)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_user_email)
    TextView mTvUserEmail;
    @BindView(R.id.img_user_profile)
    ImageView mImgUserProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, inflate);
        getUserInfo();
        return inflate;
    }

    private void getUserInfo() {
        String strUserName = new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_FULL_NAME).getSharedPref();
        String strUserEmail = new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_EMAIL).getSharedPref();
        String strUserImageProfile = new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_IMAGE_PROFILE).getSharedPref();
        mTvUserName.setText(strUserName);
        mTvUserEmail.setText(strUserEmail);
        Utility.setImageCircle(getContext(), strUserImageProfile, mImgUserProfile);
    }

    //Create Dynamic list of profile option
    private List<ProfileModel> getListImages() {
        final List<ProfileModel> mList = new ArrayList<>();
        mList.add(new ProfileModel("1", "My profile", WrapperProfile.ProfileTag.TAG_MY_PROFILE, R.drawable.ic_profile));
        mList.add(new ProfileModel("1", "My favourite", WrapperProfile.ProfileTag.TAG_MY_FAVOURITE, R.drawable.ic_heart));
        mList.add(new ProfileModel("1", "Notification", WrapperProfile.ProfileTag.TAG_NOTIFICATION, R.drawable.ic_noti));
        mList.add(new ProfileModel("1", "Invoice", WrapperProfile.ProfileTag.TAG_INVOICE, R.drawable.ic_bills));
        mList.add(new ProfileModel("1", "Address", WrapperProfile.ProfileTag.TAG_ADDRESS, R.drawable.ic_map));
        mList.add(new ProfileModel("1", "About", WrapperProfile.ProfileTag.TAG_ABOUT, R.drawable.ic_about));
        mList.add(new ProfileModel("1", "Change to english", WrapperProfile.ProfileTag.TAG_CHANGE_LANGUAGE, R.drawable.ic_lang));
        if (new SharedPerApp(getContext()).setStrKey(ConstantApp.USER_ID).getSharedPref() != null) {
            mList.add(new ProfileModel("1", getString(R.string.txt_sign_out), WrapperProfile.ProfileTag.TAG_SIGN_OUT, R.drawable.ic_logout));
        } else {
            mList.add(new ProfileModel("1", "Sign In", WrapperProfile.ProfileTag.TAG_SIGN_OUT, R.drawable.ic_signin));
        }
        mList.add(null);
        mList.add(null);
        return mList;
    }

    private void setDataToRecycleView() {
        if (getListImages().size() == 0) {
            return;
        }
        ProfileAdapter adapter = new ProfileAdapter(getContext(), getListImages(), this);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleToolbarFragment("My Profile");
        setDataToRecycleView();
    }

    @Override
    public void onItemProfileSelected(ProfileModel profileModel, int position) {
        switch (profileModel.getTagName()) {
            case TAG_MY_PROFILE:
                if (Utility.isUserLogin(getContext())) {
                    startActivity(new Intent(getContext(), SignUpActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case TAG_MY_FAVOURITE:
                Log.d("", "");
                break;
            case TAG_NOTIFICATION:
                Log.d("", "");
                break;
            case TAG_INVOICE:
                Log.d("", "");
                break;
            case TAG_ADDRESS:
                if (Utility.isUserLogin(getContext())) {
                    startActivity(new Intent(getContext(), ListOfAddressActivity.class));
                    return;
                }
                //If user logout and gust user used
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case TAG_ABOUT:
                Log.d("", "");
                break;
            case TAG_CHANGE_LANGUAGE:
                Log.d("", "");
                break;
            case TAG_SIGN_OUT:
                //If user login
                if (Utility.isUserLogin(getContext())) {
                    signOut();
                } else {
                    //If user logout and gust user used
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
        }
    }

    private void signOut() {
        GeneralDialog generalDialog = new GeneralDialog(getContext());
        generalDialog.setCallBack(new GeneralDialog.CallBackGeneralDialog() {
            @Override
            public void onCallBackGeneralDialogButtonOne() {

            }

            @Override
            public void onCallBackGeneralDialogButtonTwo() {
                new SharedPerApp(getContext()).clearAllSharedPrefByKey();
                restartFragment();
            }
        });
        generalDialog.show();
        generalDialog.setTitleDialog(getString(R.string.txt_signout_account));
        generalDialog.setTxtForButtonTwo(getString(R.string.txt_sign_out));
        generalDialog.setButtonOneVisible(View.VISIBLE);
        generalDialog.setButtonTwoVisible(View.VISIBLE);
    }

    private void restartFragment() {
        getFragmentManager().beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }

}
