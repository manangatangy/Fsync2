package com.wolfbang.fsync.ftpservice;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.filetree.SymlinkNode;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.SymLinkParsingFtpClient;

import java.io.IOException;
import java.util.Date;

/**
 * @author david
 * @date 14 Mar 2018.
 */

public class FtpRecursiveList extends FtpService<FileNode> {

    public static final String DOT_FILE = ".";
    public static final String DOT_DOT_FILE = "..";

    private String mRootPath;

    /**
     * The commons-net-3.6 version of {@link FTPClient#mlistDir(String)} doesn't return
     * symlinks in the result array, but will still correctly handle the case where the
     * pathname parameter is itself a symlink directory, in which case it is treated as
     * a normal directory.
     * If you wish to see Syslinks in the result, then pass {@link SymLinkParsingFtpClient}
     * (which is able to parse symlinks correctly).
     */
    public FtpRecursiveList(@NonNull FTPClient ftpClient, String rootPath) {
        super(ftpClient);
        mRootPath = rootPath;
    }

    /**
     * @return array of File, corresponding to the path which may be a directory
     * or a file. Do not pass null or empty string; use "." to specify the current
     * directory.  Returns FtpEndPointError.PATH_NOT_FOUND if path does not exist,
     */
    @NonNull
    @Override
    protected FtpResponse<FileNode> executeService() throws IOException {

        FTPFile ftpRoot = mFtpClient.mlistFile(mRootPath);

        if (ftpRoot == null) {
            return FtpResponse.error(FtpEndPointError.PATH_NOT_FOUND);
        }

        FileNode rootFileNode = makeFile(ftpRoot, null);

        // If path is a symlinked dir, then the rootFile will be SYMLINK, since
        // the file/directory status of symlinks cannot be determined.  However
        // appending "/." to the same value (ie., "Music/." where Music is
        // a symlink like 'Music -> /mnt/sda1/Music/music2-picarded') will give
        // a DIR rootFile.
        if (rootFileNode instanceof DirNode) {
            populateAndTraverse(mFtpClient, (DirNode) rootFileNode);
        }
        return FtpResponse.success(rootFileNode);
    }

    private void populateAndTraverse(FTPClient ftpClient,
                                     DirNode dirNode) throws IOException {

        // Fetch a list of the directory's children and iterate over it
        // twice; first to add new Files to the Directory and then second
        // to recursively traverse into any of those children which are also
        // a directory. This approach means FTPFile instances will not be
        // existent for the entire tree traversal.
        FTPFile[] ftpFiles = ftpClient.mlistDir(dirNode.getPath());

        for (FTPFile ftpFile : ftpFiles) {
            FileNode child = makeFile(ftpFile, dirNode);
            if (child != null) {
                dirNode.add(child);
            }
        }

        for (Node child : dirNode.getChildren()) {
            if (child instanceof DirNode) {
                populateAndTraverse(ftpClient, (DirNode)child);
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
    private FileNode makeFile(@NonNull FTPFile ftpFile, DirNode parent) {
        String name = ftpFile.getName();
        // TODO maybe check there are no / separators in some weird filenames ?
        Date date = (ftpFile.getTimestamp() == null) ? null : ftpFile.getTimestamp().getTime();
        if (ftpFile.isFile()) {
            return new FileNode(name, parent, date);
        } else if (ftpFile.isDirectory()) {
            if (DOT_FILE.equals(ftpFile.getName()) ||
                    DOT_DOT_FILE.equals(ftpFile.getName())) {
                return null;
            } else {
                return new DirNode(name, parent, date);
            }
        } else if (ftpFile.isSymbolicLink()) {
            // These will only occur if using a SymLinkParsingFtpClient
            return new SymlinkNode(name, parent, date);
        } else {
            return null;        // What could it be ?
        }
    }

}
