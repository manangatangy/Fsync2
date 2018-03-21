package com.wolfbang.shared;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.lsmvp.simplemvp.NestedViewHost;
import com.lsmvp.simplemvp.ProgressDialogProvider;
import com.wolfbang.fsync.R;
import com.wolfbang.shared.view.AnimatingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class SingleFragActivity
        extends AnimatingActivity
        implements NestedViewHost, ProgressDialogProvider {

    // Parameters are passed from createIntent() to onCreate() via these extra args.
    private final static String SFA_FRAG_CLASS_NAME = "SFA_FRAG_CLASS_NAME";
    private final static String SFA_HOME_AS_UP = "SFA_HOME_AS_UP";
    private final static String SFA_SHOW_HOME = "SFA_SHOW_HOME";
    private final static String SFA_TITLE = "SFA_TITLE";
    private final static String SFA_SUB_TITLE = "SFA_SUB_TITLE";
    private final static String SFA_TLE = "SFA_TITLE";

    @BindView(R.id.my_toolbar)
    protected Toolbar mToolbar;

    @BindView(R.id.progress_bar_frame)
    FrameLayout mProgressBarLayout;

    public static class Builder {
        private Intent mIntent;
        public Builder(@NonNull Context context, String fragClassName) {
            mIntent = new Intent(context, SingleFragActivity.class);
            mIntent.putExtra(SFA_FRAG_CLASS_NAME, fragClassName);
        }

        public Builder setDisplayShowHomeEnabled(boolean showHome) {
            mIntent.putExtra(SFA_SHOW_HOME, showHome);
            return this;
        }

        public Builder setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
            mIntent.putExtra(SFA_HOME_AS_UP, showHomeAsUp);
            return this;
        }

        public Builder setTitle(String title) {
            mIntent.putExtra(SFA_TITLE, title);
            return this;
        }

        public Builder setSubTitle(String subTitle) {
            mIntent.putExtra(SFA_SUB_TITLE, subTitle);
            return this;
        }

        @NonNull
        public Intent build() {
            return mIntent;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_frag);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        Bundle args = getIntent().getExtras();
        if (args != null) {
            setFragment(args.getString(SFA_FRAG_CLASS_NAME, null), R.id.fragment_container, args);

            getSupportActionBar().setDisplayShowHomeEnabled(args.getBoolean(SFA_SHOW_HOME, true));
            getSupportActionBar().setDisplayHomeAsUpEnabled(args.getBoolean(SFA_HOME_AS_UP, true));
            getSupportActionBar().setTitle(args.getString(SFA_TITLE, null));
            getSupportActionBar().setSubtitle(args.getString(SFA_SUB_TITLE, null));
            // Set left arrow icon for when home-as-up is true
//            getSupportActionBar().setHomeAsUpIndicator();


        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;        // Means: up-click is absorbed here.
    }

    @Override
    public void onBackPressed() {
        boolean handled = false;
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof BackClickHandler) {
                handled = ((BackClickHandler)fragment).onBackPressed();
                if (handled) {
                    break;
                }
            }
        }
        if (!handled) {
            super.onBackPressed();
        }
    }
/*
    protected <ClassT> ClassT findHostImplementing(Class<ClassT> clazz) {
        NestedViewHost parent = getHostParent();

        while (parent != null) {
            if (clazz.isAssignableFrom(parent.getClass())) {
                return (ClassT) parent;
            }

            parent = parent.getHostParent();
        }
        return null;
    }

        if (parent instanceof NestedViewHost) {
            return (NestedViewHost) parent;

 */



    /**
     * Create or lookup the named fragment and add its view to the specified container.
     * If not null, the specified Bundle will be given to the fragment as args.
     */
    protected Fragment setFragment(String fragClassName, @IdRes int containerViewId,
                                   @Nullable Bundle args) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragClassName);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragClassName, args);
            getSupportFragmentManager().beginTransaction().add(containerViewId, fragment, fragClassName).commit();
        }
        return fragment;
    }

    /**
     * Remove the specified fragment from the activity state and its container.
     */
    protected void teardownFragment(String fragClassName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragClassName);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

    //region NestedViewHost, ProgressDialogProvider
    @Override
    public NestedViewHost getHostParent() {
        // Activities can't be nested inside anything else
        return null;
    }

    @Override
    public void showProgressDialog() {
        mProgressBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressDialog(int messageTemplateId, Object... formatArgs) {
        showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        mProgressBarLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean isProgressVisible() {
        return (mProgressBarLayout.getVisibility() == View.VISIBLE);
    }
    //endregion


}
