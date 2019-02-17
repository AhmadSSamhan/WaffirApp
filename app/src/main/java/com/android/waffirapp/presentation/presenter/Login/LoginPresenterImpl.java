package com.android.waffirapp.presentation.presenter.Login;

import com.android.waffirapp.model.LoginResponse;
/**
 * Created by Ahmad.Samhan.
 */
public class LoginPresenterImpl implements LoginContract.presenter, LoginContract.GetLoginResultInteractor.OnFinishedListener {

    private LoginContract.MainView mMainView;
    private LoginContract.GetLoginResultInteractor mGetLoginResultInteractor;

    public LoginPresenterImpl(LoginContract.MainView mainView, LoginContract.GetLoginResultInteractor loginResultInteractor) {
        this.mMainView = mainView;
        this.mGetLoginResultInteractor = loginResultInteractor;
    }

    @Override
    public void onDestroy() {
        mMainView = null;
    }

    @Override
    public void requestLoginDataFromServer(String password, String userName) {
        if (getMainView() != null) {
            getMainView().showProgress();
        }
        mGetLoginResultInteractor.getLoginResultResponse(password, userName, this);
    }

    @Override
    public void onFinished(LoginResponse loginResponse) {
        if (getMainView() != null) {
            getMainView().setLoginResultToStore(loginResponse);
            getMainView().hideProgress();
        }
    }

    @Override
    public void onSomethingWrongOnLoginResponse(String s) {
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

    private LoginContract.MainView getMainView() {
        return mMainView;
    }
}
