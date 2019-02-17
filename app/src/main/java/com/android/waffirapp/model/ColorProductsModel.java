package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.android.waffirapp.my_interface.PhotoSliderComponent;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ColorProductsModel implements Parcelable, PhotoSliderComponent {

    private String ID;

    private String ColorName;
    private String Image;
    private String Name;


    public ColorProductsModel() {
    }

    @Override
    public String getItemId() {
        return getID();
    }

    @Override
    public String getImagePath() {
        return getImage();
    }

    @Override
    public String getSectionID() {
        return "";
    }

    @Override
    public String IsGif() {
        return "";
    }

    @Override
    public String ColorName() {
        return TextUtils.isEmpty(getColorName()) ? getName() : getColorName();
    }

    @Override
    public String ColorID() {
        return getID();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.ColorName);
        dest.writeString(this.Image);
        dest.writeString(this.Name);
    }

    protected ColorProductsModel(Parcel in) {
        this.ID = in.readString();
        this.ColorName = in.readString();
        this.Image = in.readString();
        this.Name = in.readString();
    }

    public static final Creator<ColorProductsModel> CREATOR = new Creator<ColorProductsModel>() {
        @Override
        public ColorProductsModel createFromParcel(Parcel source) {
            return new ColorProductsModel(source);
        }

        @Override
        public ColorProductsModel[] newArray(int size) {
            return new ColorProductsModel[size];
        }
    };
}
