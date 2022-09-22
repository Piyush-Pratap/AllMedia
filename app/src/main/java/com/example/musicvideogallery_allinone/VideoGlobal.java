package com.example.musicvideogallery_allinone;

import android.app.Application;

import com.example.musicvideogallery_allinone.ui.video.VideoModel;

import java.util.ArrayList;

public class VideoGlobal extends Application {
    ArrayList<VideoModel> videoList = new ArrayList<>();
    int index;
    private static VideoGlobal videoGlobal = new VideoGlobal();

    public static VideoGlobal getInstance() {
        return videoGlobal;
    }

    public ArrayList<VideoModel> getVideoList() {
        return videoList;
    }

    public void setVideoList(ArrayList<VideoModel> videoList) {
        this.videoList = videoList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
