package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ahmad.Samhan.
 */
@Setter
@Getter
public class AddressModel implements Parcelable {

    private String Provinces;

    private String StreetTow;

    private String Note;

    private String ProvincesID;

    private String Latitude;

    private String ID;

    private String CustomerID;

    private String Billding;

    private String Mobile;

    private String Longitude;

    private String StreetOne;

    private String Name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Provinces);
        dest.writeString(this.StreetTow);
        dest.writeString(this.Note);
        dest.writeString(this.ProvincesID);
        dest.writeString(this.Latitude);
        dest.writeString(this.ID);
        dest.writeString(this.CustomerID);
        dest.writeString(this.Billding);
        dest.writeString(this.Mobile);
        dest.writeString(this.Longitude);
        dest.writeString(this.StreetOne);
        dest.writeString(this.Name);
    }

    public AddressModel() {
    }

    protected AddressModel(Parcel in) {
        this.Provinces = in.readString();
        this.StreetTow = in.readString();
        this.Note = in.readString();
        this.ProvincesID = in.readString();
        this.Latitude = in.readString();
        this.ID = in.readString();
        this.CustomerID = in.readString();
        this.Billding = in.readString();
        this.Mobile = in.readString();
        this.Longitude = in.readString();
        this.StreetOne = in.readString();
        this.Name = in.readString();
    }

    public static final Parcelable.Creator<AddressModel> CREATOR = new Parcelable.Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel source) {
            return new AddressModel(source);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };
}
