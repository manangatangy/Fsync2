package com.wolfbang.fsync.ftpservice;

import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;

/**
 * @author david
 * @date 13 Mar 2018.
 */

public class FtpListDir extends FtpService<FTPFile> {

    private String mDirName;

    public FtpListDir(@NonNull FTPClient ftpClient, String dirName) {
        super(ftpClient);
        mDirName = dirName;
    }

    /**
     * Returns dot-prefixed entries, but doesn't return symlinks in the result array.
     * However when the parameter is itself a symlink directory, it returns that dirs contents.
     */
    @Override
    @NonNull
    protected FtpResponse<FTPFile> executeService() throws IOException {
        enableProtocolListener(mFtpClient);
        FTPFile[] files = mFtpClient.mlistDir(mDirName);
        disableProtocolListener(mFtpClient);
        if (files == null) {
            return FtpResponse.error(FtpError.FILE_NOT_FOUND);
        } else {
            Log.d("ftp", "-----------------");
            for (FTPFile file : files) {
                Log.d("ftp", "name:" + file.getName());
                Log.d("ftp", "isSymbolicLink:" + file.isSymbolicLink());
                Log.d("ftp", "formatted:" + file.toFormattedString());
            }
            return FtpResponse.success(files[0]);
        }
    }

}
