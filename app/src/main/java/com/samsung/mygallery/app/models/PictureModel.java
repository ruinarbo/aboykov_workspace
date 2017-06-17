package com.samsung.mygallery.app.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by aboykov on 6/16/17.
 * Picture Model is Parcelable and can be passed to activities and fragments. Contains 2 fields: uri  picture, title of picture
 */
public class PictureModel implements Parcelable {

    private String mUri;
    private String mTitle;

    public PictureModel(String uri) {
        mUri = uri;
        mTitle = new File(Uri.parse(uri).getPath()).getName();
    }
    protected PictureModel(Parcel in) {
        mUri = in.readString();
        mTitle = in.readString();

    }
    public static final Creator<PictureModel> CREATOR = new Creator<PictureModel>() {
        @Override
        public PictureModel createFromParcel(Parcel in) {
            return new PictureModel(in);
        }

        @Override
        public PictureModel[] newArray(int size) {
            return new PictureModel[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUri);
        dest.writeString(mTitle);
    }
}

