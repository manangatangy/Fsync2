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

public class FtpListFile extends FtpService<FTPFile> {

    private String mDirName;

    public FtpListFile(@NonNull FTPClient ftpClient, String dirName) {
        super(ftpClient);
        mDirName = dirName;
    }

    /**
     * Fails with MalformedServerReplyException on every call, even when
     * setStrictReplyParsing is set.  It might be an idea to go back to v 3.5
     * Although it hardly seems worth it since this method doesn't help anyway.
     */
    @Override
    @NonNull
    protected FtpResponse<FTPFile> executeService() throws IOException {
//        ftpClient.setStrictReplyParsing(true);
        FTPFile file = mFtpClient.mlistFile(mDirName);
        if (file == null) {
            return FtpResponse.error(FtpError.FILE_NOT_FOUND);
        } else {
            Log.d("ftp", "-----------------");
            Log.d("ftp", "name:" + file.getName());
            Log.d("ftp", "isSymbolicLink:" + file.isSymbolicLink());
            Log.d("ftp", "formatted:" + file.toFormattedString());
            return FtpResponse.success(file);
        }
    }

}
