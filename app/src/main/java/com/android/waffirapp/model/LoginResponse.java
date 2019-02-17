package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse implements Parcelable {

    private String GenderID;

    private String Email;

    private String MsgErorre;

    private String Address;

    private String UserID;

    private String FirstName;

    private String Images;

    private String LastName;

    private String Mobile;

    private String Password;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.GenderID);
        dest.writeString(this.Email);
        dest.writeString(this.MsgErorre);
        dest.writeString(this.Address);
        dest.writeString(this.UserID);
        dest.writeString(this.FirstName);
        dest.writeString(this.Images);
        dest.writeString(this.LastName);
        dest.writeString(this.Mobile);
        dest.writeString(this.Password);
    }

    public LoginResponse() {
    }

    protected LoginResponse(Parcel in) {
        this.GenderID = in.readString();
        this.Email = in.readString();
        this.MsgErorre = in.readString();
        this.Address = in.readString();
        this.UserID = in.readString();
        this.FirstName = in.readString();
        this.Images = in.readString();
        this.LastName = in.readString();
        this.Mobile = in.readString();
        this.Password = in.readString();
    }

    public static final Parcelable.Creator<LoginResponse> CREATOR = new Parcelable.Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
