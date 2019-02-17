package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.waffirapp.my_interface.PhotoSliderComponent;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SliderComponent implements Parcelable, PhotoSliderComponent {

    private String IsGif;

    private String ID;

    private String Image;

    private String SectionID;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.IsGif);
        dest.writeString(this.ID);
        dest.writeString(this.Image);
        dest.writeString(this.SectionID);
    }

    public SliderComponent() {
    }

    protected SliderComponent(Parcel in) {
        this.IsGif = in.readString();
        this.ID = in.readString();
        this.Image = in.readString();
        this.SectionID = in.readString();
    }

    public static final Parcelable.Creator<SliderComponent> CREATOR = new Parcelable.Creator<SliderComponent>() {
        @Override
        public SliderComponent createFromParcel(Parcel source) {
            return new SliderComponent(source);
        }

        @Override
        public SliderComponent[] newArray(int size) {
            return new SliderComponent[size];
        }
    };

    @Override
    public String getItemId() {
        return ID;
    }

    @Override
    public String getImagePath() {
        return getImage();
    }

    @Override
    public String IsGif() {
        return getIsGif();
    }

    @Override
    public String ColorName() {
        return "";
    }

    @Override
    public String ColorID() {
        return "";
    }
}
