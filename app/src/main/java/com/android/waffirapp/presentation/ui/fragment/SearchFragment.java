package com.android.waffirapp.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.waffirapp.R;
import com.android.waffirapp.presentation.ui.BaseFragment;

public class SearchFragment extends BaseFragment {

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleToolbarFragment("Search");
    }
}