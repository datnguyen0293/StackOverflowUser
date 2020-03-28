package com.stack.overflow.users.application.model.response;

import com.google.gson.annotations.SerializedName;
import com.stack.overflow.users.application.model.Reputation;
import java.util.List;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class ReputationResponse {
    @SerializedName("items") private List<Reputation> mListReputationItems;
    @SerializedName("has_more") private boolean mHasMore;
    @SerializedName("quota_max") private int mQuotaMax;
    @SerializedName("quota_remaining") private int mQuotaRemaining;

    public List<Reputation> getListReputationItems() {
        return mListReputationItems;
    }

    public int getQuotaMax() {
        return mQuotaMax;
    }
}