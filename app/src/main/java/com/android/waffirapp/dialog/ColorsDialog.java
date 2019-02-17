package com.android.waffirapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.ColorsAdapter;
import com.android.waffirapp.model.ColorProductsModel;
import com.android.waffirapp.my_interface.CallBackChooseColor;
import com.google.android.material.bottomsheet.BottomSheetDialog;

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
public class ColorsDialog extends BottomSheetDialog implements CallBackChooseColor {

    @BindView(R.id.recycle_view_all_string)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title_choose_one_item)
    TextView mTvTitleChooseItem;

    private List<ColorProductsModel> mList;
    private CallBackChooseColor mCallBackChooseColor;

    public ColorsDialog(@NonNull Context context) {
        super(context);
    }

    public ColorsDialog setList(List<ColorProductsModel> list) {
        this.mList = list;
        return this;
    }

    public ColorsDialog setCallBack(CallBackChooseColor callBackChooseColor) {
        this.mCallBackChooseColor = callBackChooseColor;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_one_item);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);
        mTvTitleChooseItem.setText(getContext().getString(R.string.txt_select_color));
        setRecyclerView();
    }

    private void setRecyclerView() {
        ColorsAdapter adapter = new ColorsAdapter(getContext(), mList).setCallBack(this);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onChooseColor(ColorProductsModel colorsModel) {
        if (mCallBackChooseColor != null) {
            mCallBackChooseColor.onChooseColor(colorsModel);
        }
        dismiss();
    }
}

