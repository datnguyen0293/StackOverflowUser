package com.stack.overflow.users.application.view.adapter.viewholder;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import com.stack.overflow.users.R;
import com.stack.overflow.users.base.BaseRecyclerViewHolder;
import butterknife.BindView;
/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */
public class ReputationViewHolder extends BaseRecyclerViewHolder {

    @BindView(R.id.tvReputationType) public AppCompatTextView mTvReputationType;
    @BindView(R.id.tvChange) public AppCompatTextView mTvChange;
    @BindView(R.id.tvCreatedAt) public AppCompatTextView mTvCreateAt;
    @BindView(R.id.tvPostId) public AppCompatTextView mTvPostId;

    public ReputationViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}