package com.android.waffirapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.waffirapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.core.content.ContextCompat;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Ahmad Samhan on 1/20/2019.
 */

public class BadgesNotification {

    private View mNotificationBadgeElement;
    private int mChildPosition = 0;
    private Context mContext;
    private int mIntBadgeCount = 0;
    private BottomNavigationView mNotificationBadgeView;
    private BottomNavigationMenuView mMenuItemBottomNavigation;
    private BottomNavigationItemView mItemViewBottomNavigation;

    public BadgesNotification(Context context) {
        this.mContext = context;
    }

    /**
     * Use Badges with BottomNavigation bar
     *
     * @param context
     * @param notificationBadgeView
     */
    public BadgesNotification(Context context, BottomNavigationView notificationBadgeView) {
        this.mContext = context;
        this.mNotificationBadgeView = notificationBadgeView;
    }

    public void setViewPosition(int childPosition) {
        this.mChildPosition = Math.abs(childPosition);
    }

    public void setBadgeCount(int badgeCount) {
        this.mIntBadgeCount = badgeCount;
    }

    public void showBadgeView() {
        mMenuItemBottomNavigation = (BottomNavigationMenuView) mNotificationBadgeView.getChildAt(0);
        if (mChildPosition < mMenuItemBottomNavigation.getChildCount()) {
            mItemViewBottomNavigation = (BottomNavigationItemView) mMenuItemBottomNavigation.getChildAt(mChildPosition);
            inflateLayout();
            return;
        }
        Toast.makeText(mContext, "You are using the wrong position \n check bottomChild count", Toast.LENGTH_LONG).show();
    }

    private void inflateLayout() {
        if (mNotificationBadgeElement != null) {
            ViewGroup parent = (ViewGroup) mNotificationBadgeElement.getParent();
            if (parent != null) {
                parent.removeView(mNotificationBadgeElement);
            }
        }
        try {
            mNotificationBadgeElement = LayoutInflater.from(mContext).inflate(R.layout.layout_badge_notification_icon_only, mMenuItemBottomNavigation, false);
            TextView tvCount = mNotificationBadgeElement.findViewById(R.id.tv_notifications_badge);
            if (mIntBadgeCount == 0) {
                tvCount.setVisibility(View.GONE);
            } else {
                String txt = "" + mIntBadgeCount;
                tvCount.setText(txt);
            }
            mItemViewBottomNavigation.addView(mNotificationBadgeElement);
        } catch (InflateException e) {
            Log.v("AddBottomNavigationIcon", e.getMessage());
        }
    }


    public void removeBadgeViewElement() {
        mItemViewBottomNavigation.removeView(mNotificationBadgeElement);
    }

    public void refreshBadgeView() {
        mNotificationBadgeElement.setVisibility(isBadgeView() ? VISIBLE : GONE);
    }

    private boolean isBadgeView() {
        return mNotificationBadgeElement.getVisibility() != VISIBLE;
    }

    /**
     * Use Badge with custom view
     * Add Drawable icon with Badge count
     *
     * @param count      : return int value to show at the top off image
     * @param drawableId : icon resource
     * @return : drawable path
     */
    public Drawable convertLayoutToImage(int count, int drawableId) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_badge_icon_with_img, null);
        ImageView imageView = view.findViewById(R.id.icon_badge);
        imageView.setImageResource(drawableId);
        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = view.findViewById(R.id.count);
            String txt = "" + count;
            textView.setText(txt);
        }
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return new BitmapDrawable(mContext.getResources(), bitmap);
    }
}

