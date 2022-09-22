package com.example.musicvideogallery_allinone.ui.gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.musicvideogallery_allinone.MusicGlobal;
import com.example.musicvideogallery_allinone.R;
import com.example.musicvideogallery_allinone.ui.GalleryGlobal;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    GridView gv;
    public static ArrayList<ViewImages> allImages = new ArrayList<>();
    boolean folder_exist;
    Adapter_ViewImages madapter;
    private static final int REQUEST_PERMISSIONS = 100;
    GalleryGlobal galleryGlobal = GalleryGlobal.getInstance();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        gv=root.findViewById(R.id.gv);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GalleryGlobal.getInstance().setFolder_position(position);
                Intent intent = new Intent(getContext(),Folder_Photos.class);
                startActivity(intent);
            }
        });


        if((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSIONS);
        }
        else{
            getImagePath();
        }

        return root;
    }

    private void getImagePath() {
        allImages.clear();

        int position=0;
        int column_index_data,column_index_folder_name;
        String imagePath=null;
        Cursor cursor;
        Uri uri=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection={MediaStore.MediaColumns.DATA,MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        final String date_taken=MediaStore.Images.Media.DATE_TAKEN;

        cursor=getContext().getContentResolver().query(uri,projection,null,null,date_taken+" DESC");

        column_index_data=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while(cursor.moveToNext()){
            imagePath=cursor.getString(column_index_data);

            for(int i=0; i<allImages.size(); i++){
                if(allImages.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))){
                    folder_exist=true;
                    position=i;
                    break;
                }
                else{
                    folder_exist=false;
                }
            }

            if(folder_exist){
                ArrayList<String> allPath=new ArrayList<>();
                allPath.addAll(allImages.get(position).getAl_imagepath());
                allPath.add(imagePath);
                allImages.get(position).setAl_imagepath(allPath);
            }
            else{
                ArrayList<String> allpath=new ArrayList<>();
                allpath.add(imagePath);
                ViewImages viewImages = new ViewImages();
                viewImages.setStr_folder(cursor.getString(column_index_folder_name));
                viewImages.setAl_imagepath(allpath);

                allImages.add(viewImages);
            }
        }

        madapter = new Adapter_ViewImages(getContext(),allImages);
        gv.setAdapter(madapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case REQUEST_PERMISSIONS:{
                for(int i=0; i<grantResults.length; i++){
                    if(grantResults.length>0 && grantResults[i]==PackageManager.PERMISSION_GRANTED){
                        getImagePath();
                    }
                    else{
                        Toast.makeText(getActivity(), "This app is not allowed to read and write to your storage", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}