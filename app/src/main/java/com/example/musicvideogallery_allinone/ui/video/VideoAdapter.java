package com.example.musicvideogallery_allinone.ui.video;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicvideogallery_allinone.R;
import com.example.musicvideogallery_allinone.VideoGlobal;
import com.example.musicvideogallery_allinone.ui.music.AudioAdapter;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.viewHolder> {
    Context context;
    ArrayList<VideoModel> videoList = new ArrayList<>();
    VideoGlobal videoGlobal = VideoGlobal.getInstance();
    public VideoAdapter.OnItemClickListener onItemClickListener;

    public VideoAdapter(Context context, ArrayList<VideoModel> videoList) {
        this.context=context;
        this.videoList=videoList;
    }

    @NonNull
    @Override
    public VideoAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.video_list_view,parent,false);
        return (new viewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.viewHolder holder, int position) {
        holder.videoName.setText(videoList.get(position).getName());
        holder.duration.setText(videoList.get(position).getDuration());

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView videoName,duration;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            videoName = itemView.findViewById(R.id.videoName);
            duration = itemView.findViewById(R.id.videoDuration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoGlobal.setIndex(getAdapterPosition());
                    onItemClickListener.onItemClick(getAdapterPosition(), v);
                }
            });
        }
    }

    public void setOnItemClickListener(VideoAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = (OnItemClickListener) onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos, View v);
    }
}
