package com.android.waffirapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.ListOfProducts.ListOfProductVIanHolder;
import com.android.waffirapp.adapter.ListOfProducts.ProgressViewHolder;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.ProductsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListOfProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ProductsModel> mList;
    // The minimum amount of items to have below your current scroll position
    // before isLoading more.
    private boolean isLoading = false;


    public ListOfProductsAdapter(Context context, List<ProductsModel> listProductModels) {
        this.mContext = context;
        this.mList = listProductModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder myViewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ConstantApp.VIEW_TYPE_ITEM:
                View viewItem = layoutInflater.inflate(R.layout.adapter_list_all_products, parent, false);
                myViewHolder = new ListOfProductVIanHolder(viewItem);
                break;
            case ConstantApp.VIEW_TYPE_LOADING:
                View viewItemLoad = layoutInflater.inflate(R.layout.layout_progress_bar, parent, false);
                myViewHolder = new ProgressViewHolder(viewItemLoad);
                break;
        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ConstantApp.VIEW_TYPE_ITEM:
                ((ListOfProductVIanHolder) holder).setData(mContext, mList);
                break;
            case ConstantApp.VIEW_TYPE_LOADING:
                ((ProgressViewHolder) holder).setData(mContext);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) == null ? ConstantApp.VIEW_TYPE_LOADING : ConstantApp.VIEW_TYPE_ITEM;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }

    public boolean isLoading() {
        return isLoading;
    }

    // Add new object ProductsModel and update list
    public void addEmptyObj() {
        if (mList.isEmpty()) {
            return;
        }
        mList.add(null);
        notifyItemInserted(mList.size() - 1);
    }

    // Add new object ProductsModel and update list
    public void removeEmptyObj() {
        if (mList.isEmpty()) {
            return;
        }
        mList.remove(mList.size() - 1);
        notifyItemRemoved(mList.size());
    }

    public void updateDataRecycleView() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

}
