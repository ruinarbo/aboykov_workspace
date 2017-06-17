package com.samsung.mygallery.app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/**
 * Created by aboykov on 6/16/17.
 * Album Model is Parcelable and can be passed to activities and fragments. Contains 3 fields: uri of album cover picture,
 * title of album, list of pictures in album.
 * Gson is used for serialization, it helps to convert list of objects to json string
 */
public class AlbumModel implements Parcelable {

    private String mUri;
    private String mTitle;
    private ArrayList<String> mPictures;

    public AlbumModel( String title, ArrayList<String> pictures) {
        mUri = pictures.get(0);
        mTitle = title;
        mPictures = pictures;
    }
    protected AlbumModel(Parcel in) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        mUri = in.readString();
        mTitle = in.readString();
        mPictures = gson.fromJson(in.readString(),type);

    }
    public static final Creator<AlbumModel> CREATOR = new Creator<AlbumModel>() {
        @Override
        public AlbumModel createFromParcel(Parcel in) {
            return new AlbumModel(in);
        }

        @Override
        public AlbumModel[] newArray(int size) {
            return new AlbumModel[size];
        }
    };

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ArrayList<String> getPictures() {
        return mPictures;
    }

    public void setPictures(ArrayList<String> pictures) {
        mPictures = pictures;
    }

    public PictureModel[] getAlbumContent() {

        PictureModel [] pictures = new PictureModel[mPictures.size()];
        for(int i = 0 ; i < mPictures.size(); i ++)
            pictures[i] = new PictureModel(mPictures.get(i));
        return  pictures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Gson gson = new Gson();
        dest.writeString(mUri);
        dest.writeString(mTitle);
        dest.writeString(gson.toJson(mPictures));
    }

}
