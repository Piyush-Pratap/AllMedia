package com.example.musicvideogallery_allinone.ui.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.musicvideogallery_allinone.MusicGlobal;
import com.example.musicvideogallery_allinone.R;

import java.util.ArrayList;

public class music_player extends AppCompatActivity {
    ImageView songImg;
    TextView title,artist,current,total;
    SeekBar seekBar;
    ImageButton repeat,prev,play,next,shuffle;
    MusicGlobal musicGlobal = MusicGlobal.getInstance();
    MediaPlayer mediaPlayer = musicGlobal.getMediaPlayer();
    ArrayList<ModelAudio> arrayList;
    boolean repeatStatus=false;
    boolean shuffleStatus=false;
    int audio_index;
    int current_pos,total_duration;

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
        setContentView(R.layout.activity_music_player);

        songImg = findViewById(R.id.songImg);
        title = findViewById(R.id.Title);
        artist = findViewById(R.id.Artist);
        current = findViewById(R.id.current);
        total = findViewById(R.id.total);
        seekBar = findViewById(R.id.seekbar);
        repeat = findViewById(R.id.repeat);
        prev = findViewById(R.id.prev);
        play = findViewById(R.id.play);
        next = findViewById(R.id.next);
        shuffle = findViewById(R.id.shuffle);

        arrayList = musicGlobal.getArrayList();

        Intent intent = getIntent();
        audio_index=musicGlobal.getIndex();

        setAudio();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(repeatStatus){
                    playAudio(audio_index);
                    return;
                }
                if(shuffleStatus){
                    audio_index=(int)(Math.random()*musicGlobal.getArrayList().size());
                }
                audio_index++;
                if (audio_index < (arrayList.size())) {
                    musicGlobal.setIndex(audio_index);
                    playAudio(audio_index);
                } else {
                    audio_index = 0;
                    musicGlobal.setIndex(audio_index);
                    playAudio(audio_index);
                }

            }
        });

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shuffleStatus){
                    shuffle.setImageResource(android.R.drawable.checkbox_off_background);
                    shuffleStatus=false;
                }
                else {
                    shuffle.setImageResource(android.R.drawable.checkbox_on_background);
                    shuffleStatus=true;
                }
            }
        });

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeatStatus) {
                    repeatStatus = false;
                    repeat.setImageResource(android.R.drawable.checkbox_off_background);
                }
                else {
                    repeatStatus = true;
                    repeat.setImageResource(android.R.drawable.checkbox_on_background);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                mediaPlayer.seekTo((int) current_pos);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    play.setImageResource(android.R.drawable.ic_media_play);
                }
                else{
                    mediaPlayer.start();
                    play.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shuffleStatus){
                    audio_index=(int)(Math.random()*musicGlobal.getArrayList().size());
                }
                audio_index++;
                if(audio_index==musicGlobal.getArrayList().size()){
                    audio_index=0;
                }
                musicGlobal.setIndex(audio_index);
                playAudio(audio_index);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shuffleStatus){
                    audio_index=(int)(Math.random()*musicGlobal.getArrayList().size());
                }
                audio_index--;
                if(audio_index<0){
                    audio_index=musicGlobal.getArrayList().size()-1;
                }
                musicGlobal.setIndex(audio_index);
                playAudio(audio_index);
            }
        });


    }

    private void setAudio() {
        Glide.with(getApplicationContext()).load(Uri.parse(arrayList.get(audio_index).getImagePath())).placeholder(R.drawable.music).fitCenter().into(songImg);
        title.setText(arrayList.get(audio_index).getName());
        artist.setText(arrayList.get(audio_index).getArtist());
        if(mediaPlayer.isPlaying()){
            play.setImageResource(android.R.drawable.ic_media_pause);
        }
        else{
            play.setImageResource(android.R.drawable.ic_media_play);
        }

        setAudioProgress();
    }

    public void playAudio(int pos) {
        try  {
            mediaPlayer.reset();
            Glide.with(getApplicationContext()).load(Uri.parse(arrayList.get(pos).getImagePath())).fitCenter().placeholder(R.drawable.music).into(songImg);
            title.setText(arrayList.get(pos).getName());
            artist.setText(arrayList.get(pos).getArtist());
            play.setImageResource(android.R.drawable.ic_media_pause);
            //set file path
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(arrayList.get(pos).getUriString()));
            mediaPlayer.prepare();
            mediaPlayer.start();
            audio_index=pos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        setAudioProgress();
    }

    public void setAudioProgress() {
        //get the audio duration
        current_pos = mediaPlayer.getCurrentPosition();
        total_duration = mediaPlayer.getDuration();

        //display the audio duration
        total.setText(timerConversion((long) total_duration));
        current.setText(timerConversion((long) current_pos));
        seekBar.setMax((int) total_duration);
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = mediaPlayer.getCurrentPosition();
                    current.setText(timerConversion((long) current_pos));
                    seekBar.setProgress((int) current_pos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed){
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public String timerConversion(long value) {
        String audioTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            audioTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            audioTime = String.format("%02d:%02d", mns, scs);
        }
        return audioTime;
    }

}