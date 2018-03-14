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
     * @return an FTPFile entry corresponding to the path which should be a file or a
     * directory (which returns a single result for that directory, not its contents).
     * null or empty string is treated as working-directory.
     * If the path does not exists then PATH_NOT_FOUND is returned.
     * Refer to {@link org.apache.commons.net.ftp.SymLinkParsingFtpClient} for details
     * of the result entries.
     */
    @Override
    @NonNull
    protected FtpResponse<FTPFile> executeService() throws IOException {
//        ftpClient.setStrictReplyParsing(true);
        FTPFile file = mFtpClient.mlistFile(mPath);
        // TODO check that "." works correctly and returns NOT_A_FILE
        Log.d("ftp", "---------[" + mPath + "]--------");

        // todo handle NOT_A_FILE
        if (file == null) {
            return FtpResponse.error(FtpError.PATH_NOT_FOUND);
        } else {
            Log.d("ftp", "name:" + file.getName()
                    + "  isSymbolicLink:" + file.isSymbolicLink()
                    + "  isDirectory:" + file.isDirectory()
                    + "  isFile:" + file.isFile());
            Log.d("ftp", "formatted:" + file.toFormattedString());
            return FtpResponse.success(file);
        }
    }

}
