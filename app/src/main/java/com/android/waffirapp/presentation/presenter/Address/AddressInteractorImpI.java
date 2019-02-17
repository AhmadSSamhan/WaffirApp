package com.android.waffirapp.presentation.presenter.Address;

import android.util.Log;

import com.android.waffirapp.model.EmptyResponse;
import com.android.waffirapp.model.GetAddressResponse;
import com.android.waffirapp.model.ProvincesResponse;
import com.android.waffirapp.network.RetrofitInstance;
import com.android.waffirapp.network.RetrofitInterface;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Ahmad.Samhan.
 */
public class AddressInteractorImpI implements AddressContract.GetListOfAddressInteractor {
    @Override
    public void getListOfAddressResponse(String customerID, OnFinishedListener onFinishedListener) {

        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        Call<GetAddressResponse> call = apiService.getListOfAddressResponseCall(customerID);
        Log.wtf("URL_ListOfAddressAPi", call.request().url() + "");
        call.enqueue(new Callback<GetAddressResponse>() {
            @Override
            public void onResponse(@Nullable Call<GetAddressResponse> call, @Nullable Response<GetAddressResponse> response) {
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
            public void onFailure(Call<GetAddressResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }

    @Override
    public void getDeleteAddressResponse(String id, OnFinishedListener onFinishedListener) {
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        Call<EmptyResponse> call = apiService.getDeleteAddressResponseCall(id);
        Log.wtf("URL_DeleteAddressAPi", call.request().url() + "");
        call.enqueue(new Callback<EmptyResponse>() {
            @Override
            public void onResponse(@Nullable Call<EmptyResponse> call, @Nullable Response<EmptyResponse> response) {
                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() == null) {
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
            public void onFailure(Call<EmptyResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }

    @Override
    public void getEditAddressResponse(String AddID, String CustomerID, String Name, String StreetOne, String StreetTow, String Billlding, String Mobile, String Note, String ProvincesID, String latitude, String longitude, OnFinishedListener onFinishedListener) {
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        Call<EmptyResponse> call = apiService.getEditAddressResponseCall(AddID, CustomerID, Name, StreetOne, StreetTow, Billlding, Mobile, Note, ProvincesID, latitude, longitude);
        Log.wtf("URL_EditAddressAPi", call.request().url() + "");
        call.enqueue(new Callback<EmptyResponse>() {
            @Override
            public void onResponse(@Nullable Call<EmptyResponse> call, @Nullable Response<EmptyResponse> response) {
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
            public void onFailure(Call<EmptyResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }

    @Override
    public void getAddAddressResponse(String CustomerID, String Name, String StreetOne, String StreetTow, String Billlding, String Mobile, String Note, String ProvincesID, String latitude, String longitude, OnFinishedListener onFinishedListener) {
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        Call<EmptyResponse> call = apiService.getAddAddressResponseCall(CustomerID, Name, StreetOne, StreetTow, Billlding, Mobile, Note, ProvincesID, latitude, longitude);
        Log.wtf("URL_AddAddressAPi", call.request().url() + "");
        call.enqueue(new Callback<EmptyResponse>() {
            @Override
            public void onResponse(@Nullable Call<EmptyResponse> call, @Nullable Response<EmptyResponse> response) {
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
            public void onFailure(Call<EmptyResponse> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void getListOfProvincesResponseCall(OnFinishedListener onFinishedListener) {
        RetrofitInterface apiService = RetrofitInstance.getClient().create(RetrofitInterface.class);
        Call<ProvincesResponse> call = apiService.getListOfProvincesResponseCall();
        Log.wtf("URL_ProvincesAPi", call.request().url() + "");
        call.enqueue(new Callback<ProvincesResponse>() {
            @Override
            public void onResponse(@Nullable Call<ProvincesResponse> call, @Nullable Response<ProvincesResponse> response) {
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
            public void onFailure(@Nullable Call<ProvincesResponse> call,@Nullable Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }
}
