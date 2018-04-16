package com.wolfbang.fsync.model.mission;

import android.support.annotation.Nullable;

/**
 * @author David Weiss
 * @date 16/4/18
 */
public class EndPointResponse<ResponseT> {

    public final ResponseT response;
    public final EndPointError endPointError;
    public final String errorMessage;

    public EndPointResponse(@Nullable ResponseT response, @Nullable EndPointError endPointError, @Nullable String errorMessage) {
        this.response = response;
        this.endPointError = endPointError;
        this.errorMessage = errorMessage;
    }

    public boolean isErrored() {
        return (endPointError != null);
    }

    @Nullable
    public ResponseT getResponse() {
        return response;
    }

    @Nullable
    public EndPointError getEndPointError() {
        return endPointError;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    @Nullable
    public String getErrorText() {
        return (endPointError == null) ? null : (
                endPointError.getError() + (
                        errorMessage == null ? "" : (":" + errorMessage)
                )
        );
    }
}
