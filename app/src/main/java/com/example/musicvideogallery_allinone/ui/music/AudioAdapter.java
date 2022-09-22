package com.example.musicvideogallery_allinone.ui.music;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicvideogallery_allinone.MusicGlobal;
import com.example.musicvideogallery_allinone.R;

import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.viewHolder> {
    Context context;
    ArrayList<ModelAudio> arrayList;
    Uri uri;
    public OnItemClickListener onItemClickListener;
    MusicGlobal musicGlobal = MusicGlobal.getInstance();

    public AudioAdapter(Context context, ArrayList<ModelAudio> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AudioAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.music_audio_list_view,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AudioAdapter.viewHolder holder, final int position) {
        holder.title.setText(arrayList.get(position).getName());
        holder.artist.setText(arrayList.get(position).getArtist());

        uri = Uri.parse(arrayList.get(position).getUriString());
        String albumId = arrayList.get(position).getAlbumId();
        String imagePath = arrayList.get(position).getImagePath();
        final Uri imagePathUri = Uri.parse(imagePath);
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(imagePathUri!=null){
                            Glide.with(context).load(imagePathUri).placeholder(R.drawable.music).into(holder.songImage);
                        }
                    }
                });
            }
        }).start();

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title,artist;
        ImageView songImage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.albumName);
            artist=itemView.findViewById(R.id.artistName);
            songImage=itemView.findViewById(R.id.songImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    musicGlobal.setIndex(getAdapterPosition());
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }

}
