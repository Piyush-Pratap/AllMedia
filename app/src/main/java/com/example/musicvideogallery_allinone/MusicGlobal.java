package com.example.musicvideogallery_allinone;

import android.app.Application;
import android.media.MediaPlayer;

import com.example.musicvideogallery_allinone.ui.music.ModelAudio;

import java.util.ArrayList;

public class MusicGlobal extends Application {
    private ArrayList<ModelAudio> arrayList;
    private int index;
    MediaPlayer mediaPlayer = new MediaPlayer();
    boolean musicSet=false;

    public MusicGlobal() {
    }

    public boolean isMusicSet() {
        return musicSet;
    }

    public void setMusicSet(boolean musicSet) {
        this.musicSet = musicSet;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    private static MusicGlobal instance = new MusicGlobal();

    // Getter-Setters
    public static MusicGlobal getInstance() {
        return instance;
    }

    public ArrayList<ModelAudio> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ModelAudio> arrayList) {
        this.arrayList = arrayList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
