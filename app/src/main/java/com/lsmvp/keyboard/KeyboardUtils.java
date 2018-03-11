//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lsmvp.keyboard;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtils {
    private static final int DELAY_MILLIS_SHOW_KEYBOARD_ON_RESUME = 200;

    public static void showKeyboard(final View view) {
        view.requestFocus();
        view.postDelayed(new Runnable() {
            public void run() {
                InputMethodManager keyboard = (InputMethodManager)view.getContext().getSystemService("input_method");
                keyboard.showSoftInput(view, 1);
            }
        }, 200L);
    }

    public static boolean dismissKeyboard(View view) {
        boolean result = false;
        if(view != null && view.getContext() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)view.getContext().getSystemService("input_method");
            result = inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        return result;
    }

    public static boolean dismissKeyboard(Activity activity) {
        boolean result = false;
        if(activity != null) {
            View view = activity.getCurrentFocus();
            if(view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService("input_method");
                result = inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if(activity instanceof KeyboardVisibilityListener) {
                    ((KeyboardVisibilityListener)activity).onKeyboardVisibilityChanged(KeyboardVisibility.GONE);
                }
            }
        }

        return result;
    }

    private KeyboardUtils() {
    }
}
