package com.wolfbang.fsync.application;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author david
 * @date 10 Mar 2018.
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}
