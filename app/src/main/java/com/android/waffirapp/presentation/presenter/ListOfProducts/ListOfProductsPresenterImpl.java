package com.android.waffirapp.presentation.presenter.ListOfProducts;

import com.android.waffirapp.model.ListOfProductResponse;

/**
 * Created by Ahmad.Samhan.
 */
public class ListOfProductsPresenterImpl implements ListOfProductsContract.presenter, ListOfProductsContract.GetListOfProductsResultInteractor.OnFinishedListener {

    private ListOfProductsContract.MainView mMainView;
    private ListOfProductsContract.GetListOfProductsResultInteractor mGetListOfProductsResultInteractor;


    public ListOfProductsPresenterImpl(ListOfProductsContract.MainView mainView, ListOfProductsContract.GetListOfProductsResultInteractor resultInteractor) {
        this.mMainView = mainView;
        this.mGetListOfProductsResultInteractor = resultInteractor;
    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void requestListOfProductsDataFromServer(String id, String pageNumber) {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetListOfProductsResultInteractor.getListOfProductsResultResponse(id, pageNumber, this);
    }

    private ListOfProductsContract.MainView getMainView() {
        return mMainView;
    }

    @Override
    public void onFinished(ListOfProductResponse listOfProductResponse) {
        if (getMainView() != null) {
            getMainView().setListOfProductsResultToRecycle(listOfProductResponse);
            getMainView().hideProgress();
        }
    }

    @Override
    public void onSomethingWrongOnListOfProductsResponse(String s) {
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
