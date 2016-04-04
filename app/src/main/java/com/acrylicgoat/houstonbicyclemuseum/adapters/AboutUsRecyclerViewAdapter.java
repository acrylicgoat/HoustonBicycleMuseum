/**
 * Copyright (c) 2016 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.houstonbicyclemuseum.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acrylicgoat.houstonbicyclemuseum.R;
import com.acrylicgoat.houstonbicyclemuseum.activity.WebviewActivity;
import com.acrylicgoat.houstonbicyclemuseum.beans.About;

import java.util.ArrayList;


public class AboutUsRecyclerViewAdapter extends RecyclerView.Adapter<AboutUsRecyclerViewAdapter.ViewHolder>
{
    private int rowLayout;
    Context context;
    private ArrayList<About> contentList;

    public AboutUsRecyclerViewAdapter(ArrayList<About> content, int rowLayout, Context context)
    {
        contentList = content;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v,contentList);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i)
    {
        About about = contentList.get(i);
        Log.d("rv","title: " + about.getTitle() + " details: "+ about.getDetails() + " logo: "+ about.getLogo());
        viewHolder.title.setText(about.getTitle());
        viewHolder.details.setText(Html.fromHtml(about.getDetails()));
        String logo = about.getLogo();
        if(logo.equals("ic_account_balance_black_24dp"))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_account_balance_black_24dp);
        }
        else if(logo.equals("ic_access_time_black_24dp"))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_access_time_black_24dp);
        }
        else if(logo.equals("ic_language_black_24dp"))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_language_black_24dp);
        }
        else if(logo.equals("ic_check_circle_black_24dp"))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_check_circle_black_24dp);
        }
        else if(logo.equals("ic_mood_black_24dp"))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_mood_black_24dp);
        }
        else if(logo.equals("ic_mail_outline_black_24dp"))
        {
            viewHolder.logo.setImageResource(R.drawable.ic_mail_outline_black_24dp);
        }
        Linkify.addLinks(viewHolder.details, Linkify.WEB_URLS);

    }

    @Override
    public int getItemCount()
    {
        return contentList == null ? 0 : contentList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView title;
        private TextView details;
        private ImageView logo;
        ArrayList<About> contentList;

        public ViewHolder(View itemView, ArrayList<About> contentList)
        {
            super(itemView);
            this.contentList = contentList;

            title = (TextView) itemView.findViewById(R.id.title);
            details = (TextView)itemView.findViewById(R.id.details);
            logo = (ImageView) itemView.findViewById(R.id.logoView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v)
        {
            int position = getAdapterPosition();
            //Log.d("onClick()","position: " + position);

            if(position == 1)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/d/edit?mid=zQw3pXjaT2oI.kbgRFAwXqj0w&usp=sharing"));
                v.getContext().startActivity(browserIntent);
            }
            else if(position == 3)
            {
                //Intent wv = new Intent(v.getContext(), WebviewActivity.class);
                //wv.putExtra("webcontent", "http://www.houstonbicyclemuseum.org");
                //v.getContext().startActivity(wv);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.houstonbicyclemuseum.org"));
                v.getContext().startActivity(browserIntent);
            }
            else
            {
                return;
            }

        }


    }

}
