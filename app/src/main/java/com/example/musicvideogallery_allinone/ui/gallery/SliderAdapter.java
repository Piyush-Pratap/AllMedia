package com.example.musicvideogallery_allinone.ui.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.musicvideogallery_allinone.ui.GalleryGlobal;

public class SliderAdapter extends PagerAdapter {
    Context context;
    int int_position;
    int index;
    GalleryGlobal galleryGlobal = GalleryGlobal.getInstance();

    public SliderAdapter(Context context,int int_position) {
        this.context = context;
        this.int_position = int_position;
    }

    public SliderAdapter(){

    }

    @Override
    public int getCount() {
        return GalleryFragment.allImages.get(int_position).getAl_imagepath().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        galleryGlobal.setImage_position(position);
        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .load("file://"+GalleryFragment.allImages.get(int_position).getAl_imagepath().get(position))
                .into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}
