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

    private String mPath;

    /**
     * The commons-net-3.6 version of {@link FTPClient#mlistFile(String)} fails with
     * MalformedServerReplyException on every call, even if setStrictReplyParsing is set.
     * Therefore please pass {@link org.apache.commons.net.ftp.SymLinkParsingFtpClient}
     * if you want to use this class successfully.
     */
    public FtpListFile(@NonNull FTPClient ftpClient, String path) {
        super(ftpClient);
        mPath = path;
    }

    /**
     * @return an FTPFile entry corresponding to the path which
     * should be a file. null or empty string causes FILE_NOT_FOUND error.
     * If the path exists but is not a file, then NOT_A_FILE
     * error is returned.
     */
    @Override
    @NonNull
    protected FtpResponse<FTPFile> executeService() throws IOException {
//        ftpClient.setStrictReplyParsing(true);
        FTPFile file = mFtpClient.mlistFile(mPath);
        // TODO check that "." works correctly and returns NOT_A_FILE

        // todo handle NOT_A_FILE
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
