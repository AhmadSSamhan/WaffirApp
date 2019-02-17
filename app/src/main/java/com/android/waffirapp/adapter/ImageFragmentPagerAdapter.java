package com.android.waffirapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.waffirapp.my_interface.PhotoSliderComponent;
import com.android.waffirapp.presentation.ui.fragment.SwipeImageFragment;

import java.util.List;
/**
 * Created by Ahmad.Samhan.
 */
public class ImageFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<PhotoSliderComponent> mListOfImages;

    public ImageFragmentPagerAdapter(FragmentManager fm, List<PhotoSliderComponent> stringList) {
        super(fm);
        this.mListOfImages = stringList;
    }

    @Override
    public Fragment getItem(int position) {
        return SwipeImageFragment.newInstance(mListOfImages.get(position));
    }

    @Override
    public int getCount() {
        return mListOfImages.size();
    }
}
