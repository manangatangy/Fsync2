package com.wolfbang.fsync.ftpservice;

/**
 * @author david
 * @date 13 Mar 2018.
 */

public enum FtpError {
    ADDRESS_FOR_HOST_NOT_FOUND,
    CONNECT_REFUSED,
    LOGIN_FAILED,
    UNKNOWN_HOST,
    CONNECTION_CLOSED,
    CONNECTION_EXCEPTION,
    IO_EXCEPTION,
    NOT_A_DIRECTORY,
    NOT_A_FILE,
    PATH_NOT_FOUND,
    FILE_NOT_FOUND,     // todo deprecate
    UNKNOWN_EXCEPTION;

}
