package com.stack.overflow.users.application.presenter.service;

import com.stack.overflow.users.BuildConfig;
import com.stack.overflow.users.R;
import com.stack.overflow.users.StackOverflowApplication;
import com.stack.overflow.users.application.database.SQLiteHelper;
import com.stack.overflow.users.application.model.UserItem;
import com.stack.overflow.users.base.utils.LoggerUtil;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;

public class ServiceCall {
    private ServiceApi mServiceApi;

    public ServiceCall() {
        mServiceApi = ServiceFactory.create();
    }

    public interface ApiCallback {
        void responseSucceed(Object obj);

        void responseFail(String errorMessage);

        void callbackFail(Throwable throwable);
    }

    public Disposable getListUsers(int page, int pageSize, final ApiCallback callback) {
        if (StackOverflowApplication.getInstance().isNetWorkNotConnected()) {
            return (Disposable) Observable.error(new Exception(StackOverflowApplication.getInstance().getString(R.string.error_network)));
        }
        return mServiceApi.getUsers(page, pageSize, StackOverflowApplication.getInstance().getString(R.string.stack_overflow_site), StackOverflowApplication.getInstance().getString(R.string.sort_by), StackOverflowApplication.getInstance().getString(R.string.order_by), BuildConfig.STACK_OVERFLOW_APPLICATION_ID)
                .subscribeOn(StackOverflowApplication.getInstance().subscribeScheduler())
                .observeOn(StackOverflowApplication.getInstance().observeScheduler())
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

    public Disposable getListReputations(long userId, int page, int pageSize, final ApiCallback callback) {
        if (StackOverflowApplication.getInstance().isNetWorkNotConnected()) {
            return (Disposable) Observable.error(new Exception(StackOverflowApplication.getInstance().getString(R.string.error_network)));
        }
        return mServiceApi.getReputations(userId, page, pageSize, StackOverflowApplication.getInstance().getString(R.string.stack_overflow_site), StackOverflowApplication.getInstance().getString(R.string.sort_by), StackOverflowApplication.getInstance().getString(R.string.order_by), BuildConfig.STACK_OVERFLOW_APPLICATION_ID)
                .subscribeOn(StackOverflowApplication.getInstance().subscribeScheduler())
                .observeOn(StackOverflowApplication.getInstance().observeScheduler())
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
        return Single.defer((Callable<SingleSource<List<UserItem>>>) () -> Single.just(SQLiteHelper.getInstance(StackOverflowApplication.getInstance()).getFavoriteUsers()))
                .subscribeOn(StackOverflowApplication.getInstance().subscribeScheduler())
                .observeOn(StackOverflowApplication.getInstance().observeScheduler())
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
                SQLiteHelper.getInstance(StackOverflowApplication.getInstance()).updateFavoriteUsers(userItem);
            } catch (IOException e) {
                LoggerUtil.e(ServiceCall.class.getSimpleName(), "saveFavoriteUser(UserItem userItem)", e.getMessage(), e);
            }
        })
                .subscribeOn(StackOverflowApplication.getInstance().subscribeScheduler())
                .observeOn(StackOverflowApplication.getInstance().observeScheduler())
                .subscribe(() -> callback.responseSucceed(null), callback::callbackFail);
    }

}