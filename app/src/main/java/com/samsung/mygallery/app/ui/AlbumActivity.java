package com.samsung.mygallery.app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.samsung.mygallery.app.R;
import com.samsung.mygallery.app.models.AlbumModel;

/**
 * Created by aboykov on 6/16/17.
 */
public class AlbumActivity extends AppCompatActivity {
    public static final String EXTRA_ALBUM_MODEL = "extraAlbumModel";

    private RecyclerView mAlbumView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mAlbumView = (RecyclerView) findViewById(R.id.rv_album);
        mAlbumView.setHasFixedSize(true);
        mAlbumView.setLayoutManager(layoutManager);
        AlbumModel album = getIntent().getParcelableExtra(EXTRA_ALBUM_MODEL);
        AlbumAdapter adapter = new AlbumAdapter(this, album.getAlbumContent());
        mAlbumView.setAdapter(adapter);
    }
}
