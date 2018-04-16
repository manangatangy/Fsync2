package com.wolfbang.fsync.model.compare;


/**
 * @author david
 * @date 30 Mar 2018.
 */

public enum Precedence {
    // This is a mode set prior to the comparison process.
    // It specifies which version of the file (endpoint a or endpoint b)
    // should be copied to the other endpoint.
    A, B, NEWEST, NONE
}
