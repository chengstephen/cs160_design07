package com.projects.cs160_design07;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by cstep on 4/18/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter{

    int mNumOfTabs;
    FeedFragment tab1;
    PatientListFragment tab2;
    InventoryFragment tab3;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if(tab1 == null) {
                    tab1 = new FeedFragment();
                }
                return tab1;
            case 1:
                if(tab2 == null) {
                    tab2 = new PatientListFragment();
                }
                return tab2;
            case 2:
                if(tab3 == null) {
                    tab3 = new InventoryFragment();
                }
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
