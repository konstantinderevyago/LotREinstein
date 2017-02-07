package by.jetfire.lotreinstein.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import by.jetfire.lotreinstein.ui.fragment.ConditionsFragment;
import by.jetfire.lotreinstein.ui.fragment.TaskFragment;

/**
 * Created by Konstantin on 06.02.2017.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TaskFragment();
            case 1:
                return new ConditionsFragment();
        }
        return null;
    }

}
