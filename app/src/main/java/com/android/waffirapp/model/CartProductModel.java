package com.android.waffirapp.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartProductModel extends RealmObject {
    @Required
    private String id;
    private String imgPath;
    private String productName;
    private String productPrice;
    private String productColor;
    private String productCount;
    private String productNotes;

    public CartProductModel() {
    }

    public CartProductModel(String id) {
        this.id = id;
    }
}
