package com.example.musicvideogallery_allinone.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicvideogallery_allinone.R;

import java.util.ArrayList;

import static com.example.musicvideogallery_allinone.ui.gallery.GalleryFragment.allImages;

public class Adapter_Photos extends ArrayAdapter<ViewImages> {

    Context context;
    ViewHolder viewHolder;
    int int_position;
    ArrayList<ViewImages> allImages = new ArrayList<>();

    public Adapter_Photos(@NonNull Context context, ArrayList<ViewImages> allImages,int int_position) {
        super(context, R.layout.activity_gallery_fragment_view, allImages);
        this.context=context;
        this.allImages=allImages;
        this.int_position=int_position;
    }

    @Override
    public int getCount() {
        return allImages.get(int_position).getAl_imagepath().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (allImages.get(int_position).getAl_imagepath().size() > 0) {
            return allImages.get(int_position).getAl_imagepath().size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.activity_gallery_fragment_view,parent,false);
            viewHolder.folderName=convertView.findViewById(R.id.folderName);
            viewHolder.folderSize=convertView.findViewById(R.id.folderSize);
            viewHolder.galleryImage=convertView.findViewById(R.id.galleryImage);

            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.folderName.setVisibility(View.GONE);
        viewHolder.folderSize.setVisibility(View.GONE);

        Glide.with(context).load("file://"+allImages.get(int_position).getAl_imagepath().get(position))
                .circleCrop()
                .into(viewHolder.galleryImage);

        return convertView;
    }

    public class ViewHolder{
        TextView folderName,folderSize;
        ImageView galleryImage;
    }
}
