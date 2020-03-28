package com.stack.overflow.users.application.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * @author dat nguyen
 * @since 2019 Sep 12
 */

public class UserItem implements Parcelable {
    @SerializedName("user_id") private long mUserId;
    @SerializedName("display_name") private String mUserName;
    @SerializedName("profile_image") private String mUserAvatar;
    @SerializedName("reputation") private long mReputation;
    @SerializedName("last_access_date") private long mLastAccessDate;
    @SerializedName("location") private String mLocation;
    private Bitmap mUserAvatarBitmap;

    public Bitmap getUserAvatarBitmap() {
        return mUserAvatarBitmap;
    }

    public void setUserAvatarBitmap(Bitmap userAvatarBitmap) {
        mUserAvatarBitmap = userAvatarBitmap;
    }

    public long getUserId() {
        return mUserId;
    }

    public void setUserId(long mUserId) {
        this.mUserId = mUserId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getUserAvatar() {
        return mUserAvatar;
    }

    public long getReputation() {
        return mReputation;
    }

    public void setReputation(long mReputation) {
        this.mReputation = mReputation;
    }

    public long getLastAccessDate() {
        return mLastAccessDate;
    }

    public void setLastAccessDate(long mLastAccessDate) {
        this.mLastAccessDate = mLastAccessDate;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public UserItem() {
        mUserId = -1;
        mUserName = "";
        mUserAvatar = "";
        mReputation = -1;
        mLastAccessDate = -1;
        mLocation = "";
    }

    private UserItem(Parcel in) {
        mUserId = in.readLong();
        mUserName = in.readString();
        mUserAvatar = in.readString();
        mReputation = in.readLong();
        mLastAccessDate = in.readLong();
        mLocation = in.readString();
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        @Override public UserItem createFromParcel(Parcel in) {
            return new UserItem(in);
        }

        @Override public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mUserId);
        dest.writeString(mUserName);
        dest.writeString(mUserAvatar);
        dest.writeLong(mReputation);
        dest.writeLong(mLastAccessDate);
        dest.writeString(mLocation);
    }
}