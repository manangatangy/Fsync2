package com.wolfbang.shared;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public interface BackClickHandler {
    /**
     * @return false if you want Activity.onBackPressed() to handle it.
     */
    boolean onBackPressed();
}
