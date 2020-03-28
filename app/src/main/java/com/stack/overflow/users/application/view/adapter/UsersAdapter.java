package com.stack.overflow.users.application.view.adapter;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.stack.overflow.users.R;
import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.application.view.adapter.viewholder.UsersViewHolder;
import com.stack.overflow.users.application.view.listener.UsersListener;
import com.stack.overflow.users.base.BaseRecyclerViewAdapter;
import com.stack.overflow.users.base.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 * @author dat nguyen
 * @since 2019 Sep 12
 */
public class UsersAdapter extends BaseRecyclerViewAdapter<UserItem, UsersViewHolder> {

    private UsersListener mListener;
    private List<UserItem> mListFavoriteUsers;

    public void setListener(UsersListener mListener) {
        this.mListener = mListener;
    }

    public void setListFavoriteUsers(List<UserItem> listFavoriteUsers) {
        if (mListFavoriteUsers == null) {
            mListFavoriteUsers = new ArrayList<>();
        } else {
            mListFavoriteUsers.clear();
        }
        mListFavoriteUsers.addAll(listFavoriteUsers);
        notifyDataSetChanged();
    }

    public UsersAdapter() {
        // Do nothing
    }

    private boolean isFavorite(UserItem userItem) {
        if (mListFavoriteUsers != null && !mListFavoriteUsers.isEmpty()) {
            for (UserItem user : mListFavoriteUsers) {
                if (user.getUserId() == userItem.getUserId()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void bindData(@NonNull UsersViewHolder holder, UserItem data) {
        Drawable bookmarked = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_favorite_on);
        Drawable unBookmarked = ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.ic_favorite);
        holder.mBtnBookmark.setImageDrawable(unBookmarked);
        new Picasso.Builder(holder.itemView.getContext()).build().load(data.getUserAvatar()).memoryPolicy(MemoryPolicy.NO_CACHE).fit().into(holder.mImageAvatar);
        holder.mBtnBookmark.setImageDrawable(isFavorite(data) ? bookmarked : unBookmarked);
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
            holder.itemView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.goToDetail(data);
                }
            });
            holder.mBtnBookmark.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.saveAsFavorite(data);
                }
            });
        }
    }
}