package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ProvincesResponse implements Parcelable {
    @Setter @Getter
    private List<ProvincesModel> Provinces;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.Provinces);
    }

    public ProvincesResponse() {
    }

    protected ProvincesResponse(Parcel in) {
        this.Provinces = in.createTypedArrayList(ProvincesModel.CREATOR);
    }

    public static final Parcelable.Creator<ProvincesResponse> CREATOR = new Parcelable.Creator<ProvincesResponse>() {
        @Override
        public ProvincesResponse createFromParcel(Parcel source) {
            return new ProvincesResponse(source);
        }

        @Override
        public ProvincesResponse[] newArray(int size) {
            return new ProvincesResponse[size];
        }
    };
}
