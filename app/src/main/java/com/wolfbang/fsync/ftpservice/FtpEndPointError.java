package com.wolfbang.fsync.ftpservice;

import android.support.annotation.NonNull;

import com.wolfbang.fsync.ftpservice.model.mission.EndPointError;

/**
 * @author david
 * @date 13 Mar 2018.
 */

public enum FtpEndPointError implements EndPointError {

    ADDRESS_FOR_HOST_NOT_FOUND,
    CONNECT_REFUSED,
    LOGIN_FAILED,
    UNKNOWN_HOST,
    CONNECTION_CLOSED,
    CONNECTION_EXCEPTION,
    IO_EXCEPTION,
    PATH_NOT_FOUND,
    UNKNOWN_EXCEPTION;

    @NonNull
    public String getError() {
        return this.name();
    }

}
