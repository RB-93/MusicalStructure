package com.rohit.examples.android.surmayi.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rohit.examples.android.surmayi.Fragment.AlbumFragment;
import com.rohit.examples.android.surmayi.Fragment.ArtistFragment;
import com.rohit.examples.android.surmayi.Fragment.SongsFragment;
import com.rohit.examples.android.surmayi.Model.SongAlbum;
import com.rohit.examples.android.surmayi.Model.SongUtils;
import com.rohit.examples.android.surmayi.R;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity implements ArtistFragment.OnItemSelectedListener, AlbumFragment.OnItemSelectedListener {

    /**
     * Setting tags to handle Fragments and Fragments data
     */
    public static final String ALBUM_TAG = "ALBUMS";
    public static final String SONG_TAG = "SONGS";
    public static final String FRAGMENT_TAG = "TAG";
    public static final String ARTIST_TAG = "ARTISTS";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String ARTIST = "ARTIST";
    public static final String ALBUM_NAME = "ALBUM_NAME";
    public static final String ITEM_POS = "ITEM_POS";
    private static final String ALBUM = "ALBUM";
    /*
     * Layout view declaration for the activity and Fragments manager to handle fragments
     */
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        /*
         *  Setting Custom toolbar
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
         *  Setup Action bar if not null
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        /*
         *  Fetching ViewId for Views and handling DrawerLayout navigation
         *  Setup Navigation DrawerLayout into teh view layout
         */
        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        fragmentManager = getSupportFragmentManager();

        NavigationView navigationView = findViewById(R.id.navigation_view);

        // Assigning Menu options to Drawer Pane from menu Resource
        final Menu menu = navigationView.getMenu();

        // Logic to handle Intent received from activities other than this, if not null
        if (getIntent() != null) {

            // Fragment Instance to get Song Fragment by default
            Fragment fragment = new SongsFragment();
            menu.getItem(0).setChecked(true);
            String fgTag;
            setTitle(getString(R.string.songs));

            // Handling Fragment by Intent received from other activities
            if (getIntent().getStringExtra(FRAGMENT_TAG) != null) {
                fgTag = getIntent().getStringExtra(FRAGMENT_TAG);

                switch (fgTag) {
                    case ALBUM_TAG:
                        fragment = new AlbumFragment();
                        fgTag = ALBUM_TAG;
                        menu.getItem(1).setChecked(true);
                        setTitle(getString(R.string.album));
                        break;

                    case ARTIST_TAG:
                        fragment = new ArtistFragment();
                        fgTag = ARTIST_TAG;
                        menu.getItem(2).setChecked(true);
                        setTitle(getString(R.string.artist));
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.container, fragment, fgTag).commit();
            } else {
                fragmentManager.beginTransaction().replace(R.id.container, fragment, SONG_TAG).commit();
            }

            /*
             *  Check logic to Drawer menu items selection and a setup to inflate Fragments requested
             */
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.favorite) {
                        Intent favIntent = new Intent(LibraryActivity.this, FavoritesActivity.class);
                        startActivity(favIntent);
                        finish();
                    }

                    /*
                     *  new Fragment instance to handle menu based clicks for showing Fragments
                     */
                    Fragment fragment;

                    Class Fragment;

                    switch (menuItem.getItemId()) {
                        case R.id.albums:
                            Fragment = AlbumFragment.class;
                            break;

                        case R.id.artists:
                            Fragment = ArtistFragment.class;
                            break;

                        default:
                            Fragment = SongsFragment.class;
                    }

                    /*
                     *  Handling exception to set and check correct fragment based on their Tag assigned
                     */
                    try {
                        fragment = (android.support.v4.app.Fragment) Fragment.newInstance();

                        String tag = SONG_TAG;

                        switch (fragment.getClass().getSimpleName()) {
                            case "AlbumFragment":
                                tag = ALBUM_TAG;
                                break;

                            case "ArtistFragment":
                                tag = ARTIST_TAG;
                                break;
                        }

                        fragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    menuItem.setChecked(true);
                    setTitle(menuItem.getTitle());
                    drawerLayout.closeDrawers();

                    return true;
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Maintaining selected state for Action bar toggle for Drawer item
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    /**
     * Inflating Menu for DrawerLayout from Menu resource
     *
     * @param menu for Menu resource instance, getting SearchView on Action Bar
     * @return true if Search bar handled
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(true);
        return true;
    }

    /**
     * Maintain option selected state
     *
     * @param item to get Menu item
     * @return selection state of Menu item in Drawer layout
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    /**
     * Method to handle click events on Artist Items
     *
     * @param artistName to receive item selected for populating relevant metadata
     */
    @Override
    public void onArtistSelected(String artistName) {
        ArrayList<SongAlbum> filterByArtist = SongUtils.filterSongByArtist(this, artistName);
        SongsFragment songsFragment = new SongsFragment();

        // Bundle object to preserve and send the Tag for Artist selected
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_NAME, artistName);
        bundle.putSerializable(ARTIST, filterByArtist);
        songsFragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.container, songsFragment, SONG_TAG).addToBackStack(SONG_TAG).commit();
        setTitle(getString(R.string.artist));
    }

    /**
     * Method to handle click events on Album Items
     *
     * @param albumName to receive item selected for populating relevant metadata
     */
    @Override
    public void onAlbumSelected(String albumName) {
        /*
         *  Array to store Albums from Song Resource
         *  Instance to handle Fragment, sending Bundle object with the tag
         */
        ArrayList<SongAlbum> filterByAlbums = SongUtils.filterSongByAlbum(this, albumName);
        SongsFragment songsFragment = new SongsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(ALBUM_NAME, albumName);
        bundle.putSerializable(ALBUM, filterByAlbums);
        songsFragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(R.id.container, songsFragment, SONG_TAG).addToBackStack(SONG_TAG).commit();
        setTitle(getString(R.string.album));
    }
}