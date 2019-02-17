package com.android.waffirapp.presentation.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.waffirapp.R;
import com.android.waffirapp.my_interface.PhotoSliderComponent;
import com.android.waffirapp.util.Utility;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.waffirapp.help.ConstantApp.SLIDER_IMAGE_PATH;

/**
 * Created by Ahmad.Samhan.
 */
public class SwipeImageFragment extends Fragment {

    @BindView(R.id.imageView)
    ImageView mImageView;
    private String strImagePath;

    public static SwipeImageFragment newInstance(PhotoSliderComponent component) {
        SwipeImageFragment swipeFragment = new SwipeImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SLIDER_IMAGE_PATH, component.getImagePath());
        swipeFragment.setArguments(bundle);
        return swipeFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        if (bundle != null) {
            strImagePath = bundle.getString(SLIDER_IMAGE_PATH);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View swipeView = inflater.inflate(R.layout.layout_image_swipe, container, false);
        ButterKnife.bind(this, swipeView);
        int width = Utility.getImageDimensions(mImageView).first;
        int height = Utility.getImageDimensions(mImageView).second;
        Utility.setGlideDefaultImage(getContext(), strImagePath, width, height, mImageView);
        return swipeView;
    }
}
