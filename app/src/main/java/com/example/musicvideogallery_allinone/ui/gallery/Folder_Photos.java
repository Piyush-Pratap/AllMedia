package com.example.musicvideogallery_allinone.ui.gallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.musicvideogallery_allinone.R;
import com.example.musicvideogallery_allinone.ui.GalleryGlobal;

public class Folder_Photos extends AppCompatActivity {

    private GridView gv;
    Adapter_Photos adapter;
    int int_position;

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);
        gv=(GridView)findViewById(R.id.gv);
        Intent intent = getIntent();
        adapter=new Adapter_Photos(this,GalleryFragment.allImages,GalleryGlobal.getInstance().getFolder_position());
        gv.setAdapter(adapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryGlobal.getInstance().setImage_position(position);
                Intent intent = new Intent(Folder_Photos.this,SingleImageView.class);
                startActivity(intent);
            }
        });
    }
}