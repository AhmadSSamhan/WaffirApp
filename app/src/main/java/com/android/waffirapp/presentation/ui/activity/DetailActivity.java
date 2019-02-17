package com.android.waffirapp.presentation.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.adapter.ImageFragmentPagerAdapter;
import com.android.waffirapp.adapter.ProductAdapter;
import com.android.waffirapp.dialog.AddQuantityDialog;
import com.android.waffirapp.dialog.ColorsDialog;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.model.CartProductModel;
import com.android.waffirapp.model.ColorProductsModel;
import com.android.waffirapp.model.RelatedProductsModel;
import com.android.waffirapp.model.SingleProductsResponse;
import com.android.waffirapp.my_interface.CallBackChooseColor;
import com.android.waffirapp.my_interface.PhotoSliderComponent;
import com.android.waffirapp.my_interface.ProductComponent;
import com.android.waffirapp.presentation.presenter.SingleProductsDetails.SingleProContract;
import com.android.waffirapp.presentation.presenter.SingleProductsDetails.SingleProInteractorImpl;
import com.android.waffirapp.presentation.presenter.SingleProductsDetails.SingleProPresenterImpl;
import com.android.waffirapp.presentation.ui.BaseActivity;
import com.android.waffirapp.realm.RealmController;
import com.android.waffirapp.util.ExpandTextView;
import com.android.waffirapp.util.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Ahmad.Samhan.
 */
