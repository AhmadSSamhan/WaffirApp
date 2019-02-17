package com.android.waffirapp.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.waffirapp.adapter.ProvinceAdapter;
import com.android.waffirapp.R;
import com.android.waffirapp.model.ProvincesModel;
import com.android.waffirapp.my_interface.CallBackSelectProvince;
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
public class ProvinceDialog extends BottomSheetDialog implements CallBackSelectProvince {
    @BindView(R.id.recycle_view_all_string)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title_choose_one_item)
    TextView mTvTitleChooseItem;
    private List<ProvincesModel> mList;
    private CallBackSelectProvince mCallBackSelectProvince;

    public ProvinceDialog(@NonNull Context context) {
        super(context);
    }

    public ProvinceDialog setList(List<ProvincesModel> list) {
        this.mList = list;
        return this;
    }

    public ProvinceDialog setCallBack(CallBackSelectProvince callBack) {
        this.mCallBackSelectProvince = callBack;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_one_item);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);
        mTvTitleChooseItem.setText(getContext().getString(R.string.txt_select_province));
        setRecyclerView();
    }

    private void setRecyclerView() {
        ProvinceAdapter adapter = new ProvinceAdapter(getContext(), mList, this);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onSelectProvince(ProvincesModel provincesModel) {
        if (mCallBackSelectProvince != null) {
            mCallBackSelectProvince.onSelectProvince(provincesModel);
        }
        dismiss();
    }
}
