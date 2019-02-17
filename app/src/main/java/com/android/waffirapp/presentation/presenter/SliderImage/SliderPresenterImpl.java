package com.android.waffirapp.presentation.presenter.SliderImage;

import com.android.waffirapp.model.SliderResponse;

/**
 * Created by Ahmad.Samhan.
 */
public class SliderPresenterImpl implements SliderContract.presenter, SliderContract.GetSliderImagesInteractor.OnFinishedListener {

    private SliderContract.MainView mMainView;
    private SliderContract.GetSliderImagesInteractor mGetSliderImagesInteractor;

    public SliderPresenterImpl(SliderContract.MainView mainView, SliderContract.GetSliderImagesInteractor getSliderImagesInteractor) {
        this.mMainView = mainView;
        this.mGetSliderImagesInteractor = getSliderImagesInteractor;
    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void getSliderImagesDataFromAPI() {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetSliderImagesInteractor.geSliderResultResponse("", this);
    }

    private SliderContract.MainView getMainView() {
        return mMainView;
    }

    @Override
    public void onFinished(SliderResponse sliderResponse) {
        if (getMainView() != null) {
            getMainView().setSliderImagesToView(sliderResponse);
            getMainView().hideProgress();
        }
    }

    @Override
    public void onSomethingWrongOnSliderResponse(String s) {
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
