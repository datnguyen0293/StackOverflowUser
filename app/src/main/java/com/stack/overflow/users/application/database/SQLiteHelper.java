package com.stack.overflow.users.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.base.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dat nguyen
 * @since 2020 Feb 22
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    /**
     * Version number to upgrade database version
     * each time if you Add, Edit table, you need to change the version number.
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Database Name
     */
    private static final String DATABASE_NAME = "StackOverflowUser.db";
    /**
     * Table
     */
    private static final String TABLE_NAME = "User";
    private static final String COLUMN_USER_ID = "UserId";
    private static final String COLUMN_USER_NAME = "UserName";
    private static final String COLUMN_USER_AVATAR = "UserAvatar";
    private static final String COLUMN_REPUTATION = "Reputation";
    private static final String COLUMN_LAST_ACCESS_DATE = "LastAccessDate";
    private static final String COLUMN_LOCATION = "Location";
    private Context mContext;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public List<UserItem> getFavoriteUsers() {
        //Open connection to read only
        SQLiteDatabase database = getReadableDatabase();
        final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = database.rawQuery(SELECT_QUERY, null);

        List<UserItem> userItemList = new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserItem userItem = new UserItem();
                userItem.setUserId(cursor.getLong(cursor.getColumnIndex(COLUMN_USER_ID)));
                userItem.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                userItem.setUserAvatarBitmap(Utils.toBitmap(cursor.getBlob(cursor.getColumnIndex(COLUMN_USER_AVATAR))));
                userItem.setReputation(cursor.getLong(cursor.getColumnIndex(COLUMN_REPUTATION)));
                userItem.setLastAccessDate(cursor.getLong(cursor.getColumnIndex(COLUMN_LAST_ACCESS_DATE)));
                userItem.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                userItemList.add(userItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return userItemList;
    }

    public void updateFavoriteUsers(UserItem userItem) throws IOException {
        //Open connection to write data
        SQLiteDatabase database = getWritableDatabase();
        final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = " + userItem.getUserId();
        Cursor cursor = database.rawQuery(SELECT_QUERY, null);

        if (cursor.getCount() > 0) {
            database.delete(TABLE_NAME, COLUMN_USER_ID + "= ?", new String[]{String.valueOf(userItem.getUserId())});
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, userItem.getUserId());
            values.put(COLUMN_USER_NAME, userItem.getUserName());
            values.put(COLUMN_USER_AVATAR, Utils.toBytes(mContext, userItem.getUserAvatar()));
            values.put(COLUMN_REPUTATION, userItem.getReputation());
            values.put(COLUMN_LAST_ACCESS_DATE, userItem.getLastAccessDate());
            values.put(COLUMN_LOCATION, userItem.getLocation());
            // Inserting Row
            database.insert(TABLE_NAME, null, values);
        }
        cursor.close();
        database.close(); // Closing database connection
    }

    @Override public void onCreate(SQLiteDatabase database) {
        final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME
                + " (" + COLUMN_USER_ID + " LONG PRIMARY KEY, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_AVATAR + " BLOB, "
                + COLUMN_REPUTATION + " LONG, "
                + COLUMN_LAST_ACCESS_DATE + " LONG, "
                + COLUMN_LOCATION + " TEXT)";
        database.execSQL(CREATE_TABLE_QUERY);
    }

    @Override public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(database);
    }
}