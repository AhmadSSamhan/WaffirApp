package com.android.waffirapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.SortAdapter;
import com.android.waffirapp.model.SortModel;
import com.android.waffirapp.my_interface.CallBackSortBy;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmad.Samhan.
 */
public class SortDialog extends BottomSheetDialog implements CallBackSortBy {
    private Context mContext;
    private List<SortModel> mListSort = new ArrayList<>();
    @BindView(R.id.recycle_view_all_string)
    RecyclerView mRecyclerView;
    private CallBackSortBy mCallBackSortBy;
    private SortAdapter mAdapter;
    @BindView(R.id.tv_title_choose_one_item)
    TextView mTvTitleChooseItem;


    public SortDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public SortDialog setList(List<SortModel> list) {
        this.mListSort = list;
        return this;
    }

    public SortDialog setCallBack(CallBackSortBy callBack) {
        this.mCallBackSortBy = callBack;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_one_item);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);
        setRecyclerView();
        mTvTitleChooseItem.setText(getContext().getString(R.string.txt_sort_by));
    }

    private void setRecyclerView() {
        if (mListSort == null || mListSort.size() == 0) {
            mListSort = new ArrayList<>();
            String[] sortArray = mContext.getResources().getStringArray(R.array.sort_array);
            for (int item = 0; item < sortArray.length; item++) {
                mListSort.add(new SortModel("" + item, sortArray[item]));
            }
        }
        mAdapter = new SortAdapter(mContext, mListSort, this);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClickSortBy(SortModel sortModel) {
        if (mCallBackSortBy != null) {
            mCallBackSortBy.onClickSortBy(sortModel);
        }
        dismiss();
    }
}
