package com.stack.overflow.users.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ContentView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public abstract class BaseFragment extends DaggerFragment implements BaseView {

    private Unbinder mUnBinder;

    @ContentView
    public BaseFragment(@LayoutRes int contentLayoutId) {
        super(contentLayoutId);
    }

    public void onFragmentResume() {
        // For overriding
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, getView());
    }

    @Override public void onDestroyView() {
        hideLoadingDialog();
        activity().dismissErrorDialog();

        if (mUnBinder != null) {
            mUnBinder.unbind();
            mUnBinder = null;
        }
        super.onDestroyView();
    }

    @Override public void showLoadingDialog() {
        activity().showLoadingDialog();
    }

    @Override public void hideLoadingDialog() {
        activity().hideLoadingDialog();
    }

    @Override public void showErrorDialog(String errorMessage) {
        activity().showErrorDialog(errorMessage);
    }

    @Override public void showErrorDialog(Throwable throwable) {
        activity().showErrorDialog(throwable);
    }

    @Override public BaseActivity activity(){
        return (BaseActivity) getActivity();
    }
}