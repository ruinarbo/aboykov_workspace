package com.samsung.mygallery.app.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.samsung.mygallery.app.R;
import com.samsung.mygallery.app.models.AlbumModel;

import java.util.ArrayList;

/**
 * Created by aboykov on 6/16/17.
 * This fragment represents album preview which has been implemented with ViewPager which allows to switch pictures on preview
 * swiping left/right. Glide is used to load pictures inside ImageView. Album model is passed as argument to create this fragment
 */
public class AlbumDetailsFragment extends Fragment{

    public static final String CURRENT_ALBUM = "currentAlbum";
    private ViewPager mAlbumDetailsViewPager;
    private PagerAdapter mPagerAdapter;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.album_details_layout, container, false);
        mAlbumDetailsViewPager = (ViewPager) layout.findViewById(R.id.album_view_pager);
        mPagerAdapter = new AlbumDetailesPagerAdapter(getContext(),((AlbumModel)getArguments().getParcelable(CURRENT_ALBUM)).getPictures());
        mAlbumDetailsViewPager.setAdapter(mPagerAdapter);
        return layout;
    }
//this adapter get list of pictures from album model and set it inside ViewPager
    private class AlbumDetailesPagerAdapter extends PagerAdapter {
       private Context mContext;
       private ArrayList<String>mPictures;
        private LayoutInflater mInflater;

        public AlbumDetailesPagerAdapter(Context context, ArrayList<String> pictures) {
            mContext =context;
            mPictures = pictures;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mInflater.inflate(R.layout.albumdetails_viewpager_item, container,
                    false);
            final ImageView pictureView = (ImageView) itemView.findViewById(R.id.iv_album_detailed_picture);
            container.addView(itemView);

            Glide.with(mContext)
                    .load(Uri.parse(mPictures.get(position)))
                    .placeholder(R.drawable.picture_placeholder)
                    .into(pictureView);


            return itemView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((FrameLayout) object);
        }

        @Override
        public int getCount() {
            return mPictures.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}
