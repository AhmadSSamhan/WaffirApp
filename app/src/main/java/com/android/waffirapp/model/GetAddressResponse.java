package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetAddressResponse implements Parcelable {
    @Setter
    @Getter
    private List<AddressModel> Address;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.Address);
    }

    public GetAddressResponse() {
    }

    protected GetAddressResponse(Parcel in) {
        this.Address = in.createTypedArrayList(AddressModel.CREATOR);
    }

    public static final Parcelable.Creator<GetAddressResponse> CREATOR = new Parcelable.Creator<GetAddressResponse>() {
        @Override
        public GetAddressResponse createFromParcel(Parcel source) {
            return new GetAddressResponse(source);
        }

        @Override
        public GetAddressResponse[] newArray(int size) {
            return new GetAddressResponse[size];
        }
    };
}
