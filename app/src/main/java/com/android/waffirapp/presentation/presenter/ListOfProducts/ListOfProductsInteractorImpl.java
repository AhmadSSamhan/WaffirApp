package com.android.waffirapp.presentation.presenter.ListOfProducts;

import android.util.Log;

import com.android.waffirapp.model.ListOfProductResponse;
import com.android.waffirapp.network.RetrofitInstance;
import com.android.waffirapp.network.RetrofitInterface;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.waffirapp.help.ConstantApp.TOTAL_PAGES;
/**
 * Created by Ahmad.Samhan.
 */
public class ListOfProductsInteractorImpl implements ListOfProductsContract.GetListOfProductsResultInteractor {

    @Override
    public void getListOfProductsResultResponse(String id, String pageNumber, OnFinishedListener onFinishedListener) {
        /* Create handle for the RetrofitInstance interface*/
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        /* Call the method with parameter in the interface to get the Offers Response data*/
        Call<ListOfProductResponse> call = apiService.getListOfProductResponseCall(id, String.valueOf(TOTAL_PAGES), pageNumber);
        /*Log the URL called*/
        Log.wtf("URL_ListOfProductsAPi", call.request().url() + "");
        call.enqueue(new Callback<ListOfProductResponse>() {
            @Override
            public void onResponse(@Nullable Call<ListOfProductResponse> call, @Nullable Response<ListOfProductResponse> response) {
                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() == null) {
                            //TODO change string try again
                            onFinishedListener.onSomethingWrongOnListOfProductsResponse("TEXT_TRY_AGAIN");
                            return;
                        }
                        onFinishedListener.onFinished(response.body());
                    }
                } catch (Exception e) {
                    onFinishedListener.onSomethingWrongOnListOfProductsResponse(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ListOfProductResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
