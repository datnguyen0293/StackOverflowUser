package com.stack.overflow.users.application.presenter.service;

import com.stack.overflow.users.BuildConfig;
import com.stack.overflow.users.application.database.SQLiteHelper;
import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.base.utils.LoggerUtil;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ServiceCall {
    private ServiceApi mServiceApi;
    private SQLiteHelper mSqLiteHelper;

    public ServiceCall(ServiceApi serviceApi, SQLiteHelper sqLiteHelper) {
        mServiceApi = serviceApi;
        mSqLiteHelper = sqLiteHelper;
    }

    public interface ApiCallback {
        void responseSucceed(Object obj);

        void responseFail(String errorMessage);

        void callbackFail(Throwable throwable);
    }

    public Disposable getListUsers(int page, int pageSize, String site, String creation, String sort, final ApiCallback callback) {
        return mServiceApi.getUsers(page, pageSize, site, creation, sort, BuildConfig.STACK_OVERFLOW_APPLICATION_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(usersResponse -> {
                    if (usersResponse != null) {
                        if (usersResponse.getListUserItems() != null) {
                            callback.responseSucceed(usersResponse.getListUserItems());
                        } else {
                            callback.responseFail(null);
                        }
                    }
                }, throwable -> {
                    if (throwable != null) {
                        callback.callbackFail(throwable);
                    }
                });
    }

    public Disposable getListReputations(long userId, int page, int pageSize, String site, String creation, String sort, final ApiCallback callback) {
        return mServiceApi.getReputations(userId, page, pageSize, site, creation, sort, BuildConfig.STACK_OVERFLOW_APPLICATION_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(usersResponse -> {
                    if (usersResponse != null) {
                        if (usersResponse.getListReputationItems() != null) {
                            callback.responseSucceed(usersResponse.getListReputationItems());
                        } else {
                            callback.responseFail(null);
                        }
                    }
                }, throwable -> {
                    if (throwable != null) {
                        callback.callbackFail(throwable);
                    }
                });
    }

    public Disposable getFavoriteUsers(final ApiCallback callback){
        return Single.defer((Callable<SingleSource<List<UserItem>>>) () -> Single.just(mSqLiteHelper.getFavoriteUsers()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listUsers -> {
                    if (listUsers != null) {
                        callback.responseSucceed(listUsers);
                    } else {
                        callback.responseFail(null);
                    }
                }, throwable -> {
                    if (throwable != null) {
                        callback.callbackFail(throwable);
                    }
                });
    }

    public Disposable saveFavoriteUser(UserItem userItem, final ApiCallback callback) {
        return Completable.fromRunnable(()-> {
            try {
                mSqLiteHelper.updateFavoriteUsers(userItem);
            } catch (IOException e) {
                LoggerUtil.e(ServiceCall.class.getSimpleName(), "saveFavoriteUser(UserItem userItem)", e.getMessage(), e);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> callback.responseSucceed(null), callback::callbackFail);
    }

}