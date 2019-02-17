package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListOfProductResponse implements Parcelable {

    private List<ProductsModel> Products;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.Products);
    }

    public ListOfProductResponse() {
    }

    protected ListOfProductResponse(Parcel in) {
        this.Products = in.createTypedArrayList(ProductsModel.CREATOR);
    }

    public static final Parcelable.Creator<ListOfProductResponse> CREATOR = new Parcelable.Creator<ListOfProductResponse>() {
        @Override
        public ListOfProductResponse createFromParcel(Parcel source) {
            return new ListOfProductResponse(source);
        }

        @Override
        public ListOfProductResponse[] newArray(int size) {
            return new ListOfProductResponse[size];
        }
    };
}
