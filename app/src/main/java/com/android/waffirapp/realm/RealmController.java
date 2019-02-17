package com.android.waffirapp.realm;

import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.CartProductModel;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Ahmad.Samhan on 2019-01-20.
 */

public class RealmController {
    private static RealmController instance;
    private Realm realm;


    private RealmController() {
        realm = Realm.getDefaultInstance();
    }

    /**
     * Create an instance of Realm Controller
     */
    public static RealmController create() {
        if (instance == null) {
            instance = new RealmController();
        }
        return instance;
    }

    public static void init() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(ConstantApp.PATH_WAFFIR_ITEM)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    private void returnOpenRealmIfClose() {
        if (isClosed()) {
            realm = Realm.getDefaultInstance();
        }
    }

    private boolean isClosed() {
        return getRealm().isClosed();
    }

    /**
     * Close Realm
     */
    public void unbindController() {
        getRealm().close();
    }


    public void addCartShoppingItemToList(CartProductModel productModel) {
        returnOpenRealmIfClose();
        getRealm().executeTransaction(realm -> {
            CartProductModel cartProductModel = realm.createObject(CartProductModel.class);
            cartProductModel.setId(productModel.getId());
            cartProductModel.setImgPath(productModel.getImgPath());
            cartProductModel.setProductName(productModel.getProductName());
            cartProductModel.setProductColor(productModel.getProductColor());
            cartProductModel.setProductPrice(productModel.getProductPrice());
            cartProductModel.setProductCount(productModel.getProductCount());
            cartProductModel.setProductNotes(productModel.getProductNotes());
            setCartProductList(cartProductModel);
            realm.insertOrUpdate(cartProductModel);
        });
    }

    private void setCartProductList(CartProductModel cartProductModel) {
        RealmList<CartProductModel> mRealmList = new RealmList<>();
        mRealmList.add(cartProductModel);
    }

    public RealmResults<CartProductModel> getCartShoppingProductList() {
        returnOpenRealmIfClose();
        return getRealm().where(CartProductModel.class).findAll();
    }

    public void deleteCartProductList(CartProductModel productModel) {
        final String objId = productModel.getId();
        returnOpenRealmIfClose();
        getRealm().executeTransaction(realm -> {
            RealmResults<CartProductModel> result = realm.where(CartProductModel.class).equalTo("id", objId).findAll();
            result.deleteAllFromRealm();
        });
    }
}
