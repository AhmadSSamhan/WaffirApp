package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EmptyResponse implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public EmptyResponse() {
    }

    protected EmptyResponse(Parcel in) {
    }

    public static final Parcelable.Creator<EmptyResponse> CREATOR = new Parcelable.Creator<EmptyResponse>() {
        @Override
        public EmptyResponse createFromParcel(Parcel source) {
            return new EmptyResponse(source);
        }

        @Override
        public EmptyResponse[] newArray(int size) {
            return new EmptyResponse[size];
        }
    };
}
