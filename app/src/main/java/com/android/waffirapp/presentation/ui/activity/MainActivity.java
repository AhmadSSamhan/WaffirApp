package com.android.waffirapp.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.android.waffirapp.help.ConstantApp;
import com.android.waffirapp.my_interface.CallBackCartCount;
import com.android.waffirapp.my_interface.CallBackChangeTitleToolBarByFragment;
import com.android.waffirapp.my_interface.CallBackGifNameDrawer;
import com.android.waffirapp.presentation.ui.BaseActivity;
import com.android.waffirapp.presentation.ui.fragment.CartFragment;
import com.android.waffirapp.presentation.ui.fragment.HomePageFragment;
import com.android.waffirapp.presentation.ui.fragment.OffersFragment;
import com.android.waffirapp.presentation.ui.fragment.ProfileFragment;
import com.android.waffirapp.presentation.ui.fragment.SearchFragment;
import com.android.waffirapp.realm.RealmController;
import com.android.waffirapp.util.BadgesNotification;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
/**
 * Created by Ahmad.Samhan.
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, CallBackChangeTitleToolBarByFragment, CallBackCartCount, CallBackGifNameDrawer {

    @BindView(R.id.nav_part_one)
    BottomNavigationView mBottomNavigationShoppingCart;
    @BindView(R.id.nav_part_two)
    BottomNavigationView mBottomNavigationProfile;
    @BindView(R.id.img_offer)
    ImageView mImgSelectOffer;
    @BindView(R.id.fl_main_activity)
    FrameLayout mFrameLayout;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.toolbar_main)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_main)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.app_tool_bar)
    AppBarLayout mAppToolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolBarTitle;
    @BindView(R.id.img_toolbar_logo)
    ImageView mImgLogoToolBar;
    private boolean isClickImgOffer = false;
    private BadgesNotification mBadgesNotificationCartShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RealmController.create();
        createToolBarWithBackBtn(mToolbar);
        setToolbarTitle("");
        setInitDrawer();
        showLogoOrTitleWithToolBar(View.VISIBLE, View.GONE);
        mAppToolbar.addOnOffsetChangedListener(new AppBarLayout.BaseOnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
//                    mImgToolBar.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
//                    mImgToolBar.setVisibility(View.GONE);
                    isShow = false;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setListenersToBottomNavigation();
        showAddHeaderNavigationView("", "");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNotificationCartShopping();
    }

    private void updateNotificationCartShopping() {
        mBadgesNotificationCartShopping.setBadgeCount(RealmController.getInstance().getCartShoppingProductList().size());
        mBadgesNotificationCartShopping.showBadgeView();
    }

    /**
     * Create a method to initialize drawer with item .
     */
    private void setInitDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Create a method to implement listeners over all Navigation Bottom Views
     */
    private void setListenersToBottomNavigation() {
        setCustomBottomNav(mBottomNavigationShoppingCart);
        setCustomBottomNav(mBottomNavigationProfile);
        addBadgesToBottomNavigationToCart();
        addBadgesToBottomNavigationToProfile();
        mBottomNavigationProfile.setSelectedItemId(R.id.navigation_home);
    }

    /**
     * Add Badges to item on BottomNavigation
     */
    private void addBadgesToBottomNavigationToCart() {
        mBadgesNotificationCartShopping = new BadgesNotification(getContext(), mBottomNavigationShoppingCart);
        mBadgesNotificationCartShopping.setBadgeCount(RealmController.getInstance().getCartShoppingProductList().size());
        mBadgesNotificationCartShopping.showBadgeView();
    }

    /**
     * Add Badges to item on BottomNavigation
     */
    private void addBadgesToBottomNavigationToProfile() {
        BadgesNotification badgesNotification = new BadgesNotification(getContext(), mBottomNavigationProfile);
        badgesNotification.setViewPosition(1);
        badgesNotification.setBadgeCount(2);
        badgesNotification.showBadgeView();
    }

    @Optional
    private void showAddHeaderNavigationView(String title, String sectionId) {
        View headerView = mNavigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.tv_gift_offer);
        textView.setText(title);
        textView.setOnClickListener(view -> Toast.makeText(getContext(), "SectionID F" + sectionId, Toast.LENGTH_SHORT).show());
        textView.setVisibility(View.VISIBLE);
    }

    /**
     * Create a method to reset all Navigation Bottom Views
     */
    private void setResetNavigationBottomViewToDefault() {
        mBottomNavigationShoppingCart.getMenu().setGroupCheckable(0, false, true);
        mBottomNavigationProfile.getMenu().setGroupCheckable(0, false, true);
    }

    private void setCustomBottomNav(final BottomNavigationView bottomNav) {
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            setResetImgOffersToDefault();
            if (item.isChecked()) {
                item.setCheckable(false);
            }
            setResetNavigationBottomViewToDefault();
            switch (item.getItemId()) {
                case R.id.navigation_cart:
                    item.setCheckable(true);
                    replaceFragment(new CartFragment());
                    break;
                case R.id.navigation_search:
                    item.setCheckable(true);
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.navigation_home:
                    item.setCheckable(true);
                    replaceFragment(new HomePageFragment());
                    break;
                case R.id.navigation_profile:
                    item.setCheckable(true);
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void setResetImgOffersToDefault() {
        isClickImgOffer = false;
        mImgSelectOffer.setImageResource(R.drawable.ic_offers_default);
    }

    @OnClick(R.id.img_offer)
    void setOnClickImgOffers() {
        setResetNavigationBottomViewToDefault();
        if (!isClickImgOffer) {
            isClickImgOffer = true;
            mImgSelectOffer.setImageResource(R.drawable.ic_offers_green);
            replaceFragment(new OffersFragment());
        }
    }

    //Replace Fragment and Pass the Fragment title via Fragment
    private void replaceFragment(Fragment strFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_activity, strFragment, "ItemFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_loyalty_card) {
            startActivity(new Intent(getContext(), LoyaltyCardActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_juices:
                Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_ice:
                break;
            case R.id.nav_baby_milk:
                break;
            case R.id.nav_tools:
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setChangeTitleToolBarByFragment(String title) {
        if (title.equalsIgnoreCase(ConstantApp.HOME_FRAGMENT_TITLE)) {
            showLogoOrTitleWithToolBar(View.VISIBLE, View.GONE);
            return;
        }
        showLogoOrTitleWithToolBar(View.GONE, View.VISIBLE);
        mTvToolBarTitle.setText(title);
    }

    protected void showLogoOrTitleWithToolBar(int showLogo, int showTitle) {
        mImgLogoToolBar.setVisibility(showLogo);
        mTvToolBarTitle.setVisibility(showTitle);
    }

    @Override
    protected void onDestroy() {
        RealmController.getInstance().unbindController();
        super.onDestroy();
    }

    @Override
    public void onTotalItemCountOnCart(int count) {
//        if (count == 0) {
//            mBadgesNotificationCartShopping.removeBadgeViewElement();
//            return;
//
//        }
        updateNotificationCartShopping();
//        mBadgesNotificationCartShopping.setBadgeCount(count);
//        mBadgesNotificationCartShopping.showBadgeView();
    }

    @Override
    public void setUpdateGifNameDrawer(String title, String sectionId) {
        showAddHeaderNavigationView(title, sectionId);
    }
}
