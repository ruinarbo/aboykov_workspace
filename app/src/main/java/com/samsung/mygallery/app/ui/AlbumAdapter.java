package com.samsung.mygallery.app.ui;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.samsung.mygallery.app.R;
import com.samsung.mygallery.app.models.PictureModel;

/**
 * Created by aboykov on 6/16/17.
 * this adapter is used to set pictures inside album view
 */
public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {

    private PictureModel[] mPictures;
    private Context mContext;

    public AlbumAdapter(Context context, PictureModel[] pictures) {
        mContext = context;
        mPictures = pictures;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.album_picture, parent, false);
        AlbumAdapter.AlbumViewHolder viewHolder = new AlbumAdapter.AlbumViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        PictureModel picture = mPictures[position];
        ImageView imageView = holder.mPictureImage;
        TextView textView = holder.mPictureTitle;

        Glide.with(mContext)
                .load(Uri.parse(picture.getUri()))
                .placeholder(R.drawable.picture_placeholder)
                .into(imageView);
        textView.setText(picture.getTitle());

    }

    @Override
    public int getItemCount() {
        return mPictures.length;
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder {
        public ImageView mPictureImage;
        public TextView mPictureTitle;
        public AlbumViewHolder(View itemView) {
            super(itemView);
            mPictureImage = (ImageView) itemView.findViewById(R.id.iv_picture);
            mPictureTitle = (TextView)itemView.findViewById(R.id.tv_picture_title);
        }

    }
}
