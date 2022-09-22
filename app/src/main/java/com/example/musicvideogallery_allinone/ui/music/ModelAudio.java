package com.example.musicvideogallery_allinone.ui.music;

import android.net.Uri;

public class ModelAudio {
    String name;
    String album;
    String artist;
    String duration;
    String uriString;
    String albumId;
    String imagePath;

    public ModelAudio(String name, String album, String artist, String duration, String uriString, String albumId, String imagePath) {
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.uriString = uriString;
        this.albumId = albumId;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUriString() {
        return uriString;
    }

    public void setUriString(String uriString) {
        this.uriString = uriString;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
