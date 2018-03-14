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

public class FtpListDir extends FtpService<FTPFile[]> {

    private String mPath;

    /**
     * The commons-net-3.6 version of {@link FTPClient#mlistDir(String)} returns
     * dot-prefixed entries, but doesn't return symlinks in the result array.
     * Therefore please pass {@link org.apache.commons.net.ftp.SymLinkParsingFtpClient}
     * if you want to see symlinks in the result.
     * Note that the default FTPClient will still correctly handle the case where
     * the pathname parameter is itself a symlink directory, in which case it returns
     * the contents of that directory.
     */
    public FtpListDir(@NonNull FTPClient ftpClient, String path) {
        super(ftpClient);
        mPath = path;
    }

    /**
     * @return array of FTPFile entries, corresponding to the path which
     * should be a directory. null or empty string means the current directory.
     * If the path exists but is not a directory, then NOT_A_DIRECTORY
     * error is returned.
     */
    @Override
    @NonNull
    protected FtpResponse<FTPFile[]> executeService() throws IOException {
        // TODO check for dirName is a file  and return NOT_A_DIRECTORY
        FTPFile[] files = mFtpClient.mlistDir(mPath);
        if (files == null) {
            return FtpResponse.error(FtpError.PATH_NOT_FOUND);
        } else {
            Log.d("ftp", "-----------------");
            for (FTPFile file : files) {
                Log.d("ftp", "name:" + file.getName());
                Log.d("ftp", "isSymbolicLink:" + file.isSymbolicLink());
                Log.d("ftp", "formatted:" + file.toFormattedString());
            }
            return FtpResponse.success(files);
        }
    }

}
