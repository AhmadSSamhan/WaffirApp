package com.android.waffirapp.presentation.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.MyCartAdapter;
import com.android.waffirapp.dialog.CompletePurchaseDialog;
import com.android.waffirapp.dialog.GeneralDialog;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.CartProductModel;
import com.android.waffirapp.my_interface.CallBackCartCount;
import com.android.waffirapp.my_interface.CallBackCompleteDialog;
import com.android.waffirapp.presentation.ui.BaseFragment;
import com.android.waffirapp.presentation.ui.activity.CompletePurchaseActivity;
import com.android.waffirapp.realm.RealmController;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * Created by Ahmad.Samhan.
 */
public class CartFragment extends BaseFragment implements CallBackCompleteDialog, MyCartAdapter.CallBack {
    @BindView(R.id.recycler_view_list_cart)
    RecyclerView mRecyclerView;
    @BindView(R.id.content_complete_action)
    View mViewCompleteAction;
    private RealmResults<CartProductModel> listProductModelList;
    private List<CartProductModel> mListAllProduct = new ArrayList<>();
    private CallBackCartCount mCallBackCarCount;
    private MyCartAdapter mAdapter;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mCallBackCarCount == null) {
            mCallBackCarCount = (CallBackCartCount) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, inflate);
        RealmController.create();
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        listProductModelList = RealmController.getInstance().getCartShoppingProductList();
        mListAllProduct.addAll(listProductModelList);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleToolbarFragment("My Cart");
        if (setVisibleCompleteAction()) return;
        setDataToRecycleView();
    }

    private void setTotalCountOnCartShopping() {
        if (mCallBackCarCount != null) {
            mCallBackCarCount.onTotalItemCountOnCart(listProductModelList.size());
        }
    }

    private void setDataToRecycleView() {
        if (mListAllProduct.size() == 0) {
            setTotalCountOnCartShopping();
            return;
        }
        mListAllProduct.add(null);
        mListAllProduct.add(null);
        mAdapter = new MyCartAdapter(getContext(), mListAllProduct, this);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.tv_complete_purchase)
    void onClickCompletePurchase() {
        CompletePurchaseDialog purchaseDialog = new CompletePurchaseDialog(getContext());
        purchaseDialog.show();
        purchaseDialog.setTitle("Your cart is full \n Do you wish to continue or create a new cart?");
        purchaseDialog.setTextOptionOne(R.string.txt_receipt_from_the_center);
        purchaseDialog.setTextOptionTwo(R.string.txt_delivery);
        purchaseDialog.setTypeOptionOne(ConstantApp.TYPE_TAKE_ORDER_FROM_CENTER);
        purchaseDialog.setTypeOptionTwo(ConstantApp.TYPE_TAKE_ORDER_TO_HOME);
        purchaseDialog.setCallBack(this);
    }

    @Override
    public void onCompletePurchaseDialog(String type) {
        if (type.equalsIgnoreCase(ConstantApp.TYPE_TAKE_ORDER_FROM_CENTER)) {
            setActionToCompletePurchaseActivity(false);
            return;
        }
        setActionToCompletePurchaseActivity(true);
    }

    private void setActionToCompletePurchaseActivity(boolean showDeliveryPart) {
        Intent intent = new Intent(getContext(), CompletePurchaseActivity.class);
        intent.putExtra(ConstantApp.SHOW_DELIVERY_COMPLETE_PURCHASE, showDeliveryPart);
        getContext().startActivity(intent);
    }

    @Override
    public void setUpdateOnList(CartProductModel productModel, int adapterPosition) {
        if (setVisibleCompleteAction()) return;
        GeneralDialog generalDialog = new GeneralDialog(getContext());
        generalDialog.setCallBack(new GeneralDialog.CallBackGeneralDialog() {
            @Override
            public void onCallBackGeneralDialogButtonOne() {

            }

            @Override
            public void onCallBackGeneralDialogButtonTwo() {
                try {
                    RealmController.getInstance().deleteCartProductList(productModel);
                } catch (Exception e) {
                    e.getMessage();
                    //TODO remove try/catch after using API of cart
                }
                mAdapter.updateList(adapterPosition);
                updateBadgeCount();
            }
        });
        generalDialog.show();
        generalDialog.setTitleDialog("Do you want to delete this item ?");
        generalDialog.setButtonOneVisible(View.VISIBLE);
        generalDialog.setButtonTwoVisible(View.VISIBLE);
    }

    @Override
    public void getUpdateOnListSize(List<CartProductModel> list) {
        setVisibleCompleteAction();
    }

    private boolean setVisibleCompleteAction() {
        if (listProductModelList.size() == 0) {
            mViewCompleteAction.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }

    private void updateBadgeCount() {
        setTotalCountOnCartShopping();
    }

    @Override
    public void onDestroyView() {
        try {
            RealmController.getInstance().unbindController();
        } catch (Exception e) {
            e.getMessage();
            //TODO remove try/catch after using API of cart
        }
        super.onDestroyView();
    }
}
