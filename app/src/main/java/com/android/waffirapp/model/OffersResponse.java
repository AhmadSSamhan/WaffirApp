package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OffersResponse implements Parcelable {
    private List<OffersModel> Offers;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.Offers);
    }

    public OffersResponse() {
    }

    protected OffersResponse(Parcel in) {
        this.Offers = in.createTypedArrayList(OffersModel.CREATOR);
    }

    public static final Parcelable.Creator<OffersResponse> CREATOR = new Parcelable.Creator<OffersResponse>() {
        @Override
        public OffersResponse createFromParcel(Parcel source) {
            return new OffersResponse(source);
        }

        @Override
        public OffersResponse[] newArray(int size) {
            return new OffersResponse[size];
        }
    };
}
