package com.example.musicvideogallery_allinone.ui.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.musicvideogallery_allinone.BuildConfig;
import com.example.musicvideogallery_allinone.MainActivity;
import com.example.musicvideogallery_allinone.R;
import com.example.musicvideogallery_allinone.ui.GalleryGlobal;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.FileOutputStream;

public class SingleImageView extends AppCompatActivity {

    int position,int_position;
    ViewPager viewPager;
    ImageButton menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image_view2);
        getSupportActionBar().hide();

        menu = (ImageButton)findViewById(R.id.menu);

        Intent i = getIntent();
        int_position = GalleryGlobal.getInstance().getFolder_position();
        position = GalleryGlobal.getInstance().getImage_position();

        viewPager = findViewById(R.id.singleImage);
        setViewPager(position);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(),"ModalBottomSheet");
            }
        });

    }

    public void setViewPager(int pos) {
        SliderAdapter sliderAdapter = new SliderAdapter(getApplicationContext(),int_position);
        viewPager.setAdapter(sliderAdapter);
        viewPager.setCurrentItem(pos);
    }


}