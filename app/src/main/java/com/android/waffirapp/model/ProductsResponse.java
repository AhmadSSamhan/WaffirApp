package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ProductsResponse implements Parcelable {

    private List<ProductsModel> Products;
    private String Gif;

    private String CategoryName;

    private String IsAdvertising;

    private String IsClick;

    private String SectionName;

    private String AdvertisingID;

    private String SectionID;

    private String Advertising;

    private String TitleAdvertising;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.Products);
        dest.writeString(this.Gif);
        dest.writeString(this.CategoryName);
        dest.writeString(this.IsAdvertising);
        dest.writeString(this.IsClick);
        dest.writeString(this.SectionName);
        dest.writeString(this.AdvertisingID);
        dest.writeString(this.SectionID);
        dest.writeString(this.Advertising);
        dest.writeString(this.TitleAdvertising);
    }

    public ProductsResponse() {
    }

    protected ProductsResponse(Parcel in) {
        this.Products = in.createTypedArrayList(ProductsModel.CREATOR);
        this.Gif = in.readString();
        this.CategoryName = in.readString();
        this.IsAdvertising = in.readString();
        this.IsClick = in.readString();
        this.SectionName = in.readString();
        this.AdvertisingID = in.readString();
        this.SectionID = in.readString();
        this.Advertising = in.readString();
        this.TitleAdvertising = in.readString();
    }

    public static final Parcelable.Creator<ProductsResponse> CREATOR = new Parcelable.Creator<ProductsResponse>() {
        @Override
        public ProductsResponse createFromParcel(Parcel source) {
            return new ProductsResponse(source);
        }

        @Override
        public ProductsResponse[] newArray(int size) {
            return new ProductsResponse[size];
        }
    };
}
