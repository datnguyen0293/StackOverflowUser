package com.stack.overflow.users.base.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author dat nguyen
 * @since 2019 Sep 12
 */

public abstract class RecyclerViewScrollEvent extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLayoutManager;

    protected RecyclerViewScrollEvent(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = mLayoutManager.getChildCount();
        int currentTotalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        if (!isLoading()
                && !isLastPage(currentTotalItemCount)
                && firstVisibleItemPosition >= 0
                && (visibleItemCount + firstVisibleItemPosition) >= currentTotalItemCount) {
            loadMoreItems();
        }
    }

    protected abstract void loadMoreItems();

    public abstract boolean isLastPage(long currentTotalItemCount);

    public abstract boolean isLoading();
}