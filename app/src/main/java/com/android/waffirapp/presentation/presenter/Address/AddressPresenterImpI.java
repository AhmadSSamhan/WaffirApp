package com.android.waffirapp.presentation.presenter.Address;

import com.android.waffirapp.model.GetAddressResponse;
import com.android.waffirapp.model.ProvincesResponse;
/**
 * Created by Ahmad.Samhan.
 */
public class AddressPresenterImpI implements AddressContract.presenter, AddressContract.GetListOfAddressInteractor.OnFinishedListener {

    private AddressContract.MainView mMainView;
    private AddressContract.GetListOfAddressInteractor mGetListOfAddressInteractor;

    public AddressPresenterImpI(AddressContract.MainView mainView, AddressContract.GetListOfAddressInteractor getListOfAddressInteractor) {
        this.mMainView = mainView;
        this.mGetListOfAddressInteractor = getListOfAddressInteractor;
    }

    private AddressContract.MainView getMainView() {
        return mMainView;
    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void requestAddNewAddressFromServer(String CustomerID, String Name, String StreetOne, String StreetTow, String Billlding, String Mobile, String Note, String ProvincesID, String latitude, String longitude) {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetListOfAddressInteractor.getAddAddressResponse(CustomerID, Name, StreetOne, StreetTow, Billlding, Mobile, Note, ProvincesID, latitude, longitude, this);
    }


    @Override
    public void requestDeleteAddressFromServer(String id) {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetListOfAddressInteractor.getDeleteAddressResponse(id, this);
    }

    @Override
    public void requestEditAddressFromServer(String AddID, String CustomerID, String Name, String StreetOne, String StreetTow, String Billlding, String Mobile, String Note, String ProvincesID, String latitude, String longitude) {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetListOfAddressInteractor.getEditAddressResponse(AddID, CustomerID, Name, StreetOne, StreetTow, Billlding, Mobile, Note, ProvincesID, latitude, longitude, this);
    }

    @Override
    public void requestGetListOfAddressFromServer(String customerID) {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetListOfAddressInteractor.getListOfAddressResponse(customerID, this);
    }

    @Override
    public void requestGetListOfProvincesFromServer() {
        mGetListOfAddressInteractor.getListOfProvincesResponseCall(this);
    }

    @Override
    public void onFinished(Object o) {
        if (getMainView() != null) {
            if (o instanceof GetAddressResponse) {
                getMainView().setListOfAddressResultToRecycle((GetAddressResponse) o);
            } else if (o instanceof ProvincesResponse) {
                getMainView().setListOfProvincesToRecycle((ProvincesResponse) o);
                return;
            } else {
                getMainView().onResponseEmpty("");
            }
            getMainView().hideProgress();
        }
    }

    @Override
    public void onSomethingWrongOnListOfProductsResponse(String s) {
        if (getMainView() != null) {
            getMainView().onResponseEmpty(s);
            getMainView().hideProgress();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (getMainView() != null) {
            getMainView().onResponseFailure(t);
            getMainView().hideProgress();
        }
    }
}
