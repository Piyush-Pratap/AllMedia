package com.example.musicvideogallery_allinone.ui.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.musicvideogallery_allinone.MusicGlobal;
import com.example.musicvideogallery_allinone.R;
import com.example.musicvideogallery_allinone.VideoGlobal;

import java.io.IOException;
import java.util.ArrayList;

public class VideoPlayer extends AppCompatActivity {
    int position;
    VideoGlobal videoGlobal = VideoGlobal.getInstance();
    ArrayList<VideoModel> videoList = videoGlobal.getVideoList();
    VideoView videoViewPlay;
    TextView videoCurrent,videoTotal;
    SeekBar videoSeekbar;
    ImageButton prev,play,next;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    MediaPlayer mediaPlayer = MusicGlobal.getInstance().getMediaPlayer();
    double current,total;
    boolean progressVisible=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_player);

        Intent intent = getIntent();
        position=intent.getIntExtra("position",0);
        videoGlobal.setIndex(position);

        videoViewPlay = findViewById(R.id.videoViewPlay);
        videoCurrent = findViewById(R.id.videoCurrent);
        videoTotal = findViewById(R.id.videoTotal);
        videoSeekbar = findViewById(R.id.videoSeekbar);
        relativeLayout = findViewById(R.id.relativeLayout);
        linearLayout = findViewById(R.id.linearLayout);
        prev = findViewById(R.id.videoPrev);
        play = findViewById(R.id.videoPlay);
        next = findViewById(R.id.videoNext);

        mediaPlayer.pause();
        setVideo(position);

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position<0){
                    position=videoList.size()-1;
                }
                setVideo(position);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position==videoList.size()){
                    position=0;
                }
                setVideo(position);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoViewPlay.isPlaying()){
                    videoViewPlay.pause();
                    play.setImageResource(android.R.drawable.ic_media_play);
                }
                else{
                    videoViewPlay.start();
                    play.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        });

        videoViewPlay.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                position++;
                if(position==videoList.size()){
                    position=0;
                }
                setVideo(position);
            }
        });

        videoViewPlay.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setScreenOnWhilePlaying(true);
                setVideoProgress();
            }
        });

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressVisible){
                    linearLayout.setVisibility(View.GONE);
                    progressVisible = false;
                }
                else{
                    linearLayout.setVisibility(View.VISIBLE);
                    progressVisible = true;
                }
            }
        });

    }

    private void setVideo(int position) {
        videoGlobal.setIndex(position);
        getSupportActionBar().hide();
        videoViewPlay.setVideoURI(Uri.parse(videoList.get(position).getUriString()));
        videoViewPlay.start();
        play.setImageResource(android.R.drawable.ic_media_pause);
    }

    private void setVideoProgress() {
        current = videoViewPlay.getCurrentPosition();
        total = videoViewPlay.getDuration();

        //display video duration
        videoTotal.setText(timeConversion((long) total));
        videoCurrent.setText(timeConversion((long) current));
        videoSeekbar.setMax((int) total);
        final Handler handler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current = videoViewPlay.getCurrentPosition();
                    videoCurrent.setText(timeConversion((long) current));
                    videoSeekbar.setProgress((int) current);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed){
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

        videoSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current=videoSeekbar.getProgress();
                videoViewPlay.seekTo((int)current);
            }
        });
    }

    public String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }
}