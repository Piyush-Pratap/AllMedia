package com.example.musicvideogallery_allinone.ui.video;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicvideogallery_allinone.R;
import com.example.musicvideogallery_allinone.VideoGlobal;
import com.example.musicvideogallery_allinone.ui.music.AudioAdapter;

import java.util.ArrayList;

public class VideoFragment extends Fragment {
    private final  int MY_PERMISSION_REQUEST=1;
    RecyclerView videoRecycler;
    VideoGlobal videoGlobal = VideoGlobal.getInstance();
    VideoAdapter videoAdapter;
    ArrayList<VideoModel> videoList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_video, container, false);
        
        videoRecycler = root.findViewById(R.id.recyclerVideo);

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }
            else{
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }
        }
        else{
            doThis();
        }

        return root;
    }

    private void doThis() {
        getAllVideo();
        videoList = videoGlobal.getVideoList();
        videoAdapter = new VideoAdapter(getContext(),videoList);
        videoRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        videoRecycler.setHasFixedSize(true);
        videoRecycler.setAdapter(videoAdapter);

        videoAdapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int pos, View v) {
                Intent intent = new Intent(getContext(),VideoPlayer.class);
                intent.putExtra("position",pos);
                startActivity(intent);
            }
        });
    }

    private void getAllVideo() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri,null,null,null,null);
        ArrayList<VideoModel> temp = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                VideoModel  videoModel  = new VideoModel ();
                videoModel.setName(title);
                videoModel.setUriString(data);
                videoModel.setDuration(timeConversion(Long.parseLong(duration)));
                temp.add(videoModel);

            } while (cursor.moveToNext());

            videoGlobal.setVideoList(temp);
        }


    }

    public String timeConversion(long value) {
        String videoTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }
}