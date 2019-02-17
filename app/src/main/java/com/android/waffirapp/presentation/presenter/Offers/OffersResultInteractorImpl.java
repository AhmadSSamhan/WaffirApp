package com.android.waffirapp.presentation.presenter.Offers;

import android.util.Log;

import com.android.waffirapp.model.OffersResponse;
import com.android.waffirapp.network.RetrofitInstance;
import com.android.waffirapp.network.RetrofitInterface;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmad.Samhan.
 */
public class OffersResultInteractorImpl implements OffersContract.GetOffersResultInteractor {

    @Override
    public void getOffersResultResponse(String langKey, OnFinishedListener onFinishedListener) {

        /* Create handle for the RetrofitInstance interface*/
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        /* Call the method with parameter in the interface to get the Offers Response data*/
        Call<OffersResponse> call = apiService.getOffersResponseCall();
        /*Log the URL called*/
        Log.wtf("URL_OffersAPi", call.request().url() + "");
        call.enqueue(new Callback<OffersResponse>() {
            @Override
            public void onResponse(@Nullable Call<OffersResponse> call, @Nullable Response<OffersResponse> response) {
                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() == null) {
                            //TODO change string try again
                            onFinishedListener.onSomethingWrongOnOffersResponse("TEXT_TRY_AGAIN");
                            return;
                        }
                        onFinishedListener.onFinished(response.body());
                    }
                } catch (Exception e) {
                    onFinishedListener.onSomethingWrongOnOffersResponse(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<OffersResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
