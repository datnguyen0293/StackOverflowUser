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
import com.stack.overflow.users.application.presenter.FavoriteUsersPresenter;
import com.stack.overflow.users.application.view.GetFavoriteUsersView;
import com.stack.overflow.users.application.view.adapter.FavoriteUsersAdapter;
import com.stack.overflow.users.application.view.listener.UsersListener;
import com.stack.overflow.users.base.BaseFragment;
import com.stack.overflow.users.base.utils.Utils;
import java.util.List;
import butterknife.BindView;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class FavoriteUsersFragment extends BaseFragment implements GetFavoriteUsersView, UsersListener {

    @BindView(R.id.rcvUsers) RecyclerView mRcvUsers;
    @BindView(R.id.tvNoData) AppCompatTextView mTvNoData;
    private FavoriteUsersAdapter mAdapter;
    private FavoriteUsersPresenter mPresenter;

    private void initializeRecyclerView() {
        mAdapter = new FavoriteUsersAdapter();
        mAdapter.setListener(this);
        mRcvUsers.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.activity());
        mRcvUsers.setLayoutManager(linearLayoutManager);
        mRcvUsers.setAdapter(mAdapter);
    }

    @Override protected int getLayoutResourceId() {
        return R.layout.fragment_favorite_users;
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView();
        if (mPresenter == null) {
            mPresenter = new FavoriteUsersPresenter();
        }
        mPresenter.bindView(this);
    }

    @Override public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.unbindView();
            mPresenter = null;
        }
        super.onDestroyView();
    }

    @Override public void onFragmentResume() {
        super.onFragmentResume();
        mPresenter.getFavoriteUsers();
    }

    @Override public void goToDetail(UserItem userItem) {
        // Don't go to detail
    }

    @Override public void saveAsFavorite(UserItem userItem) {
        mPresenter.saveFavoriteUser(userItem);
    }

    @Override public void onGetFavoriteUsers(List<UserItem> userItemList) {
        Utils.runOnUiSafeThread(() -> {
            mTvNoData.setVisibility(View.GONE);
            mAdapter.setListData(userItemList);
        });
    }

    @Override public void onNoFavoriteUsers() {
        mTvNoData.setVisibility(View.VISIBLE);
    }

    @Override public void onSaveFavoriteUsers() {
        mPresenter.getFavoriteUsers();
    }
}