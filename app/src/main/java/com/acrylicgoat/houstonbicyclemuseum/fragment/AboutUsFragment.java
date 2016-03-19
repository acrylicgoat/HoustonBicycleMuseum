/**
 * Copyright (c) 2016 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.houstonbicyclemuseum.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acrylicgoat.houstonbicyclemuseum.R;
import com.acrylicgoat.houstonbicyclemuseum.adapters.AboutUsRecyclerViewAdapter;
import com.acrylicgoat.houstonbicyclemuseum.beans.About;

import java.util.ArrayList;

/**
 * Created by ed on 1/24/16.
 */
public class AboutUsFragment extends Fragment
{
    RecyclerView recyclerView;
    Activity activity;
    AboutUsRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        activity = getActivity();
        return inflater.inflate(R.layout.aboutus_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView)getView().findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AboutUsRecyclerViewAdapter(getContent(), R.layout.card_row, activity);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<About> getContent()
    {
        ArrayList<About> arl = new ArrayList<>();
        About a = new About();
        a.setTitle("Houston Bicycle Museum Location");
        a.setDetails("&nbsp;&nbsp;&nbsp;1313 Binz St.<br/>&nbsp;&nbsp;&nbsp;Houston, Tx 77054<br/>&nbsp;&nbsp;&nbsp;<a href=\\'https://www.google.com/maps/d/edit?mid=zQw3pXjaT2oI.kbgRFAwXqj0w&usp=sharing\\'>Google Map</a>");
        arl.add(a);
        About a2 = new About();
        a2.setTitle("Hours");
        //a2.setDetails("Tues - Wed. 10am - 5pm<br/>Thurs - Sat 10am - 7pm<br/>Sun 1pm - 6pm");
        a2.setDetails("<ul><li>&nbsp;&nbsp;&nbsp;&#8226;<strong> Tues-Wed:</strong> 10am - 5pm</li><br/><li>&nbsp;&nbsp;&nbsp;&#8226;<strong> Thur-Sat:</strong> 10am - 7pm</li><br/><li>&nbsp;&nbsp;&nbsp;&#8226;<strong> Sun:</strong> 1pm - 6pm</li><br/><li>&nbsp;&nbsp;&nbsp;&#8226;<strong> Closed:</strong> Monday</li><br/><li>&nbsp;&nbsp;&nbsp;&#8226;<strong> Free day:</strong> Thurs 3-7pm</li></ul>");
        arl.add(a2);
        About a3 = new About();
        a3.setTitle("Visit Us on the Web");
        a3.setDetails("&nbsp;&nbsp;&nbsp;<a href=\\'http://www.houstonbicyclemuseum.org/\\'>Museum Web Site</a>");
        arl.add(a3);
        return arl;
    }
}
