package com.stack.overflow.users.application.view.adapter;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.stack.overflow.users.R;
import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.application.view.adapter.viewholder.UsersViewHolder;
import com.stack.overflow.users.application.view.listener.UsersListener;
import com.stack.overflow.users.base.BaseRecyclerViewAdapter;
import com.stack.overflow.users.base.utils.Utils;
import java.util.Locale;

/**
 * @author dat nguyen
 * @since 2019 Sep 12
 */
public class FavoriteUsersAdapter extends BaseRecyclerViewAdapter<UserItem, UsersViewHolder> {

    private UsersListener mListener;

    public void setListener(UsersListener listener) {
        mListener = listener;
    }

    public FavoriteUsersAdapter() {
        // Do nothing
    }

    private void bindData(@NonNull UsersViewHolder holder, UserItem data) {
        Drawable bookmarked = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_favorite_on);
        holder.mBtnBookmark.setImageDrawable(bookmarked);
        holder.mImageAvatar.setImageBitmap(data.getUserAvatarBitmap());
        holder.mBtnBookmark.setImageDrawable(bookmarked);
        holder.mTvUserName.setText(data.getUserName() != null ? data.getUserName() : "");
        holder.mTvLocation.setText(String.format(Locale.getDefault(), "Location: %s", data.getLocation() != null ? data.getLocation() : ""));
        holder.mTvLastAccessDate.setText(String.format(Locale.getDefault(), "Last access date: %n%s", Utils.formatDate(data.getLastAccessDate())));
        holder.mTvReputation.setText(String.format(Locale.getDefault(), "Reputation: %d", data.getReputation()));
    }

    @Override public int getLayoutResourceId() {
        return R.layout.layout_user_item;
    }

    @NonNull @Override public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UsersViewHolder(getView(viewGroup));
    }

    @Override public void onBindViewHolder(@NonNull UsersViewHolder holder, int i) {
        UserItem data = getItemAt(i);
        if (data != null) {
            bindData(holder, data);
            holder.mBtnBookmark.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.saveAsFavorite(data);
                }
            });
        }
    }
}