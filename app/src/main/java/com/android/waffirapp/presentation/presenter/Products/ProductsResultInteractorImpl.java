package com.android.waffirapp.presentation.presenter.Products;

import android.util.Log;

import com.android.waffirapp.model.ProductsResponse;
import com.android.waffirapp.network.RetrofitInstance;
import com.android.waffirapp.network.RetrofitInterface;

import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmad.Samhan.
 */
public class ProductsResultInteractorImpl implements ProductsContract.GetProductsInteractor {
    @Override
    public void geProductsResultResponse(String langKey, OnFinishedListener onFinishedListener) {
        /* Create handle for the RetrofitInstance interface*/
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        /* Call the method with parameter in the interface to get the Offers Response data*/
        Call<List<ProductsResponse>> call = apiService.getProductsResponseCall();
        /*Log the URL called*/
        Log.wtf("URL_ProductsAPi", call.request().url() + "");

        call.enqueue(new Callback<List<ProductsResponse>>() {
            @Override
            public void onResponse(@Nullable Call<List<ProductsResponse>> call, @Nullable Response<List<ProductsResponse>> response) {
                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() == null) {
                            //TODO change string try again
                            onFinishedListener.onSomethingWrongOnProductsResponse("TEXT_TRY_AGAIN");
                            return;
                        }
                        onFinishedListener.onFinished(response.body());
                    }
                } catch (Exception e) {
                    onFinishedListener.onSomethingWrongOnProductsResponse(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<ProductsResponse>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
