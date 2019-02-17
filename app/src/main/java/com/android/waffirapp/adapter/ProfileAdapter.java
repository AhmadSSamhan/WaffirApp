package com.android.waffirapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.model.ProfileModel;
import com.android.waffirapp.util.BadgesNotification;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyViewHolder> {
    private Context mContext;
    private CallBack mCallBack;
    private List<ProfileModel> mList;

    public ProfileAdapter(Context context, List<ProfileModel> profileModels, CallBack callBack) {
        this.mContext = context;
        this.mCallBack = callBack;
        this.mList = profileModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profile_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProfileModel profileModel = mList.get(position);
        holder.mLyProfile.setVisibility(View.VISIBLE);
        if (profileModel == null || TextUtils.isEmpty(profileModel.getId())) {
            holder.mLyProfile.setVisibility(View.INVISIBLE);
            return;
        }
        holder.mTvTitle.setText(profileModel.getRowName());
        holder.mImgProfileIcon.setImageResource(profileModel.getDrawable());
        if (position == 2) {
            holder.mImgProfileIcon.setImageDrawable(new BadgesNotification(mContext).convertLayoutToImage(2, R.drawable.ic_noti));
        }
        holder.mImgArrow.setVisibility(View.VISIBLE);
        if (position == getItemCount() - 3) {
            holder.mImgArrow.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_profile_item)
        TextView mTvTitle;
        @BindView(R.id.img_profile_icon)
        ImageView mImgProfileIcon;
        @BindView(R.id.img_arrow)
        ImageView mImgArrow;
        @BindView(R.id.ly_profile)
        LinearLayout mLyProfile;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.ly_profile)
        void onClickItem() {
            if (mCallBack != null) {
                mCallBack.onItemProfileSelected(mList.get(getAdapterPosition()), getAdapterPosition());
            }
        }
    }

    public interface CallBack {
        void onItemProfileSelected(ProfileModel profileModel, int position);
    }
}
