package com.example.musicvideogallery_allinone.ui.gallery;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.musicvideogallery_allinone.R;
import com.example.musicvideogallery_allinone.ui.GalleryGlobal;
import com.example.musicvideogallery_allinone.ui.music.MusicFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;

public class BottomSheetDialog extends BottomSheetDialogFragment {
    ImageButton share,delete;
    private static final int REQUEST_PERMISSIONS = 100;
    int int_position = GalleryGlobal.getInstance().getFolder_position();
    int position = GalleryGlobal.getInstance().getImage_position();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,container,false);

        share = v.findViewById(R.id.share);
        delete= v.findViewById(R.id.delete);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpg");
                intent.putExtra(Intent.EXTRA_STREAM,Uri.parse(GalleryFragment.allImages.get(int_position).getAl_imagepath().get(position-1)));
                startActivity(Intent.createChooser(intent,"share this image using..."));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSIONS);
                }
                else{
                    deleteFile();
                }
            }
        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case REQUEST_PERMISSIONS:{
                for(int i=0; i<grantResults.length; i++){
                    if(grantResults.length>0 && grantResults[i]==PackageManager.PERMISSION_GRANTED){
                        deleteFile();
                    }
                    else{
                        Toast.makeText(getActivity(), "This app is not allowed to read and write to your storage", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void deleteFile() {
        File photo = new File(GalleryFragment.allImages.get(int_position).getAl_imagepath().get(position-1));
        if(photo.exists()){
            if(photo.delete()){
                GalleryFragment.allImages.get(int_position).getAl_imagepath().remove(position-1);
                Log.d("flll","file deleted : "+GalleryFragment.allImages.get(int_position).getAl_imagepath().get(GalleryGlobal.getInstance().getImage_position()-1));
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(Uri.fromFile(photo));
                getContext().sendBroadcast(scanIntent);


            }
            else{
                Log.d("flll","file is not deleted");
            }
        }
        else{
            Log.d("flll","file does not exists");
        }
    }
}
