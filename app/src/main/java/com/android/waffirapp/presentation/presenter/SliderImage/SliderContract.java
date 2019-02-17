package com.android.waffirapp.presentation.presenter.SliderImage;

import com.android.waffirapp.model.SliderResponse;

/**
 * Created by Ahmad.Samhan.
 */
public interface SliderContract {
    /**
     * This interface to handel Staging Api and interact with activity
     */
    interface presenter {
        void onDestroy();

        void getSliderImagesDataFromAPI();
    }

    interface MainView {

        void showProgress();

        void hideProgress();

        void setSliderImagesToView(SliderResponse result);

        void onResponseEmpty(String strNoData);

        void onResponseFailure(Throwable throwable);
    }

    interface GetSliderImagesInteractor {
        interface OnFinishedListener {
            void onFinished(SliderResponse sliderResponse);

            void onSomethingWrongOnSliderResponse(String s);

            void onFailure(Throwable t);
        }

        void geSliderResultResponse(String langKey, SliderContract.GetSliderImagesInteractor.OnFinishedListener onFinishedListener);
    }
}
