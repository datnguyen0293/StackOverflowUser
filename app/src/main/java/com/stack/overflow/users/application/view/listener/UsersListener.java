package com.stack.overflow.users.application.view.listener;

import com.stack.overflow.users.application.model.UserItem;

/**
 * @author dat nguyen
 * @since 2019 Sep 12
 */
public interface UsersListener {
    void goToDetail(UserItem userItem);
    void saveAsFavorite(UserItem userItem);
}