package com.android.waffirapp.presentation.presenter.Login;

import com.android.waffirapp.model.LoginResponse;
/**
 * Created by Ahmad.Samhan.
 */
public interface LoginContract {
    interface presenter {

        void onDestroy();

        void requestLoginDataFromServer(String password, String userName);
    }

    interface MainView {

        void showProgress();

        void hideProgress();

        void setLoginResultToStore(LoginResponse result);

        void onResponseEmpty(String strNoData);

        void onResponseFailure(Throwable throwable);
    }

    interface GetLoginResultInteractor {

        interface OnFinishedListener {
            void onFinished(LoginResponse loginResponse);

            void onSomethingWrongOnLoginResponse(String s);

            void onFailure(Throwable t);
        }

        void getLoginResultResponse(String password, String userName, OnFinishedListener onFinishedListener);
    }
}
