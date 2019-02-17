package com.android.waffirapp.presentation.presenter.Products;

import com.android.waffirapp.model.ProductsResponse;

import java.util.List;

/**
 * Created by Ahmad.Samhan.
 */
public interface ProductsContract {

    /**
     * This interface to handel Staging Api and interact with activity
     */
    interface presenter {
        void onDestroy();

        void getProductsDataFromAPI();
    }

    interface MainView {

        void showProgress();

        void hideProgress();

        void setProductsListToView(List<ProductsResponse> result);

        void onResponseEmpty(String strNoData);

        void onResponseFailure(Throwable throwable);
    }

    interface GetProductsInteractor {
        interface OnFinishedListener {
            void onFinished(List<ProductsResponse> productsResponse);

            void onSomethingWrongOnProductsResponse(String s);

            void onFailure(Throwable t);
        }

        void geProductsResultResponse(String langKey, ProductsContract.GetProductsInteractor.OnFinishedListener onFinishedListener);
    }
}
