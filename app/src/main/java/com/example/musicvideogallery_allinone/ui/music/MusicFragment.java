package com.example.musicvideogallery_allinone.ui.music;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicvideogallery_allinone.MainActivity;
import com.example.musicvideogallery_allinone.MusicGlobal;
import com.example.musicvideogallery_allinone.R;

import java.io.IOException;
import java.util.ArrayList;

public class MusicFragment extends Fragment {
    private static  final  int MY_PERMISSION_REQUEST=1;
    MusicGlobal musicGlobal = MusicGlobal.getInstance();
    ArrayList<ModelAudio> arrayList;
    RecyclerView recyclerView;
    AudioAdapter adapter;
    MediaPlayer mediaPlayer = musicGlobal.getMediaPlayer();
    ImageView songPic;
    TextView songName;
    ImageButton playSong,nextSong;
    LinearLayout goToPlayer;
    int audio_index=musicGlobal.getIndex();

    @Override
    public void onStart() {
        super.onStart();
        Log.d("showLog", "on start");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("showLog", "on pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("showLog", "on stop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("showLog", "on resume");
        if(mediaPlayer.isPlaying()){
            playSong.setImageResource(android.R.drawable.ic_media_pause);
        }
        else{
            playSong.setImageResource(android.R.drawable.ic_media_play);
        }
        songName.setText(musicGlobal.getArrayList().get(musicGlobal.getIndex()).getName());
        Glide.with(getContext()).load(Uri.parse(arrayList.get(musicGlobal.getIndex()).getImagePath())).placeholder(R.drawable.music).into(songPic);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_music, container, false);
        Log.d("showLog", "on created");

        recyclerView = root.findViewById(R.id.recyclerView);

        songPic = root.findViewById(R.id.songPic);
        songName = root.findViewById(R.id.songName);
        playSong = root.findViewById(R.id.playSong);
        nextSong = root.findViewById(R.id.nextSong);
        goToPlayer = root.findViewById(R.id.goToPlayer);

        songName.setSelected(true);

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

        playSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    playSong.setImageResource(android.R.drawable.ic_media_play);
                }
                else{
                    mediaPlayer.start();
                    playSong.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });

        nextSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio_index++;
                if(audio_index==musicGlobal.getArrayList().size()){
                    audio_index=0;
                }
                musicGlobal.setIndex(audio_index);
                playAudio(audio_index);
            }
        });

        goToPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),music_player.class);
                intent.putExtra("audio_index",audio_index);
                startActivity(intent);
            }
        });



        Glide.with(getContext()).load(Uri.parse(arrayList.get(audio_index).getImagePath())).placeholder(R.drawable.music).into(songPic);
        songName.setText(arrayList.get(audio_index).getName());
        if(mediaPlayer.isPlaying()){
            playSong.setImageResource(android.R.drawable.ic_media_pause);
        }
        if(!musicGlobal.isMusicSet()){
            try {
                mediaPlayer.setDataSource(getContext(),Uri.parse(arrayList.get(audio_index).getUriString()));
                mediaPlayer.prepare();
                musicGlobal.setMusicSet(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return root;
    }

    public void doThis(){
        getAllAudio();
        arrayList = musicGlobal.getArrayList();
        adapter = new AudioAdapter(getContext(),arrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                playAudio(pos);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST: {
                if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    doThis();
                }
                else{
                    Toast.makeText(getContext(), "No permission granted!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void getAllAudio(){
        ArrayList<ModelAudio> tempList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = getContext().getContentResolver().query(uri,null,null,null,null);

        if(songCursor!=null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int songAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int songDuration = songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int songUri = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int songAlbumId = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

            do{
                String currentTitle = songCursor.getString(songTitle);
                String currentAlbum = songCursor.getString(songAlbum);
                String currentArtist = songCursor.getString(songArtist);
                String currentDuration = songCursor.getString(songDuration);
                String currentUri = songCursor.getString(songUri);
                long currentAlbumId = songCursor.getLong(songAlbumId);

                Uri imagePath = Uri.parse("content://media/external/audio/albumart");
                Uri imagePathUri = ContentUris.withAppendedId(imagePath,currentAlbumId);

                ModelAudio modelAudio = new ModelAudio(currentTitle,currentAlbum,currentArtist,currentDuration,currentUri,String.valueOf(currentAlbumId),imagePathUri.toString());
                tempList.add(modelAudio);
            }while(songCursor.moveToNext());

            musicGlobal.setArrayList(tempList);
        }


    }

    public void playAudio(int pos) {
        try  {
            mediaPlayer.reset();
            Glide.with(getContext()).load(Uri.parse(arrayList.get(pos).getImagePath())).placeholder(R.drawable.music).into(songPic);
            songName.setText(arrayList.get(pos).getName());
            playSong.setImageResource(android.R.drawable.ic_media_pause);
            //set file path
            mediaPlayer.setDataSource(getContext(), Uri.parse(arrayList.get(pos).getUriString()));
            mediaPlayer.prepare();
            mediaPlayer.start();
            audio_index=pos;
            musicGlobal.setIndex(audio_index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}