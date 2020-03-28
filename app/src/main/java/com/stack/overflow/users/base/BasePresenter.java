package com.stack.overflow.users.base;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.stack.overflow.users.R;
import com.stack.overflow.users.StackOverflowApplication;
import com.stack.overflow.users.application.presenter.service.ServiceCall;
import com.stack.overflow.users.base.utils.LoggerUtil;
import java.net.SocketTimeoutException;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

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
        mCompositeDisposable = new CompositeDisposable();
        mServices = new ServiceCall();
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

        if (throwable instanceof HttpException) {
            throwHttpException();
            return;
        }

        if (throwable instanceof SocketTimeoutException) {
            throwSocketTimeoutException();
        }

        String msg = throwable.getMessage();
        mView.showErrorDialog(msg);
    }

    private void throwHttpException() {
        String msg =StackOverflowApplication.getInstance().getString(R.string.err_network_not_connected);
        mView.showErrorDialog(msg);
    }

    private void throwSocketTimeoutException(){
        String msg = StackOverflowApplication.getInstance().getString(R.string.err_timeout);
        mView.showErrorDialog(msg);
    }
}