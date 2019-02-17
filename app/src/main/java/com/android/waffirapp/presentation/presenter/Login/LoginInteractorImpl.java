package com.android.waffirapp.presentation.presenter.Login;

import android.util.Log;

import com.android.waffirapp.model.LoginResponse;
import com.android.waffirapp.network.RetrofitInstance;
import com.android.waffirapp.network.RetrofitInterface;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Ahmad.Samhan.
 */
public class LoginInteractorImpl implements LoginContract.GetLoginResultInteractor {
    @Override
    public void getLoginResultResponse(String password, String userName, OnFinishedListener onFinishedListener) {
        /* Create handle for the RetrofitInstance interface*/
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        /* Call the method with parameter in the interface to get the Offers Response data*/
        Call<LoginResponse> call = apiService.getLoginResponseCall(password, userName);
        /*Log the URL called*/
        Log.wtf("URL_LoginAPi", call.request().url() + "");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@Nullable Call<LoginResponse> call, @Nullable Response<LoginResponse> response) {
                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() == null) {
                            //TODO change string try again
                            onFinishedListener.onSomethingWrongOnLoginResponse("TEXT_TRY_AGAIN");
                            return;
                        }
                        onFinishedListener.onFinished(response.body());
                    }
                } catch (Exception e) {
                    onFinishedListener.onSomethingWrongOnLoginResponse(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
