package com.wolfbang.fsync.ftpservice;

import android.support.annotation.NonNull;

import com.wolfbang.fsync.model.mission.EndPointResponse;

/**
 * @author david
 * @date 13 Mar 2018.
 */

public class FtpResponse<ResponseT> extends EndPointResponse<ResponseT> {

    public static <ResponseT> FtpResponse<ResponseT> success(@NonNull ResponseT response) {
        return new FtpResponse<>(response, null, null);
    }

    public static <ResponseT> FtpResponse<ResponseT> error(@NonNull FtpEndPointError ftpEndPointError) {
        return new FtpResponse<>(null, ftpEndPointError, null);
    }

    public static <ResponseT> FtpResponse<ResponseT> error(@NonNull FtpEndPointError ftpEndPointError, String errorMessage) {
        return new FtpResponse<>(null, ftpEndPointError, errorMessage);
    }

    private FtpResponse(ResponseT response, FtpEndPointError ftpEndPointError, String errorMessage) {
        super(response, ftpEndPointError, errorMessage);
    }

//    public FtpEndPointError getFtpEndPointError() {
//        return mFtpEndPointError;
//    }

}
