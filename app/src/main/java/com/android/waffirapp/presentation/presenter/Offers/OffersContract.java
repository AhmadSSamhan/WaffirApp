package com.android.waffirapp.presentation.presenter.Offers;

import com.android.waffirapp.model.OffersResponse;

/**
 * This interface to handel Staging Api and interact with activity
 * * Created by Ahmad.Samhan.
 */
public interface OffersContract {
    /**
     * Call when user interact with the view and other when view OnDestroy()
     */
    interface presenter {

        void onDestroy();

        void requestOffersDataFromServer(String langKey);
    }

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * while the setOffersResultToActivity/Fragment and onResponseFailure is fetched from the OffersResultIntractorImpl class
     **/
    interface MainView {

        void showProgress();

        void hideProgress();

        void setOffersResultToRecycle(OffersResponse result);

        void onResponseEmpty(String strNoData);

        void onResponseFailure(Throwable throwable);
    }

    /**
     * Interactors are classes built for fetching data from web services.
     **/
    interface GetOffersResultInteractor {

        interface OnFinishedListener {
            void onFinished(OffersResponse gitOffersResponses);

            void onSomethingWrongOnOffersResponse(String s);

            void onFailure(Throwable t);
        }

        void getOffersResultResponse(String langKey, OnFinishedListener onFinishedListener);
    }
}
