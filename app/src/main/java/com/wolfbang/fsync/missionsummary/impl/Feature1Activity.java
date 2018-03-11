package com.wolfbang.fsync.missionsummary.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.lsmvp.simplemvp.NestedViewHost;
import com.lsmvp.simplemvp.ProgressDialogProvider;
import com.wolfbang.fsync.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class Feature1Activity
        extends AppCompatActivity
        implements NestedViewHost, ProgressDialogProvider {

    private final static String KEY_FRAG_CLASS_NAME = "FRAG_CLASS_NAME";

    @BindView(R.id.progress_bar_frame)
    FrameLayout mProgressBarLayout;

    @NonNull
    public static Intent createIntent(@NonNull Context context, String fragClassName) {
        Intent intent = new Intent(context, Feature1Activity.class);
        Bundle args = new Bundle();
        args.putString(KEY_FRAG_CLASS_NAME, fragClassName);
        intent.putExtras(args);
        return intent;
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature1);
        ButterKnife.bind(this);

        Bundle args = getIntent().getExtras();
        String fragClassName = (args == null) ? null : args.getString(KEY_FRAG_CLASS_NAME);
        if (fragClassName != null) {
            setFragment(fragClassName, R.id.fragment_container, args);
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
