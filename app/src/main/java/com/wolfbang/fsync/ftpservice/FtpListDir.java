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
     * should be a directory.
     * null or empty string is treated as working-directory.
     * If the path doesn't exist, or is not a directory, then  PATH_NOT_FOUND is returned.
     * Refer to {@link org.apache.commons.net.ftp.SymLinkParsingFtpClient} for details
     * of the result entries.
     */
    @Override
    @NonNull
    protected FtpResponse<FTPFile[]> executeService() throws IOException {
        FTPFile[] files = mFtpClient.mlistDir(mPath);
        Log.d("ftp", "---------[" + mPath + "]--------");
        if (files == null) {
            return FtpResponse.error(FtpError.NOT_A_DIRECTORY);
        } else {
            for (FTPFile file : files) {
                Log.d("ftp", "name:" + file.getName()
                        + "  isSymbolicLink:" + file.isSymbolicLink()
                        + "  isDirectory:" + file.isDirectory()
                        + "  isFile:" + file.isFile());
                Log.d("ftp", "formatted:" + file.toFormattedString());
            }
            return FtpResponse.success(files);
        }
    }

}