public class DetailActivity extends BaseActivity implements CallBackChooseColor, SingleProContract.MainView {
    @BindView(R.id.viewPager_detail)
    ViewPager mViewPager;
    @BindView(R.id.recycle_view_product_desc)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_main_custom)
    Toolbar mToolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvTitleToolbar;
    @BindView(R.id.tv_product_discount)
    TextView mTvProductDiscountLabel;
    @BindView(R.id.tv_available_colors)
    TextView mTvSelectColor;
    @BindView(R.id.tv_product_brand)
    TextView mTvTitleProduct;
    @BindView(R.id.tv_desc_product)
    TextView mTvTitleProductName;
    @BindView(R.id.tv_product_price)
    TextView mTvTitleProductPrice;
    @BindView(R.id.tv_quantity_available)
    TextView mTvQuantityAvailable;
    @BindView(R.id.tv_title_pieces)
    TextView mTvTitlePieces;
    @BindView(R.id.tv_desc_pieces)
    TextView mTvDescPieces;
    @BindView(R.id.image_pieces)
    ImageView mImgPieces;
    @BindView(R.id.tv_product_desc)
    ExpandTextView mTvDescProduct;
    @BindView(R.id.img_fav)
    ImageView mImgFavourite;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;
    @BindView(R.id.tv_free_product)
    View mViewProductPieces;
    @BindView(R.id.view)
    View mViewLine;
    @BindView(R.id.circle_indicator)
    CircleIndicator mIndicator;

    private int mCurrentPage = 0;
    private List<ColorProductsModel> mListColors = new ArrayList<>();
    private boolean isFavourite = true;
    private String mColorSelected;
    private AddQuantityDialog mQuantityDialog;
    private Snackbar mSnackbar;
    private SingleProContract.presenter mPresenter;
    private String mSingleProId;
    private String mMaxQuantity;
    private String mMinQuantity;
    private List<PhotoSliderComponent> mPhotoSliderComponents = new ArrayList<>();
    private ArrayList<ColorProductsModel> mColorProductsWithImage = new ArrayList<>();
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        RealmController.create();
        createToolBarWithBackBtn(mToolbar);
        setToolbarTitle("");
        getExtraData();
        mPresenter = new SingleProPresenterImpl(this, new SingleProInteractorImpl());
        mViewLine.setVisibility(View.INVISIBLE);
        mUserId = Utility.getUserId(getContext());
    }

    private void getExtraData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSingleProId = bundle.getString(ConstantApp.REQ_SINGLE_PRODUCT_ID);
        }
    }

    private void addDataToSlider() {
        ImageFragmentPagerAdapter mPagerAdapter = new ImageFragmentPagerAdapter(getSupportFragmentManager(), mPhotoSliderComponents);
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (mCurrentPage == mPhotoSliderComponents.size()) {
                mCurrentPage = 0;
            }
            mViewPager.setCurrentItem(mCurrentPage++, true);
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1500, 2000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestSingleProductsDataFromServer(getContext(), mSingleProId);
    }

    @OnClick(R.id.btn_add_to_cart)
    void addToCart() {
        try {
            mQuantityDialog = new AddQuantityDialog(getContext());
            mQuantityDialog.setMaxQuantity(mMaxQuantity);
            mQuantityDialog.setMinQuantity(mMinQuantity);
            mQuantityDialog.setCallBack((int count) -> {
                Random rand = new Random();
                int id = rand.nextInt(50) + 1;
                CartProductModel cartProductModel = new CartProductModel();
                cartProductModel.setId(String.valueOf(id));
                cartProductModel.setImgPath("");
                cartProductModel.setProductName(mTvTitleProduct.getText().toString());
                cartProductModel.setProductColor(mColorSelected);
                cartProductModel.setProductPrice("mPrice");
                cartProductModel.setProductCount(String.valueOf(count));
                cartProductModel.setProductNotes("2");
                RealmController.getInstance().addCartShoppingItemToList(cartProductModel);
                setCartItem();
            });
            mQuantityDialog.show();
        } catch (Exception e) {
            Log.d("AddToCart", e.getMessage());
        }

    }

    private void setCartItem() {
        mSnackbar = Snackbar.make(mScrollView, "Added to cart successfully", Snackbar.LENGTH_LONG);
        View view = mSnackbar.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        mSnackbar.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearViewDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearViewDialog();
    }

    private void clearViewDialog() {
        if (mQuantityDialog != null && mQuantityDialog.isShowing()) {
            mQuantityDialog.hide();
        }
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
    }

    @OnClick(R.id.img_fav)
    protected void addToFavourite() {
        if (!Utility.isUserLogin(getContext())) {
            Toast.makeText(getContext(), "First you must log in to follow up", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.requestFavoriteProductFromServer(getContext(), mUserId, mSingleProId);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_bounce);
        mImgFavourite.startAnimation(animation);
        if (isFavourite) {
            mImgFavourite.setBackgroundResource(R.drawable.ic_favorite_red_500_24dp);
            isFavourite = false;

        } else {
            mImgFavourite.setBackgroundResource(R.drawable.ic_heart);
            isFavourite = true;
        }
    }

    private void addRedHeartFavourite() {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.animation_bounce);
        mImgFavourite.startAnimation(animation);
        if (isFavourite) {
            mImgFavourite.setBackgroundResource(R.drawable.ic_favorite_red_500_24dp);
            isFavourite = false;
            return;
        }
        isFavourite = true;
    }

    @OnClick(R.id.tv_available_colors)
    protected void showColorsDialog() {
        if (mListColors.isEmpty()) {
            mTvSelectColor.setVisibility(View.GONE);
            return;
        }
        if (mListColors.size() == 1) {
            return;
        }
        new ColorsDialog(getContext()).setList(mListColors).setCallBack(this).show();
    }

    @Override
    public void onChooseColor(ColorProductsModel colorsModel) {
        mColorSelected = colorsModel.getColorName();
        String txt = getString(R.string.txt_color_selected) + " " + mColorSelected;
        mTvSelectColor.setText(txt);
    }

    @Override
    protected void onDestroy() {
        RealmController.getInstance().unbindController();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void setSingleProductsResultToRecycle(SingleProductsResponse result) {
        if (result != null) {
            mTvTitleToolbar.setVisibility(View.VISIBLE);
            mTvTitleToolbar.setText(result.getBrand());
            mPhotoSliderComponents.addAll(result.getImagesProducts());
            isFavourite = Boolean.valueOf(result.getIsFavorite());
            addRedHeartFavourite();
            addDataToSlider();
            mTvTitleProduct.setText(result.getBrand());
            mTvTitleProductName.setText(result.getProductName());
            Utility.setLabelDiscount(result.isIsOffers(), result.getProductDiscount(), result.getProductDiscountValue(), mTvProductDiscountLabel);
            String fullPriceWithCurrency = result.getProductPrice() + getString(R.string.txt_currency_iraq);
            mTvTitleProductPrice.setText(fullPriceWithCurrency);
            try {
                mListColors.addAll(result.getColorProducts().get(0));
                String txtColorName = getString(R.string.txt_color_selected) + " " + mListColors.get(0).getColorName();
                mTvSelectColor.setText(txtColorName);
                mColorProductsWithImage = new ArrayList<>();
                for (int itemStart = 1; itemStart < result.getColorProducts().size(); itemStart++) {
                    mColorProductsWithImage.addAll(result.getColorProducts().get(itemStart));
                }
            } catch (Exception e) {
                mTvSelectColor.setVisibility(View.GONE);
            }
            if (result.isIsShowQuntity()) {
                String strQuantity = getString(R.string.txt_quantity_available) + " " + result.getProductStok();
                mTvQuantityAvailable.setText(strQuantity);
                mTvQuantityAvailable.setVisibility(View.VISIBLE);
            }
            if (!TextUtils.isEmpty(result.getNamePices())) {
                mViewProductPieces.setVisibility(View.VISIBLE);
                Utility.setGlideGiftImage(getContext(), result.getImagesPices(), mImgPieces);
                mTvTitlePieces.setText(result.getNamePices());
                mTvDescPieces.setText(result.getDescPices());
            }
            if (!TextUtils.isEmpty(result.getProductDesc())) {
                mTvDescProduct.setVisibility(View.VISIBLE);
                mTvDescProduct.setDescription(Utility.getTextFromHtml(result.getProductDesc()).toString());
                mTvDescProduct.setTextColorButtonMore(R.color.colorPrimaryDark);
            }
            mMaxQuantity = result.getMaxQuntity();
            mMinQuantity = result.getMinQuntity();
            setDataToRecycleView(result.getRelatedProducts());
        }

    }

    private void setDataToRecycleView(List<RelatedProductsModel> relatedProducts) {
        List<ProductComponent> productComponents = new ArrayList<>(relatedProducts);
        productComponents.add(null);
        ProductAdapter adapter = new ProductAdapter(getContext(), productComponents);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResponseEmpty(String strNoData) {
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
    }
}
