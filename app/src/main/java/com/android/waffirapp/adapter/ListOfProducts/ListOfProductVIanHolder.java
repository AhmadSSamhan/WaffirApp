package com.android.waffirapp.adapter.ListOfProducts;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.ProductsModel;
import com.android.waffirapp.presentation.ui.activity.DetailActivity;
import com.android.waffirapp.util.Utility;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class ListOfProductVIanHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.img_item_product)
    ImageView mImgOffer;
    @BindView(R.id.tv_product_brand)
    TextView mTvProductTitle;
    @BindView(R.id.tv_desc_product)
    TextView mTvProductDesc;
    @BindView(R.id.tv_product_discount)
    TextView mTvProductDiscount;
    @BindView(R.id.tv_product_price)
    TextView mTvProductPrice;
    @BindView(R.id.card_view_products_item)
    CardView mCardViewRoot;
    @BindView(R.id.view_empty_span)
    View mViewEmptySpan;
    private Context mContext;
    private List<ProductsModel> mListModel;

    public ListOfProductVIanHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @OnClick(R.id.card_view_products_item)
    void onClickItem() {
        try {
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(ConstantApp.REQ_SINGLE_PRODUCT_ID, mListModel.get(getAdapterPosition()).getID());
            mContext.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(mContext, "Something happens , please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(Context context, List<ProductsModel> productsModels) {
        this.mContext = context;
        this.mListModel = productsModels;
        bindData();
    }

    private void bindData() {
        if (mListModel == null || mListModel.isEmpty()) {
            mCardViewRoot.setVisibility(View.INVISIBLE);
            return;
        }
        mViewEmptySpan.setVisibility(View.GONE);
        if ((mListModel.size() - 1) == getAdapterPosition()) {
            mViewEmptySpan.setVisibility(View.VISIBLE);
            return;
        }
        mCardViewRoot.setVisibility(View.VISIBLE);
        ProductsModel productsModel = mListModel.get(getAdapterPosition());
        mTvProductTitle.setText(productsModel.getBrand());
        mTvProductDesc.setText(productsModel.getName());
        Utility.setLabelDiscount(productsModel.isIsOffers(), productsModel.getDiscount(), productsModel.getDiscountValue(), mTvProductDiscount);
        mTvProductPrice.setText(productsModel.getPrice());
        //Product Promo image
        int width = Utility.getImageDimensions(mImgOffer).first;
        int height = Utility.getImageDimensions(mImgOffer).second;
        Utility.setGlideDefaultImage(mContext, productsModel.getImages(), width, height, mImgOffer);
    }
}
