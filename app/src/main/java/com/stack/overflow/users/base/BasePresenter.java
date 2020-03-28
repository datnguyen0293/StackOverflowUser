package com.stack.overflow.users.base;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.base.utils.LoggerUtil;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public abstract class BasePresenter <V extends BaseView> {
    private V mView;
    protected Disposable mDisposable;
    protected CompositeDisposable mCompositeDisposable;
    protected ServiceCall mServices;

    protected BasePresenter() {
        // Do nothing
    }

    @CallSuper public void bindView(@NonNull V view) {
        mView = view;
    }

    @CallSuper public void unbindView() {
        if (mCompositeDisposable != null) {
            if (mDisposable != null && mCompositeDisposable.isDisposed()) {
                mCompositeDisposable.delete(mDisposable);
            }

            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }

        mView = null;
    }

    @Nullable protected V view() {
        return mView;
    }

    protected void getNetErrorConsumer(Throwable throwable) {
        LoggerUtil.e(BasePresenter.class.getSimpleName(), "getNetErrorConsumer(Throwable throwable)", throwable.getMessage(), throwable);
        mView.showErrorDialog(throwable);
    }
}