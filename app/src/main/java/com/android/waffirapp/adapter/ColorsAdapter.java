package com.android.waffirapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.model.ColorProductsModel;
import com.android.waffirapp.my_interface.CallBackChooseColor;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.MyViewHolder> {
    private Context mContext;
    private List<ColorProductsModel> mList;
    private CallBackChooseColor mCallBackChooseColor;

    public ColorsAdapter(Context context, List<ColorProductsModel> colorsModels) {
        this.mContext = context;
        this.mList = colorsModels;
    }

    public ColorsAdapter setCallBack(CallBackChooseColor callBack) {
        this.mCallBackChooseColor = callBack;
        return this;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_string_select_item_dialog, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ColorProductsModel colorProductsModels = mList.get(position);
        holder.mTvColor.setText(colorProductsModels.getColorName());
        holder.mViewLine.setVisibility(View.VISIBLE);
        if (getItemCount() - 1 == position) {
            holder.mViewLine.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_string)
        TextView mTvColor;
        @BindView(R.id.view_line_string)
        View mViewLine;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.container_select_item_string)
        protected void onClick() {
            if (mCallBackChooseColor != null) {
                mCallBackChooseColor.onChooseColor(mList.get(getAdapterPosition()));
            }
        }
    }
}
