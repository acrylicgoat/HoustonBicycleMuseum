/**
 * Copyright (c) 2016 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.houstonbicyclemuseum.fragment;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acrylicgoat.houstonbicyclemuseum.beans.Bike;
import com.acrylicgoat.houstonbicyclemuseum.R;
import com.acrylicgoat.houstonbicyclemuseum.beans.Bikes;
import com.acrylicgoat.houstonbicyclemuseum.view.SlidingTabLayout;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * based on Google Android Sample SlidingTabBasic - https://developer.android.com/samples/SlidingTabsBasic/project.html
 */
public class SlidingTabsFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.view_pager_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SamplePagerAdapter());
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        slidingTabLayout.setViewPager(viewPager);
    }

    class SamplePagerAdapter extends PagerAdapter
    {
        ArrayList<Bike> bikesList;

        public SamplePagerAdapter()
        {
            super();
            Bikes b = readJson();
            bikesList = b.getBikes();
            Collections.sort(bikesList);
        }

        @Override
        public int getCount()
        {
            return bikesList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o)
        {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            Bike bike = bikesList.get(position);
            return bike.getTitle();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View view = getActivity().getLayoutInflater().inflate(R.layout.bike_layout, container, false);
            container.addView(view);
            Bike bike = bikesList.get(position);
            ImageView bikeImage = (ImageView)view.findViewById(R.id.bikeImage);
            TextView bikeName = (TextView)view.findViewById(R.id.bikeName);
            TextView blurb = (TextView)view.findViewById(R.id.blurb);

            AssetManager assetManager = getActivity().getAssets();
            try
            {
                InputStream ims = assetManager.open(bike.getImage() + ".jpg");

                Drawable d = Drawable.createFromStream(ims, null);

                bikeImage.setImageDrawable(d);
            }
            catch(IOException ex)
            {
                Log.d("fragment", ex.toString());
            }

            bikeName.setText(bike.getTitle());
            if(bike.getSubtitle().equals("none"))
            {
                blurb.setText(bike.getBlurb());
            }
            else
            {
                blurb.setText(bike.getSubtitle() + "\n\n" + bike.getBlurb());
            }

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }

        private Bikes readJson()
        {
            AssetManager assets = getActivity().getAssets();
            Bikes bikes = new Bikes();

            Gson gson = new Gson();

            try
            {
                InputStream is = assets.open("bikes.json");
                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                bikes = gson.fromJson(bf,Bikes.class);
            }
            catch(IOException ioe)
            {
                Log.d("json", "Some problem: " + ioe.toString());
            }

            return bikes;
        }

    }

}
