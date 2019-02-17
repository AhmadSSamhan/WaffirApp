package com.android.waffirapp.presentation.presenter.ListOfProducts;

import com.android.waffirapp.model.ListOfProductResponse;
/**
 * Created by Ahmad.Samhan.
 */
public interface ListOfProductsContract {

    interface presenter {

        void onDestroy();

        void requestListOfProductsDataFromServer(String id, String pageNumber);
    }

    interface MainView {

        void showProgress();

        void hideProgress();

        void setListOfProductsResultToRecycle(ListOfProductResponse result);

        void onResponseEmpty(String strNoData);

        void onResponseFailure(Throwable throwable);
    }

    interface GetListOfProductsResultInteractor {

        interface OnFinishedListener {
            void onFinished(ListOfProductResponse listOfProductResponse);

            void onSomethingWrongOnListOfProductsResponse(String s);

            void onFailure(Throwable t);
        }

        void getListOfProductsResultResponse(String id, String pageNumber, OnFinishedListener onFinishedListener);
    }
}
