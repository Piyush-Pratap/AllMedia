package com.example.musicvideogallery_allinone.ui;

import android.app.Application;

public class GalleryGlobal extends Application {
    int folder_position,image_position;

    private static GalleryGlobal galleryGlobal = new GalleryGlobal();

    public static GalleryGlobal getInstance(){
        return galleryGlobal;
    }

    public int getFolder_position() {
        return folder_position;
    }

    public void setFolder_position(int folder_position) {
        this.folder_position = folder_position;
    }

    public int getImage_position() {
        return image_position;
    }

    public void setImage_position(int image_position) {
        this.image_position = image_position;
    }


}
