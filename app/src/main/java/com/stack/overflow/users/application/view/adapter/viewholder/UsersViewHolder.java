package com.stack.overflow.users.application.view.adapter.viewholder;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.stack.overflow.users.R;
import com.stack.overflow.users.base.BaseRecyclerViewHolder;
import butterknife.BindView;
/**
 * @author dat nguyen
 * @since 2019 Sep 12
 */
public class UsersViewHolder extends BaseRecyclerViewHolder {

    @BindView(R.id.imageAvatar) public AppCompatImageView mImageAvatar;
    @BindView(R.id.tvUserName) public AppCompatTextView mTvUserName;
    @BindView(R.id.tvReputation) public AppCompatTextView mTvReputation;
    @BindView(R.id.tvLocation) public AppCompatTextView mTvLocation;
    @BindView(R.id.tvLastAccessDate) public AppCompatTextView mTvLastAccessDate;
    @BindView(R.id.btnBookmark) public AppCompatImageButton mBtnBookmark;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}