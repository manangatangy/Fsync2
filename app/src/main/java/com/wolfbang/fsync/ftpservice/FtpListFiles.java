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

@Deprecated
public class FtpListFiles extends FtpService<FTPFile> {

    private String mPathname;

    public FtpListFiles(@NonNull FTPClient ftpClient, String pathname) {
        super(ftpClient);
        mPathname = pathname;
    }

    /**
     * This doesn't return any hidden files (ie .prefixed) but it does return symlinks.
     */
    @Override
    @NonNull
    protected FtpResponse<FTPFile> executeService() throws IOException {
        FTPFile[] files = mFtpClient.listFiles(mPathname);
        if (files == null) {
            return FtpResponse.error(FtpEndPointError.PATH_NOT_FOUND);
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
