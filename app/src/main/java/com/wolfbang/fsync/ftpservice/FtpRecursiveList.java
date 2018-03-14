package com.wolfbang.fsync.ftpservice;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wolfbang.fsync.ftpservice.model.Directory;
import com.wolfbang.fsync.ftpservice.model.File;
import com.wolfbang.fsync.ftpservice.model.Symlink;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.SymLinkParsingFtpClient;

import java.io.IOException;
import java.util.Date;

/**
 * @author david
 * @date 14 Mar 2018.
 */

public class FtpRecursiveList extends FtpService<File> {

    private String mRootPath;

    /**
     * The commons-net-3.6 version of {@link FTPClient#mlistDir(String)} doesn't return
     * symlinks in the result array, will still correctly handle the case where the
     * pathname parameter is itself a symlink directory, in which case it is treated as
     * a normal directory.
     * If you wish to see Syslink s in the result, then pass {@link SymLinkParsingFtpClient}
     * (which is able to parse symlinks correctly).
     */
    public FtpRecursiveList(@NonNull FTPClient ftpClient, String rootPath) {
        super(ftpClient);
        mRootPath = rootPath;
    }

    /**
     * @return array of File, corresponding to the path which may be a directory
     * or a file. Do not pass null or empty string; use "." to specify the current
     * directory.  Returns FtpError.PATH_NOT_FOUND if path does not exist,
     */
    @NonNull
    @Override
    protected FtpResponse<File> executeService() throws IOException {

        FTPFile ftpRoot = mFtpClient.mlistFile(mRootPath);
        // TODO check that "." works correctly and indicate isDirectory()

        if (ftpRoot == null) {
            return FtpResponse.error(FtpError.PATH_NOT_FOUND);
        }

        File rootFile = makeFile(ftpRoot, null);

        if (rootFile instanceof Directory) {
            populateAndTraverse(mFtpClient, (Directory)rootFile);
        }
        return FtpResponse.success(rootFile);
    }

    private void populateAndTraverse(FTPClient ftpClient,
                                     Directory directory) throws IOException {

        // Fetch a list of the directory's children and iterate over it
        // twice; first to add new Files to the Directory and then second
        // to recursively traverse into any of those children which are also
        // a directory. This approach means FTPFile instances will not be
        // existent for the entire tree traversal.
        FTPFile[] ftpFiles = ftpClient.mlistDir(directory.getPath());

        for (FTPFile ftpFile : ftpFiles) {
            File child = makeFile(ftpFile, directory);
            if (child != null) {
                directory.getChildren().add(child);
            }
        }

        for (File child : directory.getChildren()) {
            if (child instanceof Directory) {
                populateAndTraverse(ftpClient, (Directory)child);
            }
        }
    }

    /**
     * @param ftpFile
     * @param parent - may be null to indicate the root
     * @return a File (which may be a Directory or a Symlink) corresponding
     * to the specified FTPFile. No traversing/recursion is performed.
     * Exclude "." and ".." nodes.
     */
    @Nullable
    private File makeFile(@NonNull FTPFile ftpFile, Directory parent) {
        String name = ftpFile.getName();
        // TODO maybe check there are no / separators
//        Date date = ftpFile.getTimestamp().getTime();
        Date date = null;       // TODO temp
        if (ftpFile.isFile()) {
            return new File(name, parent, date);
        } else if (ftpFile.isDirectory()) {
            if (".".equals(ftpFile.getName()) || "..".equals(ftpFile.getName())) {
                return null;
            } else {
                return new Directory(name, parent, date);
            }
        } else if (ftpFile.isSymbolicLink()) {
            // These will only occur if using a SymLinkParsingFtpClient
            return new Symlink(name, parent, date);
        } else {
            return null;        // What could it be ?
        }
    }

}
