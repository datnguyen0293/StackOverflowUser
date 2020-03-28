package com.stack.overflow.users.application.presenter;

import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.GetFavoriteUsersView;
import com.stack.overflow.users.base.BasePresenter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class FavoriteUsersPresenter extends BasePresenter<GetFavoriteUsersView> {

    public FavoriteUsersPresenter() {
        super();
        // Do nothing
    }

    public void getFavoriteUsers() {
        Objects.requireNonNull(view()).showLoadingDialog();
        mDisposable = mServices.getFavoriteUsers(new ServiceCall.ApiCallback() {
            @Override
            public void responseSucceed(Object obj) {
                List<UserItem> userItemList = new ArrayList<>();
                for (Object o : (List<?>) obj) {
                    userItemList.add((UserItem) o);
                }
                if (userItemList.isEmpty()) {
                    Objects.requireNonNull(view()).onNoFavoriteUsers();
                } else {
                    Objects.requireNonNull(view()).onGetFavoriteUsers(userItemList);
                }
                Objects.requireNonNull(view()).hideLoadingDialog();
            }

            @Override
            public void responseFail(String errorMessage) {
                Objects.requireNonNull(view()).onNoFavoriteUsers();
                Objects.requireNonNull(view()).hideLoadingDialog();
            }

            @Override
            public void callbackFail(Throwable throwable) {
                Objects.requireNonNull(view()).onNoFavoriteUsers();
                getNetErrorConsumer(throwable);
                Objects.requireNonNull(view()).hideLoadingDialog();
            }
        });
        mCompositeDisposable.add(mDisposable);
    }

    public void saveFavoriteUser(UserItem userItem) {
        Objects.requireNonNull(view()).showLoadingDialog();
        mDisposable = mServices.saveFavoriteUser(userItem, new ServiceCall.ApiCallback() {
            @Override
            public void responseSucceed(Object obj) {
                Objects.requireNonNull(view()).onSaveFavoriteUsers();
                Objects.requireNonNull(view()).hideLoadingDialog();
            }

            @Override
            public void responseFail(String errorMessage) {
                // Do nothing
            }

            @Override
            public void callbackFail(Throwable throwable) {
                getNetErrorConsumer(throwable);
                Objects.requireNonNull(view()).hideLoadingDialog();
            }
        });
        mCompositeDisposable.add(mDisposable);
    }
}