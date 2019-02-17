package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProvincesModel implements Parcelable {

    private String ID;

    private String Name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.Name);
    }

    public ProvincesModel() {
    }

    protected ProvincesModel(Parcel in) {
        this.ID = in.readString();
        this.Name = in.readString();
    }

    public static final Parcelable.Creator<ProvincesModel> CREATOR = new Parcelable.Creator<ProvincesModel>() {
        @Override
        public ProvincesModel createFromParcel(Parcel source) {
            return new ProvincesModel(source);
        }

        @Override
        public ProvincesModel[] newArray(int size) {
            return new ProvincesModel[size];
        }
    };
}
