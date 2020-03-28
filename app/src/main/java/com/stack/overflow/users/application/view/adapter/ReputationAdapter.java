package com.stack.overflow.users.application.view.adapter;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.stack.overflow.users.R;
import com.stack.overflow.users.application.model.Reputation;
import com.stack.overflow.users.application.view.adapter.viewholder.ReputationViewHolder;
import com.stack.overflow.users.base.BaseRecyclerViewAdapter;
import com.stack.overflow.users.base.utils.Utils;
import java.util.Locale;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */
public class ReputationAdapter extends BaseRecyclerViewAdapter<Reputation, ReputationViewHolder> {

    public ReputationAdapter() {
        // Do nothing
    }

    @Override public int getLayoutResourceId() {
        return R.layout.layout_reputation_item;
    }

    @NonNull @Override public ReputationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReputationViewHolder(getView(viewGroup));
    }

    @Override public void onBindViewHolder(@NonNull ReputationViewHolder holder, int i) {
        Reputation data = getItemAt(i);
        if (data != null) {
            holder.mTvChange.setText(String.format(Locale.getDefault(), "Change: %d", data.getReputationChange()));
            holder.mTvCreateAt.setText(String.format(Locale.getDefault(), "Create At: %s", Utils.formatDate(data.getCreationDate())));
            holder.mTvReputationType.setText(String.format(Locale.getDefault(), "Type: %s", data.getReputationHistoryType() != null ? data.getReputationHistoryType() : ""));
            holder.mTvPostId.setText(String.format(Locale.getDefault(), "Post ID: %d", data.getPostId()));
        }
    }
}