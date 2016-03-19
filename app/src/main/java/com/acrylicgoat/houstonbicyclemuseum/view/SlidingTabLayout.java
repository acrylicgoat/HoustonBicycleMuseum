/**
 * Copyright (c) 2016 Acrylic Goat Software
 *
 * This software is subject to the provisions of the GNU Lesser General
 * Public License Version 3 (LGPL).  See LICENSE.txt for details.
 */
package com.acrylicgoat.houstonbicyclemuseum.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

/**
 * based on Google Android Sample SlidingTabBasic - https://developer.android.com/samples/SlidingTabsBasic/project.html
 */
public class SlidingTabLayout extends HorizontalScrollView
{


    public interface TabColorizer
    {


        int getIndicatorColor(int position);

        int getDividerColor(int position);

    }

    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;

    private int titleOffset;

    private int tabViewLayoutId;
    private int tabViewTextViewId;

    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener;

    private final SlidingTabStrip tabStrip;

    public SlidingTabLayout(Context context)
    {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);

        titleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        tabStrip = new SlidingTabStrip(context);
        addView(tabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }


    public void setCustomTabColorizer(TabColorizer tabColorizer)
    {
        tabStrip.setCustomTabColorizer(tabColorizer);
    }


    public void setSelectedIndicatorColors(int... colors)
    {
        tabStrip.setSelectedIndicatorColors(colors);
    }


    public void setDividerColors(int... colors)
    {
        tabStrip.setDividerColors(colors);
    }


    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener)
    {
        viewPagerPageChangeListener = listener;
    }


    public void setCustomTabView(int layoutResId, int textViewId)
    {
        tabViewLayoutId = layoutResId;
        tabViewTextViewId = textViewId;
    }


    public void setViewPager(ViewPager viewPager)
    {
        tabStrip.removeAllViews();

        this.viewPager = viewPager;
        if (viewPager != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }


    protected TextView createDefaultTabView(Context context)
    {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {

            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            textView.setBackgroundResource(outValue.resourceId);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        {
            textView.setAllCaps(true);
        }

        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
        textView.setPadding(padding, padding, padding, padding);

        return textView;
    }

    private void populateTabStrip()
    {
        final PagerAdapter adapter = viewPager.getAdapter();
        final View.OnClickListener tabClickListener = new TabClickListener();

        for (int i = 0; i < adapter.getCount(); i++)
        {
            View tabView = null;
            TextView tabTitleView = null;

            if (tabViewLayoutId != 0)
            {
                tabView = LayoutInflater.from(getContext()).inflate(tabViewLayoutId, tabStrip, false);
                tabTitleView = (TextView) tabView.findViewById(tabViewTextViewId);
            }

            if (tabView == null)
            {
                tabView = createDefaultTabView(getContext());
            }

            if (tabTitleView == null && TextView.class.isInstance(tabView))
            {
                tabTitleView = (TextView) tabView;
            }

            tabTitleView.setText(adapter.getPageTitle(i));
            tabView.setOnClickListener(tabClickListener);

            tabStrip.addView(tabView);
        }
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        if (viewPager != null)
        {
            scrollToTab(viewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset)
    {
        final int tabStripChildCount = tabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount)
        {
            return;
        }

        View selectedChild = tabStrip.getChildAt(tabIndex);
        if (selectedChild != null)
        {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0)
            {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= titleOffset;
            }

            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener
    {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {
            int tabStripChildCount = tabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount))
            {
                return;
            }

            tabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = tabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null) ? (int) (positionOffset * selectedTitle.getWidth()) : 0;
            scrollToTab(position, extraOffset);

            if (viewPagerPageChangeListener != null)
            {
                viewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            mScrollState = state;

            if (viewPagerPageChangeListener != null)
            {
                viewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position)
        {
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE)
            {
                tabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }

            if (viewPagerPageChangeListener != null)
            {
                viewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            for (int i = 0; i < tabStrip.getChildCount(); i++)
            {
                if (v == tabStrip.getChildAt(i))
                {
                    viewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

}
