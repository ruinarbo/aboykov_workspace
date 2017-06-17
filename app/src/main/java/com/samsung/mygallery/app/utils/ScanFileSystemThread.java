package com.samsung.mygallery.app.utils;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import com.samsung.mygallery.app.models.AlbumModel;

import java.io.File;
import java.util.ArrayList;

 /**
 * Created by aboykov on 6/16/17
 * Using this task we will scan assets folder and creates array of album models
 */

public class ScanFileSystemThread extends Thread{
    private static final String TAG = "ScanFileSystemThread";
    private static final String DIR_NAME = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
            + "/MyGallery/";
    private ScanFileSystemThreadListener listener;
    public ScanFileSystemThread(ScanFileSystemThreadListener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        try {

            File rootDirectory = new File(DIR_NAME);
            if(!rootDirectory.exists()){
                if(!rootDirectory.mkdir()){
                    Log.e(TAG, "Failed to create directory");
                }
            }else{
                File[] files = rootDirectory.listFiles();
                AlbumModel[]albums = new AlbumModel[files.length];
                for (int i = 0; i < files.length ; ++i) {
                    if(files[i].isDirectory()){
                        File[] pictures = files[i].listFiles();
                        ArrayList<String> paths = new ArrayList<String>();
                        for(int j = 0; j < pictures.length ; j++) {
                            paths.add(Uri.fromFile(pictures[j]).toString());
                        }
                        albums[i] = new AlbumModel(files[i].getName(),paths);
                    }
                }
                listener.onScanFileSystemComplet(albums);
            }

        } catch (Throwable t) {
            Log.e(TAG, "ScanFileSystemThread error", t);
        }
    }
}
