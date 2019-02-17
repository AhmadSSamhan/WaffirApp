package com.android.waffirapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.waffirapp.R;
import com.android.waffirapp.model.OffersModel;
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
public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {
    private Context mContext;
    private CallBack mCallBack;
    private List<OffersModel> mList;

    public OffersAdapter(Context context, List<OffersModel> basicModels, CallBack callBack) {
        this.mContext = context;
        this.mList = basicModels;
        this.mCallBack = callBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_offers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OffersModel model = mList.get(position);
        if (model == null) {
            holder.mCardView.setVisibility(View.INVISIBLE);
            return;
        }
        holder.mCardView.setVisibility(View.VISIBLE);
        int width = Utility.getImageDimensions(holder.mImgOffer).first;
        int height = Utility.getImageDimensions(holder.mImgOffer).second;
        Utility.setGlideDefaultImage(mContext, model.getImage(), width, height, holder.mImgOffer);
        holder.mViewEmptySpan.setVisibility(View.GONE);
        if (position == getItemCount() - 1) {
            holder.mViewEmptySpan.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_offer)
        ImageView mImgOffer;
        @BindView(R.id.card_view_offer)
        CardView mCardView;
        @BindView(R.id.view_empty_span)
        View mViewEmptySpan;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card_view_offer)
        void onClick() {
            if (mCallBack != null) {
                mCallBack.onClickItemOffer(mList.get(getAdapterPosition()));
            }
        }
    }

    public interface CallBack {
        void onClickItemOffer(OffersModel offersModel);
    }
}
