package com.samsung.mygallery.app.utils;

import com.samsung.mygallery.app.models.AlbumModel;


/**
 * Created by aboykov on 6/16/17.
 * Listener to update ui once list of Album Models is ready
 */
public interface ScanFileSystemThreadListener {
    public void onScanFileSystemComplet(AlbumModel[] albums) ;
}
