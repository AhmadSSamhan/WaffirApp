package com.android.waffirapp.presentation.presenter.SliderImage;

import android.util.Log;

import com.android.waffirapp.model.SliderResponse;
import com.android.waffirapp.network.RetrofitInstance;
import com.android.waffirapp.network.RetrofitInterface;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ahmad.Samhan.
 */
public class SliderResultInteractorImpl implements SliderContract.GetSliderImagesInteractor {

    @Override
    public void geSliderResultResponse(String langKey, OnFinishedListener onFinishedListener) {
        /* Create handle for the RetrofitInstance interface*/
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        /* Call the method with parameter in the interface to get the Offers Response data*/
        Call<SliderResponse> call = apiService.getSliderImagesResponse();
        /*Log the URL called*/
        Log.wtf("URL_SliderAPi", call.request().url() + "");
        call.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(@Nullable Call<SliderResponse> call, @Nullable Response<SliderResponse> response) {
                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() == null) {
                            //TODO change string try again
                            onFinishedListener.onSomethingWrongOnSliderResponse("TEXT_TRY_AGAIN");
                            return;
                        }
                        onFinishedListener.onFinished(response.body());
                    }
                } catch (Exception e) {
                    onFinishedListener.onSomethingWrongOnSliderResponse(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
