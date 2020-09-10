package com.rohit.examples.android.surmayi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.rohit.examples.android.surmayi.Adapter.FragmentAdapter;
import com.rohit.examples.android.surmayi.Fragment.BaseFragment;
import com.rohit.examples.android.surmayi.R;

import static com.rohit.examples.android.surmayi.Activity.LibraryActivity.FRAGMENT_TAG;

public class FavoritesActivity extends AppCompatActivity implements BaseFragment.onBackListener {

    /**
     * @param POSITION to get Tab position
     */
    private static final String POSITION = "POSITION";
    /**
     * Layout view declaration for Tablayout and Viewpager
     */
    private ViewPager tabViewpager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        /*
         *  Getting view IDs for favorites layout
         */
        tabViewpager = findViewById(R.id.tabbed_viewpager);
        tabLayout = findViewById(R.id.tabs);

        // display back button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Setting up viewpager with adapter and tablayout
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        tabViewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(tabViewpager);
    }

    /**
     * navigation to previous activity when the back button is pressed on the action bar
     *
     * @return boolean stating nav_search_menu is handled
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackToLibrary(FRAGMENT_TAG);
        return true;
    }

    /**
     * handling interface from Base Fragment for back button click and up navigation
     *
     * @param backTag to get and sending Fragment to Library Activity
     */
    @Override
    public void onBackToLibrary(String backTag) {
        Intent backIntent = new Intent(this, LibraryActivity.class);
        backIntent.putExtra(FRAGMENT_TAG, backTag);
        finish();
        startActivity(backIntent);
    }

    /**
     * Method overridden to save additional instance state information to an interface bundle
     *
     * @param outState bundle preserving data
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());

    }

    /**
     * To restore the previous state
     *
     * @param savedInstanceState getting data recently supplied in onSaveInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tabViewpager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }
}