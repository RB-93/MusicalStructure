package com.rohit.examples.android.surmayi.Adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rohit.examples.android.surmayi.Fragment.BaseFragment;

import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ALBUM_TAG;
import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.ARTIST_TAG;
import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.FRAGMENT_TAG;
import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.SONG_TAG;

/**
 * Adapter to handle Tab layout tabs for Favorites activity
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private final String[] pageName = new String[]{"Albums", "Artists"};

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        /*
         *  Instantiating Fragment with Tab index position
         */
        BaseFragment baseFragment = new BaseFragment();
        Bundle bundle = new Bundle();
        String tag = SONG_TAG;

        /*
         *  Switching logic to get appropriate Tab with their Fragment
         *  later, sending Bundle object to Favorite Activity to show data
         */
        switch (position) {
            case 1:
                tag = ALBUM_TAG;
                break;

            case 2:
                tag = ARTIST_TAG;
                break;
        }
        bundle.putString(FRAGMENT_TAG, tag);
        baseFragment.setArguments(bundle);

        return baseFragment;
    }

    /**
     * Getting total no og pages
     *
     * @return TOTAL_PAGES to the Activity
     */
    @Override
    public int getCount() {
        /*
         * Setting No of fragment pages for the layout
         * Getiig context for the current activity
         */
        return 2;
    }

    /**
     * Setting up the Title for each tab
     *
     * @param position to set appropriate Title to Tabs
     * @return pageName accordingly with its Index
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageName[position];
    }
}
