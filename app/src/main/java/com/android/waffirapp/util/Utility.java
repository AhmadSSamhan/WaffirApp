package com.android.waffirapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Pair;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.waffirapp.R;
import com.android.waffirapp.help.ConstantApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import static com.android.waffirapp.help.ConstantApp.IMAGE_URL;

/**
 * Created by A.Samhan.
 */

public class Utility {

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboard(Context context) {
        //Find the currently focused view, so we can grab the correct window token from it.
        InputMethodManager immss = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        immss.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    //Use Circle Image on adapter with fit size of image special on different devices
    public static void setImageCircle(final Context context, final String imagePath, final ImageView imgItem) {
        if (imagePath != null && !imagePath.isEmpty()) {
            imgItem.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    imgItem.getViewTreeObserver().removeOnPreDrawListener(this);
                    int width = imgItem.getMeasuredWidth();
                    int height = imgItem.getMeasuredHeight();
                    if (context != null) {
                        setGlideCircleImage(context, imagePath, width, height, imgItem);
                    }
                    return true;
                }
            });
        }
    }

    public static Pair<Integer, Integer> getImageDimensions(final ImageView imgItem) {
        imgItem.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                imgItem.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
        return new Pair<>(imgItem.getMeasuredWidth(), imgItem.getMeasuredHeight());
    }


    //Use Glide lib to load images and Show images in a circular way
    public static void setGlideCircleImage(final Context context, String imgPath, int width, int height, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(ConstantApp.IMAGE_URL + imgPath)
                .apply(new RequestOptions()
                        .override(width, height)
                        .placeholder(R.color.grey_100) // Display default image if not exist
                        .error(R.color.grey_100) // If something wrong
                        .centerCrop())
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }  //Use Glide lib to load images and Show images in a circular way

    public static void setGlideDefaultImage(final Context context, String imgPath, int width, int height, final ImageView imageView) {
        Glide.with(context)
                .asBitmap()
                .load(IMAGE_URL + imgPath)
                .apply(new RequestOptions()
                        .override(width, height)
                        .placeholder(R.drawable.ic_place_holder_default) // Display default image if not exist
                        .error(R.drawable.ic_place_holder_default) // If something wrong
                        .centerCrop())
                .into(imageView);
    }

    public static void setGlideGiftImage(final Context context, String imgPath, final ImageView imageView) {
        Glide.with(context)
                .asGif()
                .load(IMAGE_URL + imgPath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_place_holder_default) // Display default image if not exist
                        .error(R.drawable.ic_place_holder_default) // If something wrong
                        .centerCrop())
                .into(imageView);
    }

    public static void setLabelDiscount(boolean isOffers, String discount, String discountValue, TextView view) {
        if (!isOffers) {
            view.setVisibility(View.INVISIBLE);
            return;
        }
        view.setVisibility(View.VISIBLE);
        String strDiscount = "<font color='red'>" + discount + view.getContext().getString(R.string.txt_currency_iraq) + "</font>";
        String strDiscountValue = "<font color='#F7C25C'>" + discountValue + "%" + "</font>";
        String fullText = strDiscount + " " + strDiscountValue;
        Spannable spannable = (Spannable) Html.fromHtml(fullText);
        spannable.setSpan(new StrikethroughSpan(), 0, discount.length() + discountValue.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(spannable);
    }

    public static Spanned getTextFromHtml(String txt) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(txt, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(txt);
        }
    }

    public static boolean isUserLogin(Context context) {
        return new SharedPerApp(context).setStrKey(ConstantApp.USER_ID).getSharedPref() != null && !new SharedPerApp(context).setStrKey(ConstantApp.USER_ID).getSharedPref().equalsIgnoreCase(ConstantApp.GUEST_USER);
    }

    public static String getUserId(Context context) {
        return new SharedPerApp(context).setStrKey(ConstantApp.USER_ID).getSharedPref();
    }
}
