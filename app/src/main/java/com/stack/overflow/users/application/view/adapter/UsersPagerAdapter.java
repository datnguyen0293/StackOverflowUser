package com.stack.overflow.users.application.view.adapter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.stack.overflow.users.base.BaseFragment;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class UsersPagerAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mLstFragments;
    private List<String> mLstFragmentsTitles;

    public UsersPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mLstFragments = new ArrayList<>();
        mLstFragmentsTitles = new ArrayList<>();
    }

    public void addFragment(BaseFragment fragment, String title) {
        mLstFragments.add(fragment);
        mLstFragmentsTitles.add(title);
    }

    @Override public int getCount() {
        return mLstFragments != null ? mLstFragments.size() : 0;
    }

    @NotNull @Override public BaseFragment getItem(int position) {
        return mLstFragments.get(position);
    }

    @Override public CharSequence getPageTitle(int position) {
        return mLstFragmentsTitles.get(position);
    }
}