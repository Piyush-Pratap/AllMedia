package com.example.musicvideogallery_allinone.ui.gallery;

import java.util.ArrayList;
import java.util.Collection;

public class ViewImages {
    ArrayList<String> al_imagepath;
    String Str_folder;
    public void setStr_folder(String str_folder) {
        Str_folder = str_folder;
    }

    public void setAl_imagepath(ArrayList<String> al_imagepath) {
        this.al_imagepath = al_imagepath;
    }


    public String getStr_folder() {
        return Str_folder;
    }

    public ArrayList<String> getAl_imagepath() {
        return al_imagepath;
    }

}
