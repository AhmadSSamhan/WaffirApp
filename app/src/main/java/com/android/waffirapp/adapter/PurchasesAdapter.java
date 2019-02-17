package com.android.waffirapp.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.model.ListProductModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Ahmad.Samhan.
 */
public class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.MyViewHolder> {
    private Context mContext;
    private List<ListProductModel> mList;

    public PurchasesAdapter(Context context, List<ListProductModel> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_complete_purchase, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ListProductModel productModel = mList.get(position);
        holder.mTvProductName.setText(productModel.getProductName());
        holder.mTvProductDesc.setText(productModel.getProductDesc());
        holder.mTvProductCount.setText(productModel.getProductCount());
        holder.mTvProductPrice.setText(productModel.getProductPrice());
        holder.mTvProductNotes.setText(productModel.getProductNotes());
        holder.mViewLine.setVisibility(View.VISIBLE);
        if (position == mList.size() - 1) {
            holder.mViewLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_product_name)
        TextView mTvProductName;
        @BindView(R.id.tv_product_desc)
        TextView mTvProductDesc;
        @BindView(R.id.tv_product_count)
        TextView mTvProductCount;
        @BindView(R.id.tv_product_price)
        TextView mTvProductPrice;
        @BindView(R.id.tv_product_notes)
        TextView mTvProductNotes;
        @BindView(R.id.view_line)
        View mViewLine;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
