package com.stack.overflow.users.base;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public interface BaseView {
    void showLoadingDialog();

    void hideLoadingDialog();

    void showErrorDialog(String errorMessage);

    void showErrorDialog(Throwable throwable);

    BaseActivity activity();
}