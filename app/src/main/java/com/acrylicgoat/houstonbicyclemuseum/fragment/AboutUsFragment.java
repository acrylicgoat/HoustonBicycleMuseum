/**
 * Copyright (c) 2016 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.houstonbicyclemuseum.fragment;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acrylicgoat.houstonbicyclemuseum.R;
import com.acrylicgoat.houstonbicyclemuseum.adapters.AboutUsRecyclerViewAdapter;
import com.acrylicgoat.houstonbicyclemuseum.beans.About;
import com.acrylicgoat.houstonbicyclemuseum.beans.AboutList;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


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
        AboutList aboutList = readJson();
        ArrayList<About> arl = aboutList.getAboutList();
        return arl;
    }

    private AboutList readJson()
    {
        AssetManager assets = getActivity().getAssets();
        AboutList aboutList = new AboutList();

        Gson gson = new Gson();

        try
        {
            InputStream is = assets.open("about.json");
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
            aboutList = gson.fromJson(bf,AboutList.class);
        }
        catch(IOException ioe)
        {
            Log.d("json", "Some problem: " + ioe.toString());
        }

        return aboutList;
    }
}
