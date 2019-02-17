package com.android.waffirapp.util;

import android.content.Context;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.waffirapp.R;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Ahmad.Samhan on 2019-02-07.
 */

public class ExpandTextView extends LinearLayout {

    @BindView(R.id.description_text)
    TextView mDesc;
    @BindView(R.id.btn_read_more)
    Button mBtnReadMore;
    @BindView(R.id.btn_read_less)
    Button mBtnReadLess;
    @BindView(R.id.view_opacity)
    ImageView mViewOpacity;
    @BindView(R.id.ly_show_desc)
    LinearLayout mRoot;
    private Context mContext;
    private boolean isExpandText = false;
    private boolean isAllowClickable;

    public ExpandTextView(Context context) {
        super(context);
        mContext = context;
        inflate(context);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context);
    }

    public ExpandTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context);
    }

    private void inflate(Context context) {
        inflate(context, R.layout.layout_custom_expand_text_view, this);
        ButterKnife.bind(this);
    }

    public void setDescription(String desc) {
        if (!TextUtils.isEmpty(desc)) {
            CheckTheNumberOfLines(desc);
            mDesc.setText(desc);
        }
    }

    public void setDescription(Spanned desc) {
        if (!TextUtils.isEmpty(desc)) {
            CheckTheNumberOfLines(desc.toString());
            mDesc.setText(desc);
        }
    }

    private int countLines(String txt) {
        String[] lines = txt.split("\r\n|\r|\n");
        return lines.length;
    }

    private void CheckTheNumberOfLines(String txt) {
        if (countLines(txt) <= 4) {
            isAllowClickable = true;
            isExpandText = true;
            onClickRootElement();
            mViewOpacity.setVisibility(View.GONE);
            mBtnReadMore.setVisibility(View.GONE);
            mBtnReadLess.setVisibility(View.GONE);
        }
    }

    public void setTextColorButtonMore(int colorButtonMore) {
        try {
            int color = ContextCompat.getColor(getContext(), colorButtonMore);
            mBtnReadMore.setTextColor(color);
            mBtnReadLess.setTextColor(color);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @OnClick(R.id.btn_read_more)
    protected void onClickReadMore() {
        if (isAllowClickable) {
            return;
        }
        isExpandText = true;
        mViewOpacity.setVisibility(View.GONE);
        mBtnReadMore.setVisibility(View.GONE);
        mBtnReadLess.setVisibility(View.VISIBLE);
        mDesc.setMaxLines(Integer.MAX_VALUE);
    }

    @OnClick(R.id.btn_read_less)
    protected void onClickReadLess() {
        if (isAllowClickable) {
            return;
        }
        isExpandText = false;
        mViewOpacity.setVisibility(View.VISIBLE);
        mBtnReadMore.setVisibility(View.VISIBLE);
        mBtnReadLess.setVisibility(View.GONE);
        mDesc.setMaxLines(4);
    }

    @OnClick(R.id.ly_show_desc)
    protected void onClickRootElement() {
        if (isAllowClickable) {
            return;
        }
        if (!isExpandText) {
            onClickReadMore();
            return;
        }
        onClickReadLess();
    }

    public void setMaxLiens(int maxLiens) {
        mDesc.setMaxLines(maxLiens);
    }
}
