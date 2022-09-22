package com.example.musicvideogallery_allinone.ui.gallery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.musicvideogallery_allinone.R;

import java.util.ArrayList;

public class Adapter_ViewImages extends ArrayAdapter<ViewImages> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<ViewImages> allImages = new ArrayList<>();

    public Adapter_ViewImages(Context context, ArrayList<ViewImages> allImages) {
        super(context,R.layout.activity_gallery_fragment_view,allImages);
        this.allImages=allImages;
        this.context=context;
    }

    @Override
    public int getCount() {
        return allImages.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (allImages.size() > 0) {
            return allImages.size();
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_gallery_fragment_view, parent, false);
            viewHolder.folderName = (TextView) convertView.findViewById(R.id.folderName);
            viewHolder.folderSize = (TextView) convertView.findViewById(R.id.folderSize);
            viewHolder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.folderName.setText(allImages.get(position).getStr_folder());
        viewHolder.folderSize.setText(allImages.get(position).getAl_imagepath().size()+"");



        Glide.with(convertView).load("file://"+ allImages.get(position).getAl_imagepath().get(0))
                .into(viewHolder.galleryImage);


        return convertView;
    }

    private static class ViewHolder {
        TextView folderName, folderSize;
        ImageView galleryImage;


    }


}