package com.rohit.examples.android.surmayi.Model;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.rohit.examples.android.surmayi.R;

import java.util.ArrayList;

/**
 * This class if for storing RAW file resources, Arrays of Song names. Artists an Albums
 * Sorts out ad filters the Album/Artist list based on selection
 */
public class SongUtils {

    public static ArrayList<SongAlbum> getSongLibrary(Context context) {

        Resources resources = context.getResources();

        // To store RAW files, Song name and Artist name
        String[] songList = resources.getStringArray(R.array.songName);
        String[] artistList = resources.getStringArray(R.array.songArtistName);
        int[] songPathList = {

                R.raw.comfortably_numb,
                R.raw.downtown,
                R.raw.get_used_to_me,
                R.raw.good_time,
                R.raw.homeless,
                R.raw.let_me_go,
                R.raw.my_way,
                R.raw.never_say_die,
                R.raw.payback,
                R.raw.photograph,
                R.raw.selfie,
                R.raw.stitches,
                R.raw.uptown_funk,
                R.raw.wildest_dreams,
        };

        // Fetching and storing Arrays of Album Drawables
        TypedArray albumImgArray = resources.obtainTypedArray(R.array.albumImg);

        int[] albumImgList = new int[albumImgArray.length()];

        // Collecting all Image drawables based on their IDs with index
        for (int album_pos = 0; album_pos < albumImgList.length; album_pos++) {
            albumImgList[album_pos] = albumImgArray.getResourceId(album_pos, 0);
        }

        albumImgArray.recycle();

        ArrayList<SongAlbum> songAlbums = new ArrayList<>();

        // Sending data to SongAlbum constructor
        for (int x = 0; x < songPathList.length; x++) {
            SongAlbum songAlbum = new SongAlbum(albumImgList[x], songList[x], artistList[x], songPathList[x]);
            songAlbums.add(songAlbum);
        }
        return songAlbums;
    }

    /**
     * Method to get Artist List items out of Song list
     *
     * @param context to get the current Context of the activity from called
     * @return song albums
     */
    public static ArrayList<SongAlbum> getArtistLibrary(Context context) {

        Resources resources = context.getResources();
        // Getting values from arrays
        String[] artistList = resources.getStringArray(R.array.songArtistName);
        TypedArray imageArray = resources.obtainTypedArray(R.array.albumImg);
        int[] artistImageList = new int[imageArray.length()];

        for (int index = 0; index < artistImageList.length; index++) {
            artistImageList[index] = imageArray.getResourceId(index, 0);
        }

        imageArray.recycle();

        // Create artist items and add them to ArrayList
        ArrayList<SongAlbum> songAlbums = new ArrayList<>();
        for (int i = 0; i < artistImageList.length; i++) {
            SongAlbum songAlbum = new SongAlbum(artistImageList[i], artistList[i]);
            songAlbums.add(songAlbum);
        }
        return songAlbums;
    }

    /**
     * Method to get Album List items out of Song list
     *
     * @param context to get the current Context of the activity from called
     * @return Son song albums
     */
    public static ArrayList<SongAlbum> getAlbumLibrary(Context context) {

        Resources resources = context.getResources();
        // Get values from arrays
        String[] albumList = resources.getStringArray(R.array.songAlbumName);
        TypedArray imageArray = resources.obtainTypedArray(R.array.albumImg);
        int[] albumImageList = new int[imageArray.length()];

        for (int index = 0; index < albumImageList.length; index++) {
            albumImageList[index] = imageArray.getResourceId(index, 0);
        }

        imageArray.recycle();

        // Create album items and add them to ArrayList
        ArrayList<SongAlbum> songAlbums = new ArrayList<>();
        for (int i = 0; i < albumImageList.length; i++) {
            SongAlbum songAlbum = new SongAlbum(albumImageList[i], albumList[i]);
            songAlbums.add(songAlbum);
        }
        return songAlbums;
    }

    // Method to filter out songs by artist
    public static ArrayList<SongAlbum> filterSongByArtist(Context context, String artistName) {
        ArrayList<SongAlbum> filterByArtists = new ArrayList<>();
        ArrayList<SongAlbum> songAlbums = getSongLibrary(context);
        for (SongAlbum songAlbum : songAlbums) {
            // Filter by artist
            if (songAlbum.getSongArtist().equals(artistName)) {
                filterByArtists.add(songAlbum);
            }
        }
        return filterByArtists;
    }

    // Method to filter out songs by album
    public static ArrayList<SongAlbum> filterSongByAlbum(Context context, String albumName) {
        ArrayList<SongAlbum> filterByAlbums = new ArrayList<>();
        ArrayList<SongAlbum> songAlbums = getSongLibrary(context);
        for (SongAlbum songAlbum : songAlbums) {
            // Filter by album
            if (songAlbum.getSongArtist().equals(albumName)) {
                filterByAlbums.add(songAlbum);
            }
        }
        return filterByAlbums;
    }
}