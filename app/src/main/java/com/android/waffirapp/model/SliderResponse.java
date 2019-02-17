package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SliderResponse implements Parcelable {
    private List<SliderComponent> Slider;

    private String Gif;

    private String ImageOffers;

    private String IsClick;

    private String ID;

    private String CatID;

    private String Name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.Slider);
        dest.writeString(this.Gif);
        dest.writeString(this.ImageOffers);
        dest.writeString(this.IsClick);
        dest.writeString(this.ID);
        dest.writeString(this.CatID);
        dest.writeString(this.Name);
    }

    public SliderResponse() {
    }

    protected SliderResponse(Parcel in) {
        this.Slider = in.createTypedArrayList(SliderComponent.CREATOR);
        this.Gif = in.readString();
        this.ImageOffers = in.readString();
        this.IsClick = in.readString();
        this.ID = in.readString();
        this.CatID = in.readString();
        this.Name = in.readString();
    }

    public static final Parcelable.Creator<SliderResponse> CREATOR = new Parcelable.Creator<SliderResponse>() {
        @Override
        public SliderResponse createFromParcel(Parcel source) {
            return new SliderResponse(source);
        }

        @Override
        public SliderResponse[] newArray(int size) {
            return new SliderResponse[size];
        }
    };
}
