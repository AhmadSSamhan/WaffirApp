package com.android.waffirapp.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListProductModel {

    private String imgPath;
    private String productName;
    private String productDesc;
    private String productDiscount;
    private String productPrice;
    private String productColor;
    private String productCount;
    private String productNotes;
    private String isHaveGifImage;

}
