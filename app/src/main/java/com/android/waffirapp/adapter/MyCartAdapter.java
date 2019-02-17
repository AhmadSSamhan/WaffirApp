package com.android.waffirapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.model.CartProductModel;
import com.android.waffirapp.util.Utility;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private Context mContext;
    private List<CartProductModel> mList;
    private CallBack mCallBack;
    private int mCount;

    public MyCartAdapter(Context context, List<CartProductModel> listProductModelList, CallBack callBack) {
        this.mContext = context;
        this.mCallBack = callBack;
        this.mList = listProductModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new MyCartAdapter.ViewHolder(layoutInflater.inflate(R.layout.adapter_my_cart, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartProductModel productModel = mList.get(position);
        if (productModel == null) {
            holder.mCardViewRoot.setVisibility(View.INVISIBLE);
            return;
        }
        mCount = Integer.valueOf(productModel.getProductCount());
        holder.mCardViewRoot.setVisibility(View.VISIBLE);
        holder.mTvProductDesc.setText(productModel.getProductName());
        holder.mTvProductColor.setText(productModel.getProductColor());
        holder.mTvProductPrice.setText(productModel.getProductPrice());
        holder.mTvQuantityCount.setText(productModel.getProductCount());
        //Product Promo image
        int width = Utility.getImageDimensions(holder.mImgItem).first;
        int height = Utility.getImageDimensions(holder.mImgItem).second;
        Utility.setGlideDefaultImage(mContext, productModel.getImgPath(), width, height, holder.mImgItem);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateList(int adapterPosition) {
        mList.remove(adapterPosition);
        // Last two item in list is null object reference, so we need to remove it from list
        // to update ui on CartFragment()
        if (mList.size() == 2) {
            mList.clear();
            refreshAdapterSize();
        }
        notifyDataSetChanged();
    }

    private void refreshAdapterSize() {
        if (mCallBack != null) {
            mCallBack.getUpdateOnListSize(mList);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_product_cart)
        ImageView mImgItem;
        @BindView(R.id.img_increase_quantity)
        ImageView mImgIncreaseQuantity;
        @BindView(R.id.img_decrease_quantity)
        ImageView mImgDecreaseQuantity;
        @BindView(R.id.tv_quantity_count)
        TextView mTvQuantityCount;
        @BindView(R.id.tv_desc_product_cart)
        TextView mTvProductDesc;
        @BindView(R.id.tv_product_cart_color)
        TextView mTvProductColor;
        @BindView(R.id.tv_product_cart_price)
        TextView mTvProductPrice;
        @BindView(R.id.tv_product_cart_note)
        TextView mTvProductNote;
        @BindView(R.id.card_view_cart_item)
        FrameLayout mCardViewRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.img_remove_item)
        void onClickItem() {
            if (refreshAdapter()) return;
            Toast.makeText(mContext, mList.get(getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
        }

        private boolean refreshAdapter() {
            if (mCallBack != null && mList.get(getAdapterPosition()) != null) {
                mCallBack.setUpdateOnList(mList.get(getAdapterPosition()), getAdapterPosition());
                return true;
            }
            return false;
        }

        @OnClick(R.id.img_increase_quantity)
        void increaseQuantity() {
            if (mCount <= 0) {
                return;
            }
            mCount--;
            setTextToQuantity();
        }

        @OnClick(R.id.img_decrease_quantity)
        void decreaseQuantity() {
            mCount++;
            setTextToQuantity();
        }

        private void setTextToQuantity() {
            String txt = "" + mCount;
            mTvQuantityCount.setText(txt);
        }
    }

    public interface CallBack {
        void setUpdateOnList(CartProductModel productModel, int adapterPosition);

        void getUpdateOnListSize(List<CartProductModel> list);
    }
}
