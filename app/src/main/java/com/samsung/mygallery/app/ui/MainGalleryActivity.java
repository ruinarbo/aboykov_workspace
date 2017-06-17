package com.samsung.mygallery.app.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.samsung.mygallery.app.R;
import com.samsung.mygallery.app.models.AlbumModel;
import com.samsung.mygallery.app.utils.ScanFileSystemThread;
import com.samsung.mygallery.app.utils.ScanFileSystemThreadListener;

/**
 * Created by aboykov on 6/16/17.
 * This is main activity which implements  ScanFileSystemThreadListener to update UI when file system scan is ready
 * it's important to do filesystem scan in separate thread to not overload UI thread i case if we have big value of albums and
 * images in them. As gallery view i'm using RecyclerView instead regular ListView which allows us to improve performance of our app.
 */
public class MainGalleryActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, ScanFileSystemThreadListener{

    private RecyclerView mGalleryView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_gallery_activity);
        //Perms to read/write granted by user required for all android version after 23
        if(Build.VERSION.SDK_INT >= 23)
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mGalleryView = (RecyclerView) findViewById(R.id.rv_gallery);
        //Fixed size will also do optimization inside  RecyclerView
        mGalleryView.setHasFixedSize(true);
        mGalleryView.setLayoutManager(layoutManager);

    }
    @Override
    protected void onResume() {
        super.onResume();
        //Scan file system  every time when resume. We could also use cashing or store results in SharedPrefs to optimize it
        new ScanFileSystemThread(this).start();

    }


    @Override
    public void onScanFileSystemComplet(AlbumModel[] albums) {
        MainGalleryAdapter adapter = new MainGalleryAdapter(this, albums);
        mGalleryView.setAdapter(adapter);
    }

}
