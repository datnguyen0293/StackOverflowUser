package com.stack.overflow.users.application.model.response;

import com.google.gson.annotations.SerializedName;
import com.stack.overflow.users.application.model.UserItem;
import java.util.List;

/**
 * @author dat nguyen
 * @since 2019 Sep 12
 */

public class UsersResponse {
    @SerializedName("items") private List<UserItem> mListUserItems;
    @SerializedName("has_more") private boolean mHasMore;
    @SerializedName("quota_max") private int mQuotaMax;
    @SerializedName("quota_remaining") private int mQuotaRemaining;

    public List<UserItem> getListUserItems() {
        return mListUserItems;
    }

    public int getQuotaMax() {
        return mQuotaMax;
    }
}