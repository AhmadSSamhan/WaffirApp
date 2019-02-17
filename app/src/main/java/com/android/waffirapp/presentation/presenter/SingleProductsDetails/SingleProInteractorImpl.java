package com.android.waffirapp.presentation.presenter.SingleProductsDetails;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.EmptyResponse;
import com.android.waffirapp.model.SingleProductsResponse;
import com.android.waffirapp.network.RetrofitInstance;
import com.android.waffirapp.network.RetrofitInterface;
import com.android.waffirapp.util.SharedPerApp;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmad.Samhan.
 */
public class SingleProInteractorImpl implements SingleProContract.GetSingleProductsResultInteractor {
    @Override
    public void getSingleProductsResultResponse(Context context, String id, OnFinishedListener onFinishedListener) {

        /* Create handle for the RetrofitInstance interface*/
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);

        String strUserId = new SharedPerApp(context).setStrKey(ConstantApp.USER_ID).getSharedPref();
        String customerId = TextUtils.isEmpty(strUserId) || strUserId == null ? ConstantApp.GUEST_USER : strUserId;
        /* Call the method with parameter in the interface to get the Offers Response data*/
        Call<SingleProductsResponse> call = apiService.getSingleProductsResponseCall(id, customerId);

        /*Log the URL called*/
        Log.wtf("URL_SingleProductsAPi", call.request().url() + "");
        call.enqueue(new Callback<SingleProductsResponse>() {
            @Override
            public void onResponse(@Nullable Call<SingleProductsResponse> call, @Nullable Response<SingleProductsResponse> response) {
                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() == null) {
                            //TODO change string try again
                            onFinishedListener.onSomethingWrongOnSingleProductsResponse("TEXT_TRY_AGAIN");
                        }
                        onFinishedListener.onFinished(response.body());
                    }
                } catch (Exception e) {
                    onFinishedListener.onSomethingWrongOnSingleProductsResponse(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SingleProductsResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getFavoriteProductResponse(Context context, String CustomerID, String ProductsID, OnFinishedListener onFinishedListener) {
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        String strUserId = new SharedPerApp(context).setStrKey(ConstantApp.USER_ID).getSharedPref();
        String customerId = TextUtils.isEmpty(strUserId) || strUserId == null ? ConstantApp.GUEST_USER : strUserId;
        Call<EmptyResponse> call = apiService.getFavoriteProductResponse(customerId, ProductsID);
        call.enqueue(new Callback<EmptyResponse>() {
            @Override
            public void onResponse(@Nullable Call<EmptyResponse> call, @Nullable Response<EmptyResponse> response) {
                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() == null) {
                            //TODO change string try again
                            onFinishedListener.onSomethingWrongOnSingleProductsResponse("TEXT_TRY_AGAIN");
                        }
                        onFinishedListener.onFinished(null);
                    }
                } catch (Exception e) {
                    onFinishedListener.onSomethingWrongOnSingleProductsResponse(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<EmptyResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
