package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OffersModel implements Parcelable {
    private String Category;
    private String Gif;
    private String ID;
    private String Image;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Category);
        dest.writeString(this.Gif);
        dest.writeString(this.ID);
        dest.writeString(this.Image);
    }

    public OffersModel() {
    }

    protected OffersModel(Parcel in) {
        this.Category = in.readString();
        this.Gif = in.readString();
        this.ID = in.readString();
        this.Image = in.readString();
    }

    public static final Parcelable.Creator<OffersModel> CREATOR = new Parcelable.Creator<OffersModel>() {
        @Override
        public OffersModel createFromParcel(Parcel source) {
            return new OffersModel(source);
        }

        @Override
        public OffersModel[] newArray(int size) {
            return new OffersModel[size];
        }
    };
}
