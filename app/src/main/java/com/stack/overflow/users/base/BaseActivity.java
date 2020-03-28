package com.stack.overflow.users.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import com.stack.overflow.users.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author dat nguyen
 * @since 2019 Sep 13
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private Unbinder mUnbinder;
    private ProgressDialog mLoadingDialog;
    private AlertDialog mErrorDialog;
    protected ActionBar mActionBar;

    protected abstract int getLayoutResourceId();

    public void setupActionBar() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.hide();
        }
    }

    public FragmentManager fragmentManager(){
        return getSupportFragmentManager();
    }

    public void dismissErrorDialog() {
        if (mErrorDialog != null) {
            mErrorDialog.dismiss();
            mErrorDialog = null;
        }
    }

    @CallSuper @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResourceId() != 0)
            setContentView(getLayoutResourceId());
        mUnbinder = ButterKnife.bind(this);
        setupActionBar();
    }

    @CallSuper @Override protected void onDestroy() {
        hideLoadingDialog();
        dismissErrorDialog();

        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        super.onDestroy();
    }

    @Override public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new ProgressDialog(this);
            mLoadingDialog.setIndeterminate(true);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setMessage(getString(R.string.processing));
            mLoadingDialog.show();
        } else if (!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    @Override public void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override public BaseActivity activity() {
        return this;
    }

    @Override public void showErrorDialog(String errorMessage) {
        if (mErrorDialog == null) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage(errorMessage)
                    .setCancelable(false)
                    .setOnCancelListener(DialogInterface::dismiss)
                    .setPositiveButton(R.string.action_close, (dialog, which) -> dialog.dismiss());
            mErrorDialog = dialogBuilder.create();
        }

        if (!mErrorDialog.isShowing()) {
            mErrorDialog.setMessage(errorMessage);
            mErrorDialog.show();
        }
    }
}