package com.stack.overflow.users.application.view.activity;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.google.android.material.tabs.TabLayout;
import com.stack.overflow.users.R;
import com.stack.overflow.users.application.view.adapter.UsersPagerAdapter;
import com.stack.overflow.users.application.view.fragment.AllUsersFragment;
import com.stack.overflow.users.application.view.fragment.FavoriteUsersFragment;
import com.stack.overflow.users.base.BaseActivity;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import butterknife.BindView;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    @BindView(R.id.view_pager) ViewPager mViewPager;

    @Override protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewPager();
        setSelectedTab(0);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupViewPager() {
        UsersPagerAdapter mPagerAdapter = new UsersPagerAdapter(fragmentManager());
        mPagerAdapter.addFragment(new AllUsersFragment(), getString(R.string.all));
        mPagerAdapter.addFragment(new FavoriteUsersFragment(), getString(R.string.favorite));

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(true, new AccordionTransformer());

        mTabLayout.setupWithViewPager(mViewPager, true);

        View homeTabView = getLayoutInflater().inflate(R.layout.tab_item, mTabLayout, false);
        ((AppCompatImageView) homeTabView.findViewById(R.id.icon)).setImageResource(R.drawable.ic_home);
        ((AppCompatTextView) homeTabView.findViewById(R.id.text)).setText(getString(R.string.all));
        Objects.requireNonNull(mTabLayout.getTabAt(0)).setCustomView(homeTabView);

        View favoriteTabView = getLayoutInflater().inflate(R.layout.tab_item, mTabLayout, false);
        ((AppCompatImageView) favoriteTabView.findViewById(R.id.icon)).setImageResource(R.drawable.ic_favorite);
        ((AppCompatTextView) favoriteTabView.findViewById(R.id.text)).setText(getString(R.string.favorite));
        Objects.requireNonNull(mTabLayout.getTabAt(1)).setCustomView(favoriteTabView);

        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override public void onTabSelected(@NotNull TabLayout.Tab tab) {
                super.onTabSelected(tab);
                setSelectedTab(tab.getPosition());
                mPagerAdapter.getItem(tab.getPosition()).onFragmentResume();
            }
        });
    }

    private void setSelectedTab(int position) {
        int size = mTabLayout.getTabCount();
        for (int i = 0; i < size; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                View view = tab.getCustomView();
                if (view != null) {
                    ((AppCompatImageView) view.findViewById(R.id.icon)).setColorFilter(ContextCompat.getColor(activity(), i == position ? R.color.red : R.color.grey), PorterDuff.Mode.SRC_IN);
                    ((AppCompatTextView) view.findViewById(R.id.text)).setTextColor(ContextCompat.getColor(activity(), i == position ? R.color.red : R.color.grey));
                }
            }
        }
    }
}
