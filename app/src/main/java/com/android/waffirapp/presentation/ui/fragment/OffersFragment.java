package com.android.waffirapp.presentation.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.OffersAdapter;
import com.android.waffirapp.model.OffersModel;
import com.android.waffirapp.model.OffersResponse;
import com.android.waffirapp.presentation.presenter.Offers.OffersContract;
import com.android.waffirapp.presentation.presenter.Offers.OffersPresenterImpl;
import com.android.waffirapp.presentation.presenter.Offers.OffersResultInteractorImpl;
import com.android.waffirapp.presentation.ui.BaseFragment;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersFragment extends BaseFragment implements OffersAdapter.CallBack, OffersContract.MainView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycle_view_offers)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_offers)
    SwipeRefreshLayout mSwipeRefreshLayout;
    OffersContract.presenter mPresenter;

    @Nullable
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View swipeView = inflater.inflate(R.layout.fragment_offers, container, false);
        ButterKnife.bind(this, swipeView);
        initSwipeRefresh();
        initPresenter();
        return swipeView;
    }

    private void initPresenter() {
        mPresenter = new OffersPresenterImpl(this, new OffersResultInteractorImpl());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleToolbarFragment("Offer 12");
        getDataFromServer();
    }

    /**
     * initializing SwipeRefresh
     */
    private void initSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.grey_700), ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setOffersResultToRecycle(OffersResponse result) {
        setRecycleViewToBasicList(result);
    }

    /**
     * Show list with basic view model like (imgPath , Value)
     */
    private void setRecycleViewToBasicList(OffersResponse result) {
        if (result.getOffers().size() == 0) {
            return;
        }
        OffersAdapter mAdapter = new OffersAdapter(getContext(), result.getOffers(), this);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClickItemOffer(OffersModel offersModel) {
        Toast.makeText(getContext(), offersModel.getID(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseEmpty(String strNoData) {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void onRefresh() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        mPresenter.requestOffersDataFromServer("");
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
