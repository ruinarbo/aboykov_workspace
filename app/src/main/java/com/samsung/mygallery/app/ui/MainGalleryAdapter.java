package com.samsung.mygallery.app.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.samsung.mygallery.app.R;
import com.samsung.mygallery.app.models.AlbumModel;

/**
 * Created by aboykov on 6/16/17.
 * This adapter is used to set images with titles inside RecyclerView which represents list of albums
 * as input it get array of album models.Im using Glide to load pictures inside TextView
 * Glide includes loading(both Uri/Url) and cashing of images(cashing could be switched off).Glide has pretty good
 * performance also it's well tested by me on other projects.
 */
public class MainGalleryAdapter extends RecyclerView.Adapter<MainGalleryAdapter.MyViewHolder> {

    private static final String ALBUM_DETAILS_FRAGMENT = "albumDetailsFragment";
    private AlbumModel[] mAlbums;
    private Context mContext;

    public MainGalleryAdapter(Context context, AlbumModel[] albums) {
        mContext = context;
        mAlbums = albums;
    }

    @Override
    public MainGalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.album_cover, parent, false);
        MainGalleryAdapter.MyViewHolder viewHolder = new MainGalleryAdapter.MyViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainGalleryAdapter.MyViewHolder holder, int position) {
        final AlbumModel album = mAlbums[position];
        final TextView tv = holder.mAlbumCoverTitle;

        Glide.with(mContext)
                .load(Uri.parse(album.getUri()))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(400,200) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        //it could be better to use customized view instead TextViw,I'm using standart one just to give the idea
                        tv.setBackground(new BitmapDrawable(mContext.getResources(),resource));
                        tv.setText(album.getTitle());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mAlbums.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener,View.OnTouchListener {
        public TextView mAlbumCoverTitle;
        public MyViewHolder(View itemView) {
            super(itemView);
            mAlbumCoverTitle = (TextView)itemView.findViewById(R.id.tv_album_cover);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            itemView.setOnTouchListener(this);

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //by finger is lifted off the screen event close album preview and remove fragment from stack
            if (event.getAction() == MotionEvent.ACTION_UP) {
                FragmentManager manager =  ((AppCompatActivity) mContext).getSupportFragmentManager();
                AlbumDetailsFragment albumDetailsFragment = (AlbumDetailsFragment)  manager.findFragmentByTag(ALBUM_DETAILS_FRAGMENT);
                if(albumDetailsFragment != null && albumDetailsFragment.isVisible()){
                    ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction().remove(albumDetailsFragment).commit();
                    manager.popBackStack();
                }
            }
                return false;
        }

        @Override
        public void onClick(View v) {
            //by single click i'll open album view which contains list of pictures
            AlbumDetailsFragment albumDetailsFragment =(AlbumDetailsFragment) ((AppCompatActivity) mContext)
                    .getSupportFragmentManager()
                    .findFragmentByTag(ALBUM_DETAILS_FRAGMENT);
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                AlbumModel albumModel = mAlbums[position];
                Intent intent = new Intent(mContext, AlbumActivity.class);
                intent.putExtra(AlbumActivity.EXTRA_ALBUM_MODEL, albumModel);
                mContext.startActivity(intent);
            }

        }

        @Override
        public boolean onLongClick(View v) {
            //by long click i'll open album preview
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(AlbumDetailsFragment.CURRENT_ALBUM,mAlbums[position]);
                FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AlbumDetailsFragment albumDetailsFragment = new AlbumDetailsFragment();
                albumDetailsFragment.setArguments(arguments);
                fragmentTransaction.add(R.id.main_activity_layout,albumDetailsFragment,ALBUM_DETAILS_FRAGMENT)
                        .addToBackStack(null)
                        .commit();

            }

            return true;
        }
    }
}
