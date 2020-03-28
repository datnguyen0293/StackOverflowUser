package com.stack.overflow.users.application.view;

import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.base.BaseView;
import java.util.List;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public interface GetFavoriteUsersView extends BaseView {
    void onGetFavoriteUsers(List<UserItem> userItemList);
    void onNoFavoriteUsers();
    void onSaveFavoriteUsers();
}