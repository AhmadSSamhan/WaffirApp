package com.android.waffirapp.presentation.presenter.SingleProductsDetails;

import android.content.Context;

import com.android.waffirapp.model.SingleProductsResponse;

/**
 * Created by Ahmad.Samhan.
 */
public interface SingleProContract {

    interface presenter {

        void onDestroy();

        void requestSingleProductsDataFromServer(Context context, String id);

        void requestFavoriteProductFromServer(Context context, String CustomerID, String ProductsID);
    }

    interface MainView {

        void showProgress();

        void hideProgress();

        void setSingleProductsResultToRecycle(SingleProductsResponse result);

        void onResponseEmpty(String strNoData);

        void onResponseFailure(Throwable throwable);
    }

    interface GetSingleProductsResultInteractor {

        interface OnFinishedListener {
            void onFinished(SingleProductsResponse response);

            void onSomethingWrongOnSingleProductsResponse(String s);

            void onFailure(Throwable t);
        }

        void getSingleProductsResultResponse(Context context, String id, OnFinishedListener onFinishedListener);

        void getFavoriteProductResponse(Context context, String CustomerID, String ProductsID, OnFinishedListener onFinishedListener);
    }

}
