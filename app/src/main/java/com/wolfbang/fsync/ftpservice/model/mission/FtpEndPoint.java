package com.wolfbang.fsync.ftpservice.model.mission;

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

    public void setHost(String mHost) {
        this.mHost = mHost;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getRootDir() {
        return mRootDir;
    }

    public void setRootDir(String mRootDir) {
        this.mRootDir = mRootDir;
    }

}
