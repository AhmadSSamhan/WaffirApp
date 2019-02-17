package com.android.waffirapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.model.AddressModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private Context mContext;
    private List<AddressModel> mList;
    private CallBackAddress mCallBackAddress;

    public AddressAdapter(Context context, List<AddressModel> colorsModels, CallBackAddress callBackAddress) {
        this.mContext = context;
        this.mList = colorsModels;
        this.mCallBackAddress = callBackAddress;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_row_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AddressModel addressModel = mList.get(position);
        holder.mTvAddressName.setVisibility(View.VISIBLE);
        holder.mViewSpace.setVisibility(View.GONE);
        if (addressModel == null) {
            holder.mTvAddressName.setVisibility(View.INVISIBLE);
            holder.mViewSpace.setVisibility(View.VISIBLE);
            return;
        }
        String strAddress = mContext.getString(R.string.txt_address_name) + " " + addressModel.getName();
        holder.mTvAddressName.setText(strAddress);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title_address)
        TextView mTvAddressName;
        @BindView(R.id.view_space)
        View mViewSpace;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ly_address_root)
        protected void onClick() {
            if (mCallBackAddress != null) {
                mCallBackAddress.onSelectedAddress(mList.get(getAdapterPosition()));
            }
        }
    }

    public interface CallBackAddress {
        void onSelectedAddress(AddressModel addressModel);
    }


}
