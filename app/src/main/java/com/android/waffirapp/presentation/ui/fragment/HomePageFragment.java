package com.android.waffirapp.presentation.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.ImageFragmentPagerAdapter;
import com.android.waffirapp.adapter.MainProductAdapter;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.ProductsResponse;
import com.android.waffirapp.model.SliderComponent;
import com.android.waffirapp.model.SliderResponse;
import com.android.waffirapp.my_interface.CallBackGifNameDrawer;
import com.android.waffirapp.my_interface.PhotoSliderComponent;
import com.android.waffirapp.presentation.presenter.Products.ProductsContract;
import com.android.waffirapp.presentation.presenter.Products.ProductsPresenterImpl;
import com.android.waffirapp.presentation.presenter.Products.ProductsResultInteractorImpl;
import com.android.waffirapp.presentation.presenter.SliderImage.SliderContract;
import com.android.waffirapp.presentation.presenter.SliderImage.SliderPresenterImpl;
import com.android.waffirapp.presentation.presenter.SliderImage.SliderResultInteractorImpl;
import com.android.waffirapp.presentation.ui.BaseFragment;
import com.android.waffirapp.util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class HomePageFragment extends BaseFragment implements SliderContract.MainView, SwipeRefreshLayout.OnRefreshListener, ProductsContract.MainView {
    @BindView(R.id.recycle_view_main_product)
    RecyclerView mRecyclerView;
    @BindView(R.id.view_stub_promo_label)
    ViewStub mViewStubPromoLabel;
    @BindView(R.id.swipe_refresh_slider)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private SliderContract.presenter mPresenter;
    private ProductsContract.presenter mProPresenter;
    private int mCurrentPage = 0;
    private CallBackGifNameDrawer mCallBackGifNameDrawer;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (mCallBackGifNameDrawer == null) {
            mCallBackGifNameDrawer = (CallBackGifNameDrawer) context;
        }
    }

    /**
     * initializing SwipeRefresh
     */
    private void initSwipeRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.grey_700), ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    public View onCreateFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new SliderPresenterImpl(this, new SliderResultInteractorImpl());
        mProPresenter = new ProductsPresenterImpl(this, new ProductsResultInteractorImpl());
        initSwipeRefresh();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleToolbarFragment(ConstantApp.HOME_FRAGMENT_TITLE);
        getDataFromAPi();
    }

    private void addDataToSlider(List<SliderComponent> result) {
        List<PhotoSliderComponent> mPhotoSliderComponents = new ArrayList<>(result);
        ImageFragmentPagerAdapter mPagerAdapter = new ImageFragmentPagerAdapter(getFragmentManager(), mPhotoSliderComponents);
        ViewPager viewPager = getView().findViewById(R.id.viewPager);
        CircleIndicator indicator = getView().findViewById(R.id.circle_indicator);
        viewPager.setAdapter(mPagerAdapter);
        indicator.setViewPager(viewPager);
        // Auto start of viewpager
        Handler mHandlerPhoto = new Handler();
        Runnable mRunnableUpdate = () -> {
            if (mCurrentPage == mPhotoSliderComponents.size()) {
                mCurrentPage = 0;
            }
            viewPager.setCurrentItem(mCurrentPage++, true);
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandlerPhoto.post(mRunnableUpdate);
            }
        }, 1500, 2000);
    }

    private void showViewStubPromoLabel(SliderResponse result) {
        if (mViewStubPromoLabel != null) {
            // If you want to change data of ViewStub at runtime, you can do like this
            View inflatedView = mViewStubPromoLabel.inflate();// inflate the layout
            ImageView imgGift = inflatedView.findViewById(R.id.img_gift_offer);
            Utility.setGlideGiftImage(getContext(), result.getImageOffers(), imgGift);
            mViewStubPromoLabel.setVisibility(View.VISIBLE);
            imgGift.setOnClickListener(view -> Toast.makeText(getContext(), "Category ID" + result.getCatID(), Toast.LENGTH_SHORT).show());
        }
    }

    private void setDataToRecycleView(List<ProductsResponse> result) {
        if (result.size() == 0) {
            return;
        }
//        result.add(null);
        MainProductAdapter adapter = new MainProductAdapter(getContext(), result);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setProductsListToView(List<ProductsResponse> result) {
        Log.v("", result.get(0).getTitleAdvertising());
        setDataToRecycleView(result);
    }

    @Override
    public void setSliderImagesToView(SliderResponse result) {
        addDataToSlider(result.getSlider());
        showViewStubPromoLabel(result);
        if (mCallBackGifNameDrawer != null) {
            mCallBackGifNameDrawer.setUpdateGifNameDrawer(result.getName(), result.getCatID());
        }
    }

    @Override
    public void onResponseEmpty(String strNoData) {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    public void onRefresh() {
        getDataFromAPi();
    }

    private void getDataFromAPi() {
        mPresenter.getSliderImagesDataFromAPI();
        mProPresenter.getProductsDataFromAPI();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        mProPresenter.onDestroy();
        super.onDestroy();
    }
}
