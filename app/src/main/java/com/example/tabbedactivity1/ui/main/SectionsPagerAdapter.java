package com.example.tabbedactivity1.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tabbedactivity1.R;
import com.example.tabbedactivity1.ui.main.bookkeeper_fragment.bookkeeperFragment;
import com.example.tabbedactivity1.ui.main.gallery_fragments.galleryFragment;
import com.example.tabbedactivity1.ui.main.phoneNumber_fragment.phonenumberFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        if (position==0)
            return phonenumberFragment.newInstance(position+1);
        else if (position==1)
            return galleryFragment.newInstance(position+2);
        else if (position==2)
            return bookkeeperFragment.newInstance(position+3);
        else
            return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}