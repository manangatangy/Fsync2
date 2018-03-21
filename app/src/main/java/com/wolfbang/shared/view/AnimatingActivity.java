package com.wolfbang.shared.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.v7.app.AppCompatActivity;

import com.wolfbang.fsync.R;

/**
 * @author david
 * @date 21 Mar 2018.
 */

public class AnimatingActivity extends AppCompatActivity {

    private static final String KEY_FINISH_ENTER_ANIMATION = "key_finish_enter_animation";
    private static final String KEY_FINISH_EXIT_ANIMATION = "key_finish_exit_animation";

    private int mStartEnterAnimation = -1;
    private int mStartExitAnimation = -1;

    private int mFinishEnterAnimation = -1;
    private int mFinishExitAnimation = -1;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_FINISH_ENTER_ANIMATION, mFinishEnterAnimation);
        outState.putInt(KEY_FINISH_EXIT_ANIMATION, mFinishExitAnimation);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mFinishEnterAnimation = savedInstanceState.getInt(KEY_FINISH_ENTER_ANIMATION, -1);
        mFinishExitAnimation = savedInstanceState.getInt(KEY_FINISH_EXIT_ANIMATION, -1);
    }

    /**
     * Use standard enter animation for a new activity.
     * Current activity animated to slide off the screen from right to left.
     * New activity animated to slide into the screen from right to left.
     */
    public void useStartAnimations() {
        useStartAnimations(R.anim.slide_in_from_right_to_left, R.anim.slide_out_from_right_to_left_partial);
    }

    public void useStartAnimations(@AnimRes int startEnterAnimation, @AnimRes int startExitAnimation) {
        mStartEnterAnimation = startEnterAnimation;
        mStartExitAnimation = startExitAnimation;
    }

    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, null);
    }
    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);

        if (mStartEnterAnimation >= 0 && mStartExitAnimation >= 0) {
            overridePendingTransition(mStartEnterAnimation, mStartExitAnimation);
        }
        mStartEnterAnimation = -1;
        mStartExitAnimation = -1;
    }

    /**
     * Use standard exit animation for the current activity.
     * This will animate current activity to slide out of the screen from left to right.
     * Previous activity will be animated to slide into the screen from left to right.
     */
    public void useFinishAnimations() {
        useFinishAnimations(R.anim.slide_in_from_left_to_right_partial, R.anim.slide_out_from_left_to_right);
    }

    public void useFinishAnimations(@AnimRes int finishEnterAnimation, @AnimRes int finishExitAnimation) {
        mFinishEnterAnimation = finishEnterAnimation;
        mFinishExitAnimation = finishExitAnimation;
    }

    @Override
    public void finish() {
        super.finish();

        if (mFinishEnterAnimation >= 0 && mFinishExitAnimation >= 0) {
            overridePendingTransition(mFinishEnterAnimation, mFinishExitAnimation);
        }
        // reset to default
        mFinishEnterAnimation = -1;
        mFinishExitAnimation = -1;
    }

}
