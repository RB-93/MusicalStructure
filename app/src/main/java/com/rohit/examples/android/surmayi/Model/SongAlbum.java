package com.rohit.examples.android.surmayi.Model;

import java.io.Serializable;

/**
 * Getter and Setter class methods for Library Activity layout elements
 */
public class SongAlbum implements Serializable {
    private final int mAlbumArtId;
    private final String mSongArtist;
    private String mSongName;
    private int mSongId;

    public SongAlbum(int albumArtId, String songName, String songArtist, int songId) {
        this.mAlbumArtId = albumArtId;
        this.mSongName = songName;
        this.mSongArtist = songArtist;
        this.mSongId = songId;
    }

    public SongAlbum(int albumArtId, String songArtist) {
        this.mAlbumArtId = albumArtId;
        this.mSongArtist = songArtist;
    }

    public int getAlbumArtId() {
        return mAlbumArtId;
    }

    public String getSongArtist() {
        return mSongArtist;
    }

    public String getSongName() {
        return mSongName;
    }

    public int getmSongId() {
        return mSongId;
    }
}
