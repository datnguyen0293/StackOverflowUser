package com.stack.overflow.users.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public abstract class BaseFragment extends Fragment implements BaseView {

    private Unbinder mUnBinder;

    protected abstract int getLayoutResourceId();

    public void onFragmentResume() {
        // For overriding
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(getLayoutResourceId(), container, false);
        mUnBinder = ButterKnife.bind(this, rootView);
        return rootView;
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

    @Override public BaseActivity activity(){
        return (BaseActivity) getActivity();
    }
}