package com.stack.overflow.users.application.presenter;

import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.application.view.GetAllUsersView;
import com.stack.overflow.users.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public class AllUsersPresenter2 extends BasePresenter<GetAllUsersView> {

    private Disposable[] disposables;
    private int page;

    public AllUsersPresenter2(ServiceCall services, CompositeDisposable compositeDisposable) {
        mServices = services;
        mCompositeDisposable = compositeDisposable;
    }

    private void dispose(int currentPage, Disposable[] disposables) {
        for (int i = 0; i < disposables.length; i++) {
            if (i + 1 != currentPage) {
                disposables[i].dispose();
            }
        }
    }

    public void getListUser(int pageSize, String site, String creation, String sort) {
        Objects.requireNonNull(view()).showLoadingDialog();
        disposables = new Disposable[10];
        for (page = 1; page <= 10; page++) {
            disposables[page - 1] = mServices.getListUsers(page, pageSize, site, creation, sort, new ServiceCall.ApiCallback() {
                @Override
                public void responseSucceed(Object obj) {
                    if (!((List<?>) obj).isEmpty()) {
                        dispose(page, disposables);
                        List<UserItem> userItemList = new ArrayList<>();
                        for (Object o : (List<?>) obj) {
                            userItemList.add((UserItem) o);
                        }
                        Objects.requireNonNull(view()).onGetAllUsers(userItemList);
                    } else {
                        Objects.requireNonNull(view()).onNoUsers();
                    }
                    Objects.requireNonNull(view()).hideLoadingDialog();
                }

                @Override
                public void responseFail(String errorMessage) {
                    Objects.requireNonNull(view()).onNoUsers();
                    Objects.requireNonNull(view()).hideLoadingDialog();
                }

                @Override
                public void callbackFail(Throwable throwable) {
                    Objects.requireNonNull(view()).onNoUsers();
                    Objects.requireNonNull(view()).hideLoadingDialog();
                    getNetErrorConsumer(throwable);
                }
            });
            mCompositeDisposable.add(disposables[page - 1]);
        }
    }
}