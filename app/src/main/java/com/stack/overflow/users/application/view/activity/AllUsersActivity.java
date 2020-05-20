package com.stack.overflow.users.application.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.stack.overflow.users.R;
import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.application.presenter.AllUsersPresenter2;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.GetAllUsersView;
import com.stack.overflow.users.application.view.adapter.UsersAdapter;
import com.stack.overflow.users.base.BaseActivity;
import com.stack.overflow.users.base.utils.Utils;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AllUsersActivity extends BaseActivity implements GetAllUsersView {

    @BindView(R.id.rcvUsers) RecyclerView mRcvUsers;
    @BindView(R.id.tvNoData) AppCompatTextView mTvNoData;
    @Inject AllUsersPresenter2 mAllUserPresenter;
    @Inject UsersAdapter mAdapter;
    private long mTotalItems = 0;
    private int mPage = 1;
    private static final int PAGE_SIZE = 30;

    public AllUsersActivity() {
        super(R.layout.fragment_all_users);
    }

    /**
     * Initialize RecyclerView
     */
    private void initializeRecyclerView() {
        mRcvUsers.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.activity());
        mRcvUsers.setLayoutManager(linearLayoutManager);
        mRcvUsers.setAdapter(mAdapter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeRecyclerView();
        mAllUserPresenter.bindView(this);
        mAllUserPresenter.getListUser(PAGE_SIZE, getString(R.string.stack_overflow_site), getString(R.string.sort_by), getString(R.string.order_by));
    }

    @Override public void onGetAllUsers(List<UserItem> userItemList) {
        Utils.runOnUiSafeThread(() -> {
            mTvNoData.setVisibility(View.GONE);
            mAdapter.setListData(userItemList);
        });
    }

    @Override public void onNoUsers() {
        mTvNoData.setVisibility(View.VISIBLE);
    }
}
