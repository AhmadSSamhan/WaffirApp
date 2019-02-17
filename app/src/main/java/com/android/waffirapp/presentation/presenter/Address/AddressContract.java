package com.android.waffirapp.presentation.presenter.Address;

import com.android.waffirapp.model.GetAddressResponse;
import com.android.waffirapp.model.ProvincesResponse;
/**
 * Created by Ahmad.Samhan.
 */
public interface AddressContract {

    interface presenter {

        void onDestroy();

        void requestAddNewAddressFromServer(String CustomerID, String Name, String StreetOne,
                                            String StreetTow, String Billlding, String Mobile, String Note, String ProvincesID,
                                            String latitude, String longitude);

        void requestDeleteAddressFromServer(String id);

        void requestEditAddressFromServer(String AddID, String CustomerID, String Name, String StreetOne,
                                          String StreetTow, String Billlding, String Mobile, String Note, String ProvincesID,
                                          String latitude, String longitude);

        void requestGetListOfAddressFromServer(String customerID);

        void requestGetListOfProvincesFromServer();
    }

    interface MainView {

        void showProgress();

        void hideProgress();

        void onResponseEmpty(String strNoData);

        void onResponseFailure(Throwable throwable);

        default void setListOfAddressResultToRecycle(GetAddressResponse getAddressResponse) {
            System.out.println("ListOfAddress" + getAddressResponse);
        }

        default void setListOfProvincesToRecycle(ProvincesResponse provincesToRecycle) {
            System.out.println("ListOfProvinces" + provincesToRecycle);
        }

    }

    interface GetListOfAddressInteractor {

        interface OnFinishedListener {
            void onFinished(Object o);

            void onSomethingWrongOnListOfProductsResponse(String s);

            void onFailure(Throwable t);
        }

        void getListOfAddressResponse(String customerID, OnFinishedListener onFinishedListener);

        void getDeleteAddressResponse(String id, OnFinishedListener onFinishedListener);

        void getEditAddressResponse(String AddID, String CustomerID, String Name, String StreetOne,
                                    String StreetTow, String Billlding, String Mobile, String Note, String ProvincesID,
                                    String latitude, String longitude, OnFinishedListener onFinishedListener);

        void getAddAddressResponse(String CustomerID, String Name, String StreetOne,
                                   String StreetTow, String Billlding, String Mobile, String Note, String ProvincesID,
                                   String latitude, String longitude, OnFinishedListener onFinishedListener);

        void getListOfProvincesResponseCall(OnFinishedListener onFinishedListener);

    }

}
