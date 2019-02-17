package com.android.waffirapp.adapter.ListOfProducts;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.android.waffirapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by Ahmad.Samhan.
 */
public class ProgressViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.progress_bar_load_more)
    ProgressBar mProgressBar;
    private Context mContext;

    public ProgressViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(Context context) {
        this.mContext = context;
        bindData();
    }

    private void bindData() {
        mProgressBar.setIndeterminate(true);
    }

}
