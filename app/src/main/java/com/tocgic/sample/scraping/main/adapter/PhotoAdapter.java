package com.tocgic.sample.scraping.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tocgic.sample.scraping.R;
import com.tocgic.sample.scraping.main.adapter.model.PhotoDataModel;
import com.tocgic.sample.scraping.main.adapter.view.PhotoAdapterView;
import com.tocgic.sample.scraping.main.data.Const;
import com.tocgic.sample.scraping.network.domain.Photo;
import com.tocgic.sample.scraping.views.interfaces.OnRecyclerItemClickListener;
import com.tocgic.sample.scraping.views.interfaces.OnRecyclerItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tocgic on 2016. 8. 6..
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> implements PhotoDataModel, PhotoAdapterView {

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_item_photo)
        ImageView ivItemPhoto;

        @Bind(R.id.tv_item_photo)
        TextView txItemPhoto;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Context context;
    private List<Photo> photoList;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;
    private OnRecyclerItemLongClickListener onRecyclerItemLongClickListener;

    public PhotoAdapter(Context context) {
        this.context = context;
        photoList = new ArrayList<>();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    public void setOnRecyclerItemLongClickListener(OnRecyclerItemLongClickListener onRecyclerItemLongClickListener) {
        this.onRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    @Override
    public PhotoAdapter.PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.PhotoViewHolder holder, int position) {
        Photo photo = getPhoto(position);
        if (photo != null && holder != null) {
            holder.ivItemPhoto.setOnClickListener(v -> {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(PhotoAdapter.this, position);
                }
            });

            holder.ivItemPhoto.setOnLongClickListener(v -> {
                if (onRecyclerItemLongClickListener != null) {
                    return onRecyclerItemLongClickListener.onItemLongClick(PhotoAdapter.this, position);
                } else {
                    return false;
                }
            });

            String path = photo.getPath();
            if (path != null && path.length() > 0) {
                Glide.with(context)
                        .load(String.format("%s/%s", Const.HOST, path))
                        .centerCrop()
                        .crossFade()
                        .into(holder.ivItemPhoto);
            }

            String fileName = photo.getFileName();
            if (fileName == null) {
                fileName = "";
            }
            holder.txItemPhoto.setText(fileName);
        }

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }


    /**
     * PhotoDataModel
     */

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }


    /**
     * PhotoAdapterView
     */

    @Override
    public void add(Photo photo) {
        photoList.add(photo);
    }

    @Override
    public Photo getPhoto(int position) {
        Photo photo = null;
        if (position > -1 && position < photoList.size()) {
            photo = photoList.get(position);
        }
        return photo;
    }

    @Override
    public void remove(int position) {
        if (position > -1 && position < photoList.size()) {
            photoList.remove(position);
        }
    }
}
