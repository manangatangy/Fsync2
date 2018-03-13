package com.wolfbang.fsync.ftpservice;

import android.support.annotation.NonNull;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;

/**
 * @author david
 * @date 13 Mar 2018.
 */

public class FtpFileListService extends FtpService<FTPFile> {

    private String mPathName;

    public FtpFileListService(@NonNull FTPClient ftpClient, String pathName) {
        super(ftpClient);
        mPathName = pathName;
        if (mPathName == null || mPathName.length() == 0) {
            mPathName = null;
        }
    }

    @Override
    @NonNull
    protected FtpResponse<FTPFile> executeService() throws IOException {
        FTPFile file = mFtpClient.mlistFile(mPathName);
        if (file == null) {
            return FtpResponse.error(FtpError.FILE_NOT_FOUND);
        } else {
            return FtpResponse.success(file);
        }
    }

}
