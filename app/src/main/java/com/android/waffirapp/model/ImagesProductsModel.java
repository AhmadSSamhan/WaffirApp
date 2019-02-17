package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.waffirapp.my_interface.PhotoSliderComponent;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ImagesProductsModel implements Parcelable,PhotoSliderComponent {
    private String ID;

    private String Image;

    private String ColorName;

    private String ColorID;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.Image);
        dest.writeString(this.ColorName);
        dest.writeString(this.ColorID);
    }

    public ImagesProductsModel() {
    }

    protected ImagesProductsModel(Parcel in) {
        this.ID = in.readString();
        this.Image = in.readString();
        this.ColorName = in.readString();
        this.ColorID = in.readString();
    }

    public static final Parcelable.Creator<ImagesProductsModel> CREATOR = new Parcelable.Creator<ImagesProductsModel>() {
        @Override
        public ImagesProductsModel createFromParcel(Parcel source) {
            return new ImagesProductsModel(source);
        }

        @Override
        public ImagesProductsModel[] newArray(int size) {
            return new ImagesProductsModel[size];
        }
    };

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
        return getColorName();
    }

    @Override
    public String ColorID() {
        return getColorID();
    }
}
