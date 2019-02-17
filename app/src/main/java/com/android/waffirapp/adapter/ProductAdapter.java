package com.android.waffirapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.my_interface.ProductComponent;
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
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context mContext;
    private List<ProductComponent> mList;

    public ProductAdapter(Context context, List<ProductComponent> listProductModels) {
        this.mContext = context;
        this.mList = listProductModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.adapter_list_of_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductComponent productModel = mList.get(position);
        if (productModel == null) {
            holder.mCardRoot.setVisibility(View.INVISIBLE);
            return;
        }
        holder.mCardRoot.setVisibility(View.VISIBLE);
        holder.mTvProductBrand.setText(productModel.Brand());
        holder.mTvProductName.setText(productModel.Name());
        Utility.setLabelDiscount(productModel.IsOffers(), productModel.Discount(), productModel.DiscountValue(), holder.mTvProductDiscount);
        String fullPriceText = productModel.Price() + " " + mContext.getString(R.string.txt_currency_iraq);
        holder.mTvProductPrice.setText(fullPriceText);
        //Product Promo image
        int width = Utility.getImageDimensions(holder.mImgOffer).first;
        int height = Utility.getImageDimensions(holder.mImgOffer).second;
        Utility.setGlideDefaultImage(mContext, productModel.Images(), width, height, holder.mImgOffer);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_product)
        ImageView mImgOffer;
        @BindView(R.id.tv_product_brand)
        TextView mTvProductBrand;
        @BindView(R.id.tv_desc_product)
        TextView mTvProductName;
        @BindView(R.id.tv_product_discount)
        TextView mTvProductDiscount;
        @BindView(R.id.tv_product_price)
        TextView mTvProductPrice;
        @BindView(R.id.card_view_product)
        CardView mCardRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card_view_product)
        void onClickItem() {
            try {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(ConstantApp.REQ_SINGLE_PRODUCT_ID, mList.get(getAdapterPosition()).ID());
                mContext.startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(mContext, "Something happens , please try again later", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
