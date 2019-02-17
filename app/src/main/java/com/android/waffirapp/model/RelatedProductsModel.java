package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.waffirapp.my_interface.ProductComponent;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RelatedProductsModel implements Parcelable, ProductComponent {

    private String Brand;

    private String Description;

    private String Discount;

    private String Price;

    private boolean ISOffers;

    private String ID;

    private String Image;

    private String DiscountValue;

    private String Name;

    public RelatedProductsModel() {
    }

    @Override
    public String Quantity() {
        return "";
    }

    @Override
    public String Description() {
        return getDescription();
    }

    @Override
    public String Discount() {
        return getDiscount();
    }

    @Override
    public String Images() {
        return getImage();
    }

    @Override
    public String Name() {
        return getName();
    }

    @Override
    public String Brand() {
        return getBrand();
    }

    @Override
    public String Price() {
        return getPrice();
    }

    @Override
    public String MaxQuantity() {
        return "";
    }

    @Override
    public String ID() {
        return getID();
    }

    @Override
    public String MinQuantity() {
        return "";
    }

    @Override
    public String DiscountValue() {
        return getDiscountValue();
    }

    @Override
    public String IsShowQuantity() {
        return "";
    }

    @Override
    public boolean IsOffers() {
        return isISOffers();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Brand);
        dest.writeString(this.Description);
        dest.writeString(this.Discount);
        dest.writeString(this.Price);
        dest.writeByte(this.ISOffers ? (byte) 1 : (byte) 0);
        dest.writeString(this.ID);
        dest.writeString(this.Image);
        dest.writeString(this.DiscountValue);
        dest.writeString(this.Name);
    }

    protected RelatedProductsModel(Parcel in) {
        this.Brand = in.readString();
        this.Description = in.readString();
        this.Discount = in.readString();
        this.Price = in.readString();
        this.ISOffers = in.readByte() != 0;
        this.ID = in.readString();
        this.Image = in.readString();
        this.DiscountValue = in.readString();
        this.Name = in.readString();
    }

    public static final Creator<RelatedProductsModel> CREATOR = new Creator<RelatedProductsModel>() {
        @Override
        public RelatedProductsModel createFromParcel(Parcel source) {
            return new RelatedProductsModel(source);
        }

        @Override
        public RelatedProductsModel[] newArray(int size) {
            return new RelatedProductsModel[size];
        }
    };
}
