package com.wolfbang.fsync.ftpservice;

import com.wolfbang.fsync.model.filetree.DirNode;
import com.wolfbang.fsync.model.filetree.FileNode;
import com.wolfbang.fsync.model.mission.EndPoint;
import com.wolfbang.fsync.model.mission.EndPointResponse;

import org.apache.commons.net.ftp.SymLinkParsingFtpClient;

/**
 * @author david
 * @date 18 Mar 2018.
 */

public class FtpEndPoint extends EndPoint {

    private String mHost;
    private String mUserName;
    private String mPassword;
    private String mRootDir;

    public FtpEndPoint(String endPointName, String host, String userName, String password, String rootDir) {
        super(endPointName);
        this.mHost = host;
        this.mUserName = userName;
        this.mPassword = password;
        this.mRootDir = rootDir;
    }

    public String getHost() {
        return mHost;
    }

//    public void setHost(String mHost) {
//        this.mHost = mHost;
//    }

    public String getUserName() {
        return mUserName;
    }

//    public void setUserName(String mUserName) {
//        this.mUserName = mUserName;
//    }

    public String getPassword() {
        return mPassword;
    }

//    public void setPassword(String mPassword) {
//        this.mPassword = mPassword;
//    }

    public String getRootDir() {
        return mRootDir;
    }

//    public void setRootDir(String mRootDir) {
//        this.mRootDir = mRootDir;
//    }

    @Override
    public EndPointResponse<DirNode> fetchFileTree() {
        FtpResponse<FileNode> ftpResponse = new FtpRecursiveList(new SymLinkParsingFtpClient(), mRootDir)
                .setShowProtocolTrace(true)
                .execute(mHost, mUserName, mPassword);

        if (ftpResponse.isErrored()) {
            return new FtpResponse<>(null, (FtpEndPointError)ftpResponse.endPointError,
                                     ftpResponse.errorMessage);
        }
        if (! (ftpResponse.getResponse() instanceof DirNode)) {
            return new FtpResponse<>(null, FtpEndPointError.ROOT_NOT_DIRECTORY,
                                     "root path is not a directory (maybe a symlink?)");
        }
        DirNode dirNode = (DirNode)ftpResponse.getResponse();
        return FtpResponse.success(dirNode);
    }

}
