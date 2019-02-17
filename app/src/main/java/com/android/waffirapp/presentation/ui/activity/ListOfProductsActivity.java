package com.android.waffirapp.presentation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.ListOfProductsAdapter;
import com.android.waffirapp.dialog.SortDialog;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.help.PaginationScrollListener;
import com.android.waffirapp.model.ListOfProductResponse;
import com.android.waffirapp.model.ProductsModel;
import com.android.waffirapp.presentation.presenter.ListOfProducts.ListOfProductsContract;
import com.android.waffirapp.presentation.presenter.ListOfProducts.ListOfProductsInteractorImpl;
import com.android.waffirapp.presentation.presenter.ListOfProducts.ListOfProductsPresenterImpl;
import com.android.waffirapp.presentation.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.android.waffirapp.help.ConstantApp.PAGE_START;
import static com.android.waffirapp.help.ConstantApp.TOTAL_PAGES;

/**
 * Created by Ahmad.Samhan.
 */
public class ListOfProductsActivity extends BaseActivity implements ListOfProductsContract.MainView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recycle_view_all_products)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_main_custom)
    Toolbar mToolbar;
    @BindView(R.id.tv_sort)
    TextView mTvSort;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitleToolbar;
    @BindView(R.id.swipe_refresh_all_products)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ListOfProductsContract.presenter mPresenter;
    private List<ProductsModel> mListProducts = new ArrayList<>();
    private ListOfProductsAdapter mAdapter;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private String mSectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        ButterKnife.bind(this);
        createToolBarWithBackBtn(mToolbar);
        setToolbarTitle("");
        initPresenter();
        initSwipeRefresh();
        setDataToRecycleView();
        getExtraData();
    }

    private void getExtraData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSectionId = bundle.getString(ConstantApp.REQ_SECTION_ID);
            mTvTitleToolbar.setVisibility(View.VISIBLE);
            mTvTitleToolbar.setText(bundle.getString(ConstantApp.REQ_SECTION_NAME));
        }
    }

    private void initPresenter() {
        mPresenter = new ListOfProductsPresenterImpl(this, new ListOfProductsInteractorImpl());
    }

    /**
     * initializing SwipeRefresh
     */
    private void initSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.grey_700), ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setDataToRecycleView() {
        mAdapter = new ListOfProductsAdapter(getContext(), mListProducts);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case ConstantApp.VIEW_TYPE_ITEM:
                        return 1;
                    case ConstantApp.VIEW_TYPE_LOADING:
                        return mGridLayoutManager.getSpanCount();
                    default:
                        return -1;
                }
            }
        });
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mGridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!mAdapter.isLoading()) {
                    currentPage += 1;
                    mAdapter.addEmptyObj();
                    getDataFromAPi();
                    mAdapter.setLoading(true);
                }
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return mAdapter.isLoading();
            }
        });
    }

    private void getDataFromAPi() {
        mPresenter.requestListOfProductsDataFromServer(mSectionId, String.valueOf(currentPage));
    }

    @OnClick(R.id.tv_sort)
    protected void onClickSort() {
        new SortDialog(getContext()).setCallBack(sortModel ->
                Toast.makeText(getContext(), sortModel.getTitle(), Toast.LENGTH_SHORT).show()).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromAPi();
    }

    @Override
    public void showProgress() {
        if (!mAdapter.isLoading()) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setListOfProductsResultToRecycle(ListOfProductResponse result) {
        mAdapter.removeEmptyObj();
        mAdapter.setLoading(false);
        if (result.getProducts().isEmpty()) {
            mAdapter.setLoading(true);
            return;
        }
        mListProducts.addAll(result.getProducts());
        mAdapter.updateDataRecycleView();
    }

    @Override
    public void onResponseEmpty(String strNoData) {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        mListProducts.clear();
        mAdapter.setLoading(false);
        getDataFromAPi();
    }
}
