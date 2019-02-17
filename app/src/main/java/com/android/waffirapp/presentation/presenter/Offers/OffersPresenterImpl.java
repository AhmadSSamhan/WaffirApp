package com.android.waffirapp.presentation.presenter.Offers;

import com.android.waffirapp.model.OffersResponse;

/**
 * Created by Ahmad.Samhan.
 */
public class OffersPresenterImpl implements OffersContract.presenter, OffersContract.GetOffersResultInteractor.OnFinishedListener {

    private OffersContract.MainView mMainView;
    private OffersContract.GetOffersResultInteractor mGetOffersResultInteractor;


    public OffersPresenterImpl(OffersContract.MainView mainView, OffersContract.GetOffersResultInteractor offersResultInteractor) {
        this.mMainView = mainView;
        this.mGetOffersResultInteractor = offersResultInteractor;
    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void requestOffersDataFromServer(String langKey) {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetOffersResultInteractor.getOffersResultResponse(langKey, this);
    }

    @Override
    public void onFinished(OffersResponse gitOffersResponses) {
        if (getMainView() != null) {
            getMainView().setOffersResultToRecycle(gitOffersResponses);
            getMainView().hideProgress();
        }
    }

    @Override
    public void onSomethingWrongOnOffersResponse(String s) {
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

    private OffersContract.MainView getMainView() {
        return mMainView;
    }
}
