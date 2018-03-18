package com.wolfbang.fsync.ftpservice;

import android.support.annotation.NonNull;
import android.util.Log;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public abstract class FtpService<ResponseT> {

//    String server1 = "192.168.0.9";
//    String server2 = "crazycat.mental";
//    String server3 = "localhost";
//    String server4 = "8.8.8.8";
//    String server5 = "8.8.8.8";
//    String user = "music";
//    String password = "music";
//    String dir = "/home/music";

    private ProtocolCommandListener mPrintCommandListener =
            new PrintCommandListener(new PrintWriter(System.err), true);

    @NonNull
    protected FTPClient mFtpClient;

    private boolean mShowProtocolTrace = false;

    public FtpService(@NonNull FTPClient ftpClient) {
        mFtpClient = ftpClient;
    }

    public FtpService<ResponseT> setShowProtocolTrace(boolean showProtocolTrace) {
        mShowProtocolTrace = showProtocolTrace;
        return this;
    }

    /**
     * Perform a service using the FtpClient, which will have been connected
     * and logged on, ready to use.  Once this method returns, the connection
     * will be logged out and disconnected.
     *
     * @return the result of the service.
     *
     * @throws IOException
     */
    @NonNull
    protected abstract FtpResponse<ResponseT> executeService() throws IOException;

    @NonNull
    public FtpResponse<ResponseT> execute(String hostName, String userName, String password) {
        FtpResponse<ResponseT> ftpResponse;
        try {
            if (InetAddress.getByName(hostName) == null) {
                Log.d("FtpService", "server can't be named");
                ftpResponse = FtpResponse.error(FtpError.ADDRESS_FOR_HOST_NOT_FOUND);
            } else {
                if (mShowProtocolTrace) {
                    mFtpClient.addProtocolCommandListener(mPrintCommandListener);
                }
                mFtpClient.setConnectTimeout(5000);
                mFtpClient.setDefaultTimeout(5000);
                mFtpClient.connect(hostName);
                if (!FTPReply.isPositiveCompletion(mFtpClient.getReplyCode())) {
                    ftpResponse = FtpResponse.error(FtpError.CONNECT_REFUSED);
                } else {
                    if (!mFtpClient.login(userName, password)) {
                        Log.d("FtpService", "login failed");
                        ftpResponse = FtpResponse.error(FtpError.LOGIN_FAILED);
                    } else {
                        mFtpClient.enterLocalPassiveMode();
                        String systemType = mFtpClient.getSystemType();
                        String currentDir = mFtpClient.printWorkingDirectory();

                        Log.d("FtpService", "Remote systemType:" + systemType);
                        Log.d("FtpService", "workingDirectory:" + currentDir);

                        ftpResponse = executeService();
                        mFtpClient.logout();
                    }
                }
            }
        } catch (UnknownHostException uhe) {
            // "crazycat.mental":"Unable to resolve host "crazycat.mental": No address associated with hostname"
            uhe.printStackTrace();
            ftpResponse = FtpResponse.error(FtpError.UNKNOWN_HOST, uhe.getMessage());
        } catch (FTPConnectionClosedException fcce) {
            fcce.printStackTrace();
            ftpResponse = FtpResponse.error(FtpError.CONNECTION_CLOSED, fcce.getMessage());
        } catch (ConnectException ce) {
            // "localhost":ConnectException: "failed to connect to localhost/127.0.0.1 (port 21) after 5000ms: isConnected failed: ECONNREFUSED (Connection refused)"
            ce.printStackTrace();
            ftpResponse = FtpResponse.error(FtpError.CONNECTION_EXCEPTION, ce.getMessage());
        } catch (IOException ioe) {
            // "192.168.0.9"/"8.8.8.8":"Timed out waiting for initial connect reply"
            ioe.printStackTrace();
            ftpResponse = FtpResponse.error(FtpError.IO_EXCEPTION, ioe.getMessage());
        } catch (Exception exc) {
            exc.printStackTrace();
            ftpResponse = FtpResponse.error(FtpError.UNKNOWN_EXCEPTION, exc.getMessage());
        } finally {
            try {
                mFtpClient.disconnect();
            } catch (IOException ioe) {
                // Swallow this exception.
                ioe.printStackTrace();
            }
        }
        if (mShowProtocolTrace) {
            mFtpClient.removeProtocolCommandListener(mPrintCommandListener);
        }
        return ftpResponse;
    }
}

    /*
    http://www.mysamplecode.com/2012/03/apache-commons-ftpclient-java-example.html
    https://commons.apache.org/proper/commons-net/apidocs/index.html?org/apache/commons/net/ftp/FTPClient.html
    https://commons.apache.org/proper/commons-net/apidocs/org/apache/commons/net/ftp/FTPClient.html#retrieveFile(java.lang.String,%20java.io.OutputStream)
    https://commons.apache.org/proper/commons-net/apidocs/org/apache/commons/net/ftp/FTPClient.html#storeFile(java.lang.String,%20java.io.InputStream)
    https://commons.apache.org/proper/commons-net/apidocs/index.html?org/apache/commons/net/ftp/FTPClientConfig.html


    FTPClient ftpClient = new FTPClient();
ftpClient.connect(InetAddress.getByName(server));
ftpClient.login(user, password);
ftpClient.changeWorkingDirectory(serverRoad);
ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

BufferedInputStream buffIn = null;
buffIn = new BufferedInputStream(new FileInputStream(file));
ftpClient.enterLocalPassiveMode();
ftpClient.storeFile("test.txt", buffIn);
buffIn.close();
ftpClient.logout();
ftpClient.disconnect();

ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));

    FTPClient f=FTPClient();
    FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
    conf.setServerLanguageCode("fr");
    f.configure(conf);
    f.connect(server);
    f.login(username, password);
    FTPFile[] files = listFiles(directory);
     */
