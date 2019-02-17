package com.android.waffirapp.presentation.presenter.SingleProductsDetails;

import android.content.Context;

import com.android.waffirapp.model.SingleProductsResponse;

/**
 * Created by Ahmad.Samhan.
 */
public class SingleProPresenterImpl implements SingleProContract.presenter, SingleProContract.GetSingleProductsResultInteractor.OnFinishedListener {

    private SingleProContract.MainView mMainView;
    private SingleProContract.GetSingleProductsResultInteractor mGetSingleProductsResultInteractor;

    public SingleProPresenterImpl(SingleProContract.MainView mainView, SingleProContract.GetSingleProductsResultInteractor getSingleProductsResultInteractor) {
        this.mMainView = mainView;
        this.mGetSingleProductsResultInteractor = getSingleProductsResultInteractor;
    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void requestSingleProductsDataFromServer(Context context, String id) {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetSingleProductsResultInteractor.getSingleProductsResultResponse(context, id, this);
    }

    @Override
    public void requestFavoriteProductFromServer(Context context, String CustomerID, String ProductsID) {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetSingleProductsResultInteractor.getFavoriteProductResponse(context, CustomerID, ProductsID, this);
    }

    private SingleProContract.MainView getMainView() {
        return mMainView;
    }

    @Override
    public void onFinished(SingleProductsResponse response) {
        if (getMainView() != null) {
            getMainView().setSingleProductsResultToRecycle(response);
            getMainView().hideProgress();
        }
    }

    @Override
    public void onSomethingWrongOnSingleProductsResponse(String s) {
        if (getMainView() != null) {
            getMainView().onResponseEmpty(s);
            getMainView().hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (getMainView() != null) {
            getMainView().onResponseFailure(t);
            getMainView().hideProgress();
        }
    }
}
