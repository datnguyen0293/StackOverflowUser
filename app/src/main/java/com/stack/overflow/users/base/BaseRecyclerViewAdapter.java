package com.stack.overflow.users.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public abstract class BaseRecyclerViewAdapter<M, V extends BaseRecyclerViewHolder> extends RecyclerView.Adapter<V> {

    /**
     * The List Data.
     */
    private List<M> mListData;

    public List<M> getListData() {
        return mListData;
    }

    protected abstract int getLayoutResourceId();

    public void setListData(List<M> data) {
        if (this.mListData == null) {
            this.mListData = new ArrayList<>();
        } else {
            this.mListData.clear();
        }
        this.mListData.addAll(data);
        notifyDataSetChanged();
    }

    protected View getView(ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutResourceId(), viewGroup, false);
    }

    protected M getItemAt(int position) {
        if (-1 < position && position < mListData.size()) {
            return mListData.get(position);
        }
        return null;
    }

    @Override public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }
}