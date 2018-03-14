package com.wolfbang.fsync.ftpservice;

import android.support.annotation.NonNull;

/**
 * @author david
 * @date 13 Mar 2018.
 */

public class FtpResponse<ResponseT> {

    public final ResponseT response;
    public final FtpError ftpError;
    public final String errorMessage;

    public static <ResponseT> FtpResponse<ResponseT> success(@NonNull ResponseT response) {
        return new FtpResponse<>(response, null, null);
    }

    public static <ResponseT> FtpResponse<ResponseT> error(@NonNull FtpError ftpError) {
        return new FtpResponse<>(null, ftpError, null);
    }

    public static <ResponseT> FtpResponse<ResponseT> error(@NonNull FtpError ftpError, String errorMessage) {
        return new FtpResponse<>(null, ftpError, errorMessage);
    }

    private FtpResponse(ResponseT response, FtpError ftpError, String errorMessage) {
        this.response = response;
        this.ftpError = ftpError;
        this.errorMessage = errorMessage;
    }

    public boolean isErrored() {
        return (ftpError != null);
    }

    public ResponseT getResponse() {
        return response;
    }

    public FtpError getFtpError() {
        return ftpError;
    }

    public String getErrorText() {
        if (ftpError == null) {
            return null;
        }
        return ftpError.name() + (errorMessage == null ? "" : (":" + errorMessage));
    }
}
