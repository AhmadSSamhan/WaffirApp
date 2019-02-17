package com.android.waffirapp.model;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

public class CartModel extends RealmObject {
    @Setter
    @Getter
    private int itemCount;
}
