package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SingleProductsResponse implements Parcelable {

    private List<List<ColorProductsModel>> ColorProducts;
    private String CategoryID;
    private String ProductName;
    private String ProductDesc;
    private String CategoryName;
    private String ProductPrice;
    private String ProductDiscount;
    private String ProductDiscountValue;
    private boolean IsOffers;
    private String ImagesPices;
    private String NamePices;
    private String DescPices;
    private String IsFavorite;
    private String Brand;
    private boolean IsShowQuntity;
    private String ProductStok;
    private String MaxQuntity;
    private String MinQuntity;
    private List<RelatedProductsModel> RelatedProducts;
    private List<ImagesProductsModel> ImagesProducts;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CategoryID);
    }

    public SingleProductsResponse() {
    }

    protected SingleProductsResponse(Parcel in) {
        this.CategoryID = in.readString();
    }

    public static final Parcelable.Creator<SingleProductsResponse> CREATOR = new Parcelable.Creator<SingleProductsResponse>() {
        @Override
        public SingleProductsResponse createFromParcel(Parcel source) {
            return new SingleProductsResponse(source);
        }

        @Override
        public SingleProductsResponse[] newArray(int size) {
            return new SingleProductsResponse[size];
        }
    };
}
