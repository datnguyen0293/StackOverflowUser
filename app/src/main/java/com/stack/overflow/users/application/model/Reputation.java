package com.stack.overflow.users.application.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */
public class Reputation implements Parcelable { //prevent error unused fields from sonarqube

    @SerializedName("reputation_history_type") private String mReputationHistoryType;
    @SerializedName("reputation_change") private int mReputationChange;
    @SerializedName("post_id") private long mPostId;
    @SerializedName("creation_date") private long mCreationDate;
    @SerializedName("user_id") private long mUserId;

    private Reputation(Parcel in) {
        mReputationHistoryType = in.readString();
        mReputationChange = in.readInt();
        mPostId = in.readLong();
        mCreationDate = in.readLong();
        mUserId = in.readLong();
    }

    public static final Creator<Reputation> CREATOR = new Creator<Reputation>() {
        @Override public Reputation createFromParcel(Parcel in) {
            return new Reputation(in);
        }

        @Override public Reputation[] newArray(int size) {
            return new Reputation[size];
        }
    };

    public String getReputationHistoryType() {
        return mReputationHistoryType;
    }

    public int getReputationChange() {
        return mReputationChange;
    }

    public long getPostId() {
        return mPostId;
    }

    public long getCreationDate() {
        return mCreationDate;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mReputationHistoryType);
        dest.writeInt(mReputationChange);
        dest.writeLong(mPostId);
        dest.writeLong(mCreationDate);
        dest.writeLong(mUserId);
    }
}