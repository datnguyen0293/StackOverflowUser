package com.stack.overflow.users.application.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.stack.overflow.users.R;
import com.stack.overflow.users.application.model.Reputation;
import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.application.presenter.ReputationPresenter;
import com.stack.overflow.users.application.view.GetReputationView;
import com.stack.overflow.users.application.view.adapter.ReputationAdapter;
import com.stack.overflow.users.base.BaseActivity;
import com.stack.overflow.users.base.BaseFragment;
import com.stack.overflow.users.base.utils.RecyclerViewScrollEvent;
import com.stack.overflow.users.base.utils.Utils;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import butterknife.BindView;
/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */
public class ReputationActivity extends BaseActivity implements GetReputationView {

    private static final String ARG_USER_ITEM = "ARG_USER_ITEM";
    @BindView(R.id.imageAvatar) AppCompatImageView mImageAvatar;
    @BindView(R.id.tvUserName) AppCompatTextView mTvUserName;
    @BindView(R.id.tvReputation) AppCompatTextView mTvReputation;
    @BindView(R.id.tvLocation) AppCompatTextView mTvLocation;
    @BindView(R.id.tvLastAccessDate) AppCompatTextView mTvLastAccessDate;
    @BindView(R.id.rcvReputation) RecyclerView mRcvReputation;
    @BindView(R.id.btnBookmark) AppCompatImageButton mBtnBookmark;
    @BindView(R.id.tvNoData) AppCompatTextView mTvNoData;
    @Inject ReputationPresenter mPresenter;
    @Inject ReputationAdapter mAdapter;
    @Inject LinearLayoutManager mLinearLayoutManager;
    private UserItem mUserItem;
    private long mTotalItems = 0;
    private int mPage = 1;
    private static final int PAGE_SIZE = 30;

    public ReputationActivity() {
        super(R.layout.activity_reputation);
    }

    public static void start(BaseFragment baseFragment, UserItem userItem) {
        Intent starter = new Intent(baseFragment.activity(), ReputationActivity.class);
        starter.putExtra(ARG_USER_ITEM, userItem);
        baseFragment.startActivity(starter);
    }

    /**
     * Display user's information
     * @param data The user's information
     */
    private void displayUserInformation(UserItem data) {
        new Picasso.Builder(this).build().load(data.getUserAvatar()).memoryPolicy(MemoryPolicy.NO_CACHE).fit().into(mImageAvatar);
        mTvUserName.setText(data.getUserName() != null ? data.getUserName() : "");
        mTvLocation.setText(String.format(Locale.getDefault(), "Location: %s", data.getLocation() != null ? data.getLocation() : ""));
        mTvLastAccessDate.setText(String.format(Locale.getDefault(), "Last access date: %n%s", Utils.formatDate(data.getLastAccessDate())));
        mTvReputation.setText(String.format(Locale.getDefault(), "Reputation: %d", data.getReputation()));
    }

    /**
     * Initialize RecyclerView
     */
    private void initializeRecyclerView() {
        mRcvReputation.setHasFixedSize(true);
        mRcvReputation.setLayoutManager(mLinearLayoutManager);
        mRcvReputation.setAdapter(mAdapter);
        // Scroll to load more data
        mRcvReputation.addOnScrollListener(new RecyclerViewScrollEvent(mLinearLayoutManager) {
            @Override protected void loadMoreItems() {
                mTotalItems += PAGE_SIZE;
                mPage++;
                mPresenter.getListReputation(mUserItem.getUserId(), mPage, PAGE_SIZE, getString(R.string.stack_overflow_site), getString(R.string.sort_by), getString(R.string.order_by));
            }

            @Override public boolean isLastPage(long currentTotalItemCount) {
                return mTotalItems == currentTotalItemCount;
            }

            @Override public boolean isLoading() {
                return false;
            }
        });
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mBtnBookmark.setVisibility(View.GONE);
        initializeRecyclerView();
        mPresenter.bindView(this);
        if (getIntent() != null) {
            mUserItem = getIntent().getParcelableExtra(ARG_USER_ITEM);
            if (mUserItem != null) {
                displayUserInformation(mUserItem);
                mPresenter.getListReputation(mUserItem.getUserId(), mPage, PAGE_SIZE, getString(R.string.stack_overflow_site), getString(R.string.sort_by), getString(R.string.order_by));
            }
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unbindView();
            mPresenter = null;
        }
    }

    @Override public void onGetReputations(List<Reputation> reputations) {
        Utils.runOnUiSafeThread(() -> {
            mTvNoData.setVisibility(View.GONE);
            if (reputations != null) {
                mAdapter.setListData(reputations);
            }
        });
    }

    @Override public void onNoReputations() {
        mTvNoData.setVisibility(View.VISIBLE);
    }
}