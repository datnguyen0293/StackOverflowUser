package com.stack.overflow.users.application.view.fragment;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.stack.overflow.users.R;
import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.application.presenter.AllUsersPresenter;
import com.stack.overflow.users.application.presenter.FavoriteUsersPresenter;
import com.stack.overflow.users.application.view.GetAllUsersView;
import com.stack.overflow.users.application.view.GetFavoriteUsersView;
import com.stack.overflow.users.application.view.activity.ReputationActivity;
import com.stack.overflow.users.application.view.adapter.UsersAdapter;
import com.stack.overflow.users.application.view.listener.UsersListener;
import com.stack.overflow.users.base.BaseFragment;
import com.stack.overflow.users.base.utils.RecyclerViewScrollEvent;
import com.stack.overflow.users.base.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class AllUsersFragment extends BaseFragment implements GetAllUsersView, GetFavoriteUsersView, UsersListener {

    @BindView(R.id.rcvUsers) RecyclerView mRcvUsers;
    @BindView(R.id.tvNoData) AppCompatTextView mTvNoData;
    @Inject AllUsersPresenter mAllUserPresenter;
    @Inject FavoriteUsersPresenter mFavoriteUsersPresenter;
    @Inject UsersAdapter mAdapter;
    private long mTotalItems = 0;
    private int mPage = 1;
    private static final int PAGE_SIZE = 30;

    public AllUsersFragment() {
        super(R.layout.fragment_all_users);
    }

    /**
     * Initialize RecyclerView
     */
    private void initializeRecyclerView() {
        mAdapter.setListener(this);
        mRcvUsers.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.activity());
        mRcvUsers.setLayoutManager(linearLayoutManager);
        mRcvUsers.setAdapter(mAdapter);
        // Scroll to load more data
        mRcvUsers.addOnScrollListener(new RecyclerViewScrollEvent(linearLayoutManager) {
            @Override protected void loadMoreItems() {
                mTotalItems += PAGE_SIZE;
                mPage++;
                mAllUserPresenter.getListUser(mPage, PAGE_SIZE, getString(R.string.stack_overflow_site), getString(R.string.sort_by), getString(R.string.order_by));
            }

            @Override public boolean isLastPage(long currentTotalItemCount) {
                return mTotalItems == currentTotalItemCount;
            }

            @Override public boolean isLoading() {
                return false;
            }
        });
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
        mAllUserPresenter.bindView(this);
        mFavoriteUsersPresenter.bindView(this);
        mAllUserPresenter.getListUser(mPage, PAGE_SIZE, getString(R.string.stack_overflow_site), getString(R.string.sort_by), getString(R.string.order_by));
    }

    @Override public void onDestroyView() {
        if (mAllUserPresenter != null) {
            mAllUserPresenter.unbindView();
            mAllUserPresenter = null;
        }
        if (mFavoriteUsersPresenter != null) {
            mFavoriteUsersPresenter.unbindView();
            mFavoriteUsersPresenter = null;
        }
        super.onDestroyView();
    }

    @Override public void onFragmentResume() {
        super.onFragmentResume();
        mFavoriteUsersPresenter.getFavoriteUsers();
    }

    @Override public void onGetAllUsers(List<UserItem> userItemList) {
        Utils.runOnUiSafeThread(() -> {
            mTvNoData.setVisibility(View.GONE);
            mAdapter.setListener(this);
            mAdapter.setListData(userItemList);
            mFavoriteUsersPresenter.getFavoriteUsers();
        });
    }

    @Override public void onNoFavoriteUsers() {
        mAdapter.setListFavoriteUsers(new ArrayList<>());
        mTvNoData.setVisibility(View.GONE);
    }

    @Override public void onNoUsers() {
        mTvNoData.setVisibility(View.VISIBLE);
    }

    @Override public void onGetFavoriteUsers(List<UserItem> userItemList) {
        mAdapter.setListFavoriteUsers(userItemList);
    }

    @Override public void goToDetail(UserItem userItem) {
        ReputationActivity.start(this, userItem);
    }

    @Override public void saveAsFavorite(UserItem userItem) {
        mFavoriteUsersPresenter.saveFavoriteUser(userItem);
    }

    @Override public void onSaveFavoriteUsers() {
        mFavoriteUsersPresenter.getFavoriteUsers();
    }
}