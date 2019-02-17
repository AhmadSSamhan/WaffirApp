package com.android.waffirapp.presentation.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.waffirapp.my_interface.CallBackChangeTitleToolBarByFragment;
import com.android.waffirapp.realm.RealmController;

public abstract class BaseFragment extends Fragment {
    private CallBackChangeTitleToolBarByFragment mCallBackOfferFragment;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {
        return onCreateFragmentView(inflater, container, savedInstanseState);
    }

    public abstract View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallBackOfferFragment = (CallBackChangeTitleToolBarByFragment) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MyInterface ");

        }
    }

    public void setTitleToolbarFragment(String title) {
        if (mCallBackOfferFragment != null) {
            mCallBackOfferFragment.setChangeTitleToolBarByFragment(title);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallBackOfferFragment = null;
    }
}
