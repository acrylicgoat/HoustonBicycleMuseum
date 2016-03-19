/**
 * Copyright (c) 2016 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.houstonbicyclemuseum.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.acrylicgoat.houstonbicyclemuseum.R;
import com.acrylicgoat.houstonbicyclemuseum.activity.AboutUsActivity;
import com.acrylicgoat.houstonbicyclemuseum.activity.ViewPagerActivity;

public class LandingFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.landing_layout2, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ImageView iv = (ImageView) view.findViewById(R.id.landingImage1);
        iv.setImageResource(R.drawable.launch1);
        iv.setClickable(true);
        iv.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //open about us
                Intent aboutIntent = new Intent(getActivity().getApplicationContext(), AboutUsActivity.class);
                aboutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(aboutIntent);
            }
        });
        ImageView iv2 = (ImageView) view.findViewById(R.id.landingImage2);
        iv2.setImageResource(R.drawable.launch2);
        iv2.setClickable(true);
        iv2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //open gallery
                Intent mainIntent = new Intent(getActivity().getApplicationContext(), ViewPagerActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);

            }
        });

    }
}
