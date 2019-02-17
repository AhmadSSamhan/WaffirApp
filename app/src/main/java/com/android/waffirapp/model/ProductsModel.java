package com.android.waffirapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.android.waffirapp.my_interface.ProductComponent;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductsModel implements Parcelable ,ProductComponent {
    private String Quntity;

    private String Description;

    private String Discount;

    private String Images;

    private String Name;

    private String Brand;

    private String Price;

    private String MaxQuntity;

    private String ID;

    private String MinQuntity;

    private String DiscountValue;

    private String IsShowQuntity;

    private boolean IsOffers;

    public ProductsModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Quntity);
        dest.writeString(this.Description);
        dest.writeString(this.Discount);
        dest.writeString(this.Images);
        dest.writeString(this.Name);
        dest.writeString(this.Brand);
        dest.writeString(this.Price);
        dest.writeString(this.MaxQuntity);
        dest.writeString(this.ID);
        dest.writeString(this.MinQuntity);
        dest.writeString(this.DiscountValue);
        dest.writeString(this.IsShowQuntity);
        dest.writeByte(this.IsOffers ? (byte) 1 : (byte) 0);
    }

    protected ProductsModel(Parcel in) {
        this.Quntity = in.readString();
        this.Description = in.readString();
        this.Discount = in.readString();
        this.Images = in.readString();
        this.Name = in.readString();
        this.Brand = in.readString();
        this.Price = in.readString();
        this.MaxQuntity = in.readString();
        this.ID = in.readString();
        this.MinQuntity = in.readString();
        this.DiscountValue = in.readString();
        this.IsShowQuntity = in.readString();
        this.IsOffers = in.readByte() != 0;
    }

    public static final Creator<ProductsModel> CREATOR = new Creator<ProductsModel>() {
        @Override
        public ProductsModel createFromParcel(Parcel source) {
            return new ProductsModel(source);
        }

        @Override
        public ProductsModel[] newArray(int size) {
            return new ProductsModel[size];
        }
    };

    @Override
    public String Quantity() {
        return getQuntity();
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
        return getImages();
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
        return getMaxQuntity();
    }

    @Override
    public String ID() {
        return getID();
    }

    @Override
    public String MinQuantity() {
        return getMinQuntity();
    }

    @Override
    public String DiscountValue() {
        return getDiscountValue();
    }

    @Override
    public String IsShowQuantity() {
        return getIsShowQuntity();
    }

    @Override
    public boolean IsOffers() {
        return isIsOffers();
    }
}
