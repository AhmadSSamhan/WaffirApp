package com.android.waffirapp.presentation.presenter.Products;

import com.android.waffirapp.model.ProductsResponse;

import java.util.List;

/**
 * Created by Ahmad.Samhan.
 */
public class ProductsPresenterImpl implements ProductsContract.presenter, ProductsContract.GetProductsInteractor.OnFinishedListener {

    private ProductsContract.MainView mMainView;
    private ProductsContract.GetProductsInteractor mGetProductsInteractor;

    public ProductsPresenterImpl(ProductsContract.MainView mainView, ProductsContract.GetProductsInteractor getProductsInteractor) {
        this.mMainView = mainView;
        this.mGetProductsInteractor = getProductsInteractor;
    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void getProductsDataFromAPI() {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetProductsInteractor.geProductsResultResponse("", this);
    }

    private ProductsContract.MainView getMainView() {
        return mMainView;
    }

    @Override
    public void onFinished(List<ProductsResponse> productsResponse) {
        if (getMainView() != null) {
            getMainView().setProductsListToView(productsResponse);
            getMainView().hideProgress();
        }
    }

    @Override
    public void onSomethingWrongOnProductsResponse(String s) {
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
