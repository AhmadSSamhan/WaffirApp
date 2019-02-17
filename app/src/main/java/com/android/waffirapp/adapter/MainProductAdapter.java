package com.android.waffirapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.ProductsResponse;
import com.android.waffirapp.my_interface.ProductComponent;
import com.android.waffirapp.presentation.ui.activity.ListOfProductsActivity;
import com.android.waffirapp.util.Utility;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.ViewHolder> {
    private Context mContext;
    private List<ProductsResponse> mList;

    public MainProductAdapter(Context context, List<ProductsResponse> productsResponses) {
        this.mContext = context;
        this.mList = productsResponses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_main_all_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsResponse productModel = mList.get(position);
        holder.mRootLayout.setVisibility(View.VISIBLE);
        if (productModel == null) {
            holder.mRootLayout.setVisibility(View.INVISIBLE);
            return;
        }
        //Product title
        holder.mTvProductTitle.setText(productModel.getSectionName());
        if (!TextUtils.isEmpty(productModel.getAdvertising())) {
            showViewStubPromoLabel(holder, productModel.getAdvertising());
        }
        //Product list of item
        List<ProductComponent> productComponents = new ArrayList<>(productModel.getProducts());
        productComponents.add(null);
        setDataToRecycleView(holder.mRecyclerView, productComponents);
    }

    private void showViewStubPromoLabel(ViewHolder holder, String imgProductPromo) {
        if (holder.mViewStubPromoLabel != null) {
            // If you want to change data of ViewStub at runtime, you can do like this
            View inflatedView = holder.mViewStubPromoLabel.inflate();// inflate the layout
            ImageView imgGift = inflatedView.findViewById(R.id.img_gift_offer);
            Utility.setGlideGiftImage(mContext, imgProductPromo, imgGift);
            holder.mViewStubPromoLabel.setVisibility(View.VISIBLE);
        }
    }

    private void setDataToRecycleView(RecyclerView recyclerView, List<ProductComponent> listOfProduct) {
        if (listOfProduct.size() == 0) {
            return;
        }
        ProductAdapter adapter = new ProductAdapter(mContext, listOfProduct);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.container_main_all_product)
        ConstraintLayout mRootLayout;
        @BindView(R.id.view_stub_promo_label)
        ViewStub mViewStubPromoLabel;
        @BindView(R.id.recycler_view_product)
        RecyclerView mRecyclerView;
        @BindView(R.id.tv_product_label)
        TextView mTvProductTitle;
        @BindView(R.id.tv_view_all)
        TextView mTvViewAllProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.view_stub_promo_label)
        void onClickItem() {
            Toast.makeText(mContext, mList.get(getAdapterPosition()).getSectionName(), Toast.LENGTH_SHORT).show();
        }

        @OnClick(R.id.tv_view_all)
        void onClickViewAllItem() {
            Intent i = new Intent(mContext, ListOfProductsActivity.class);
            i.putExtra(ConstantApp.REQ_SECTION_ID, mList.get(getAdapterPosition()).getSectionID());
            i.putExtra(ConstantApp.REQ_SECTION_NAME, mList.get(getAdapterPosition()).getSectionName());
            mContext.startActivity(i);
        }
    }
}
